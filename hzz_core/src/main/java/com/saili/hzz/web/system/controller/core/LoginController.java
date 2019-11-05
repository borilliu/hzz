package com.saili.hzz.web.system.controller.core;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.common.util.HttpUtil;
import com.saili.hzz.core.constant.Globals;
import com.saili.hzz.core.util.*;
import com.saili.hzz.core.util.*;
import com.saili.hzz.jwt.util.ResponseMessage;
import com.saili.hzz.jwt.util.Result;
import com.saili.hzz.tag.vo.datatable.SortDirection;
import com.saili.hzz.tag.vo.easyui.ComboTreeModel;
import com.saili.hzz.web.system.manager.ClientManager;
import com.saili.hzz.web.system.pojo.base.*;
import com.saili.hzz.web.system.service.MutiLangServiceI;
import com.saili.hzz.web.system.service.SystemService;
import com.saili.hzz.web.system.service.UserService;
import com.saili.hzz.web.system.sms.util.MailUtil;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.web.system.pojo.base.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import com.saili.hzz.core.common.controller.BaseController;
import com.saili.hzz.core.common.hibernate.qbc.CriteriaQuery;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.ComboTree;
import com.saili.hzz.core.enums.SysThemesEnum;
import com.saili.hzz.core.online.util.FreemarkerHelper;
import com.saili.hzz.web.system.pojo.base.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;



/**
 * 登陆初始化控制器
 * @author liuby
 * 
 */
@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController{
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	private SystemService systemService;
	private UserService userService;
	@Resource
	private ClientManager clientManager;

	@Autowired
	private MutiLangServiceI mutiLangService;
	
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {

		this.userService = userService;
	}
	

	/**
	 * 【登录逻辑】检查用户账号、密码、登录验证码
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(TSUser user, HttpServletRequest req) {
		HttpSession session = req.getSession();
		AjaxJson j = new AjaxJson();
		//语言选择
		if (req.getParameter("langCode")!=null) {
			req.getSession().setAttribute("lang", req.getParameter("langCode"));
		}
		//单点登录（返回链接）
		String returnURL = req.getParameter("ReturnURL");
		if(StringUtils.isNotEmpty(returnURL)){
			req.getSession().setAttribute("ReturnURL", returnURL);
		}
		
		//验证码
		String randCode = req.getParameter("randCode");

		if (StringUtils.isEmpty(randCode)) {
			j.setMsg(mutiLangService.getLang("common.enter.verifycode"));
			j.setSuccess(false);
		} else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))) {
			j.setMsg(mutiLangService.getLang("common.verifycode.error"));
			log.info("Username:{} ,验证码: {} 错误!", user.getUserName(), randCode);
			j.setSuccess(false);
			//IP黑名单 check
		} else if (userService.isInBlackList(IpUtil.getIpAddr(req))){
			log.info("Username:{} ,IP: {} 进入黑名单!", user.getUserName(), IpUtil.getIpAddr(req));
			j.setMsg(mutiLangService.getLang("common.blacklist.error"));
			j.setSuccess(false);
		}
		else {
			//用户登录验证逻辑
			TSUser u = userService.checkUserExits(user);
			if (u == null) {
				u = userService.findUniqueByProperty(TSUser.class, "email", user.getUserName());

				if(u == null || !u.getPassword().equals(PasswordUtil.encrypt(u.getUserName(),user.getPassword(), PasswordUtil.getStaticSalt()))){

					j.setMsg(mutiLangService.getLang("common.username.or.password.error"));
					j.setSuccess(false);
					return j;
				}
			}
			if (u != null && u.getStatus() != 0) {

				if(u.getDeleteFlag()==1){
					j.setMsg(mutiLangService.getLang("common.username.or.password.error"));
					j.setSuccess(false);
					return j;
				}

				if("2".equals(u.getUserType())){
					j.setMsg(mutiLangService.getLang("common.user.interfaceUser"));
					j.setSuccess(false);
					return j;
				}

				
				// 处理用户有多个组织机构的情况，以弹出框的形式让用户选择
				Map<String, Object> attrMap = new HashMap<String, Object>();
				j.setAttributes(attrMap);
				
				ResourceUtil.getSessionUser();

				String orgId = req.getParameter("orgId");
				if (oConvertUtils.isEmpty(orgId)) {
					// 没有传组织机构参数，则获取当前用户的组织机构
					Long orgNum = systemService.getCountForJdbcParam("select count(1) from t_s_user_org where user_id = ?",u.getId());
					if (orgNum > 1) {
						attrMap.put("orgNum", orgNum);
						//防止多级报错
						if (u!=null){
							try {
								TSUser t =new TSUser();
								MyBeanUtils.copyBeanNotNull2Bean(u, t);
								t.setUserOrgList(null);
								t.setTSLDivision(null);
								t.setTSLUserTypeEntity(null);
								t.setCurrentDepart(null);
								attrMap.put("user", t);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						Map<String, Object> userOrgMap = systemService.findOneForJdbc("select org_id as orgId from t_s_user_org where user_id=?", u.getId());
						userService.saveLoginUserInfo(req, u, (String) userOrgMap.get("orgId"));
					}
				} else {
					attrMap.put("orgNum", 1);
					userService.saveLoginUserInfo(req, u, orgId);
				}
			} else {
				//用户锁定提醒
				j.setMsg(mutiLangService.getLang("common.lock.user"));
				j.setSuccess(false);
			}
		}
		return j;
	}
	
	/**
	 * 变更在线用户组织
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "changeDefaultOrg")
	@ResponseBody
	public AjaxJson changeDefaultOrg(TSUser user, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attrMap = new HashMap<String, Object>();
		String orgId = req.getParameter("orgId");
		TSUser u = userService.checkUserExits(user);
		if(u == null){
			u = userService.findUniqueByProperty(TSUser.class, "email", user.getUserName());
		}
		if (oConvertUtils.isNotEmpty(orgId)) {
			attrMap.put("orgNum", 1);
			userService.saveLoginUserInfo(req, u, orgId);
		}
		return j;
	}

	/**
	 * 首页信息
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getHomeInfo")
	@ResponseBody
	public ResponseMessage<?> getHomeInfo(HttpServletRequest request) {
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("home_info_00", ResourceUtil.getCacheTypes("home_info_00"));
		resultMap.put("home_info_01", ResourceUtil.getCacheTypes("home_info_01"));
		resultMap.put("home_info_02", ResourceUtil.getCacheTypes("home_info_02"));
		resultMap.put("home_info_03", ResourceUtil.getCacheTypes("home_info_03"));
		resultMap.put("home_info_04", ResourceUtil.getCacheTypes("home_info_04"));
		resultMap.put("home_info_05", ResourceUtil.getCacheTypes("home_info_05"));
		resultMap.put("home_info_06", ResourceUtil.getCacheTypes("home_info_06"));
		return Result.success(resultMap);
	}
	
    /**
	 * 【首页逻辑】 {登录校验成功后才走这个逻辑}
	 * 描述： 获取登录用户的登录机构ID,角色，用户名，首页菜单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "login")
	public String login(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		TSUser user = ResourceUtil.getSessionUser();
		String roles = "";
		if (user != null) {
			log.info(" >>>>>>>>>>>>>>>>>>>>>>>>>>  Login 用户登录成功，初始化Main首页用户信息  （Main 首页加载逻辑）  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				roles += role.getRoleName() + ",";
			}
			if (roles.length() > 0) {
				roles = roles.substring(0, roles.length() - 1);
			}
			
            modelMap.put("roleName", roles.length()>3?roles.substring(0,3)+"...":roles);
            modelMap.put("userName", user.getUserName().length()>5?user.getUserName().substring(0, 5)+"...":user.getUserName());
            modelMap.put("portrait", user.getPortrait());
            //用户当前登录机构
            modelMap.put("currentOrgName", clientManager.getClient().getUser().getCurrentDepart().getDepartname());
			
			SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
			if("fineui".equals(sysTheme.getStyle())|| "ace".equals(sysTheme.getStyle())||"diy".equals(sysTheme.getStyle())||"acele".equals(sysTheme.getStyle())||"hplus".equals(sysTheme.getStyle()) || "adminlte".equals(sysTheme.getStyle())){
				request.setAttribute("menuMap", userService.getFunctionMap(user.getId()));
			}
			//国际化cookie
			Cookie i18n_cookie = new Cookie("i18n_browser_Lang", oConvertUtils.getString(request.getSession().getAttribute("lang")));
			//设置cookie有效期为一个月
			i18n_cookie.setMaxAge(3600*24*30);
			response.addCookie(i18n_cookie);
			
			//ace addOneTab无效问题
			Cookie cookie = new Cookie("JEECGINDEXSTYLE", sysTheme.getStyle());
			//设置cookie有效期为一个月
			cookie.setMaxAge(3600*24*30);
			response.addCookie(cookie);
			//zIndex索引问题
			Cookie zIndexCookie = new Cookie("ZINDEXNUMBER", "1990");
			zIndexCookie.setMaxAge(3600*24);//一天
			zIndexCookie.setPath("/");
			response.addCookie(zIndexCookie);
			/*
			 * 单点登录 - 登录需要跳转登录前页面，自己处理 ReturnURL 使用 
			 * HttpUtil.decodeURL(xx) 解码后重定向
			 */
			String returnURL = (String)request.getSession().getAttribute("ReturnURL");
			log.info("login 资源路径returnURL："+returnURL);
			if(StringUtils.isNotEmpty(returnURL)){
				SSOToken st = new SSOToken(request);
				st.setId(UUID.randomUUID().getMostSignificantBits());
				st.setUid(user.getUserName());
				st.setType(1);
				//request.setAttribute(SSOConfig.SSO_COOKIE_MAXAGE, maxAge);
				// 可以动态设置 Cookie maxAge 超时时间 ，优先于配置文件的设置，无该参数 - 默认读取配置文件数据 。
				//  maxAge 定义：-1 浏览器关闭时自动删除 0 立即删除 120 表示Cookie有效期2分钟(以秒为单位)
				//request.setAttribute(SSOConfig.SSO_COOKIE_MAXAGE, 60);
				SSOHelper.setSSOCookie(request, response, st, true);
				returnURL = HttpUtil.decodeURL(returnURL);
				log.info("login 资源路径returnURL："+returnURL);
				request.getSession().removeAttribute("ReturnURL");
				try {
					response.sendRedirect(returnURL);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			String indexPath=sysTheme.getIndexPath();
			return indexPath;
		} else {
			//单点登录 - 返回链接
			String returnURL = (String)request.getSession().getAttribute("ReturnURL");
			if(StringUtils.isNotEmpty(returnURL)){
				request.setAttribute("ReturnURL", returnURL);
			}
			return "login/hz_login";
		}
	}
	//main/ace_main
	//main/shortcut_main
	//main/main
	//main/sliding_main
	//main/hplus_main
	//main/fineui_main
	//main/adminlte_main
	

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		TSUser user = ResourceUtil.getSessionUser();
		try {
			systemService.addLog("用户" + user!=null?user.getUserName():"" + "已退出", Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			LogUtil.error(e.toString());
		}
		clientManager.removeClinet(session.getId());
		session.invalidate();
		ModelAndView modelAndView = new ModelAndView(new RedirectView("loginController.do?login"));
		return modelAndView;
	}

	/**
	 * 菜单跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "left")
	public ModelAndView left(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUser();
		HttpSession session = ContextHolderUtils.getSession();
        ModelAndView modelAndView = new ModelAndView();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
            modelAndView.setView(new RedirectView("loginController.do?login"));
		}else{
            modelAndView.setViewName("main/left");
            Object aa=userService.getFunctionMap(user.getId());
            request.setAttribute("menuMap", aa);
        }
		return modelAndView;
	}

	/**
	 * 首页菜单搜索框自动补全
	 */
	@RequestMapping(params = "getAutocomplete",method ={RequestMethod.GET, RequestMethod.POST})
	public void getAutocomplete(HttpServletRequest request, HttpServletResponse response) {
		String searchVal = request.getParameter("q");
		//获取到session中的菜单列表
		HttpSession session = ContextHolderUtils.getSession();
		Client client = clientManager.getClient(session.getId());
		//获取到的是一个map集合
		Map<Integer, List<TSFunction>> map=client.getFunctionMap();
		//声明list用来存储菜单
		List<TSFunction>autoList = new ArrayList<TSFunction>();
		//循环map集合取到菜单
		for(int t=0;t<map.size();t++){
			//根据map键取到菜单的TSFuction 用List接收
			List<TSFunction> list = map.get(t);
			//循环List取到TSFuction中的functionname
			for(int i =0;i<list.size();i++){
				//由于functionname中的一些参数没有被国际化，所以还是字母，需要MutiLangUtil中的getLang()方法来
				String name= MutiLangUtil.getLang(list.get(i).getFunctionName());
				if(name.indexOf(searchVal)!= -1 ){
					TSFunction  ts =new TSFunction();
					ts.setFunctionName(MutiLangUtil.getLang(list.get(i).getFunctionName()));
					autoList.add(ts);
				}
			}
		}		
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(JSONHelper.listtojson(new String[]{"functionName"},1,autoList));
            response.getWriter().flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			try {
				response.getWriter().close();
			} catch (IOException e) {
			}
		}
	}
	/**
	 * 获取请求路径
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getUrlpage")
	@ResponseBody
	public String getUrlpage(HttpServletRequest request,HttpServletResponse response) {
		String urlname = request.getParameter("urlname");
		HttpSession session = ContextHolderUtils.getSession();
		Client client = clientManager.getClient(session.getId());
		Map<Integer, List<TSFunction>> map=client.getFunctionMap();
		List<TSFunction>autoList = new ArrayList<TSFunction>();
		for(int t=0;t<map.size();t++){
			List<TSFunction> list = map.get(t);
			for(int i =0;i<list.size();i++){
				String funname=MutiLangUtil.getLang(list.get(i).getFunctionName());
				if(urlname.equals(funname)){
					TSFunction ts =new TSFunction();
					ts.setFunctionUrl(list.get(i).getFunctionUrl());
					autoList.add(ts);
				}
			}
		}
		if(autoList.size()==0){
			return null;
		}else{
			String name =autoList.get(0).getFunctionUrl();
			return name;
		}
		
	}
	
	/**
	 * 跳转到密码重置界面
	 * @param key
	 * @return
	 */
	@RequestMapping(params = "goResetPwd")
	public ModelAndView goResetPwd(String key){
		return new ModelAndView("login/resetPwd")
				.addObject("key", key);
	}
	
	/**
	 * 密码重置
	 * @param key
	 * @param password
	 * @return
	 */
	@RequestMapping(params = "resetPwd")
	@ResponseBody
	public AjaxJson resetPwd(String key,String password){
		AjaxJson ajaxJson = new AjaxJson();
		TSPasswordResetkey passwordResetkey = systemService.get(TSPasswordResetkey.class, key);
		Date now = new Date();
		if(passwordResetkey != null && passwordResetkey.getIsReset() != 1 && (now.getTime() - passwordResetkey.getCreateDate().getTime()) < 1000*60*60*3){
			TSUser user = systemService.findUniqueByProperty(TSUser.class, "userName", passwordResetkey.getUsername());
			user.setPassword(PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt()));
			systemService.updateEntitie(user);
			passwordResetkey.setIsReset(1);
			systemService.updateEntitie(passwordResetkey);
			ajaxJson.setMsg("密码重置成功");
		}else{
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("无效重置密码KEY");
		}
		
		return ajaxJson;
	}
	
	/**
	 * 跳转到密码重置填写邮箱界面
	 * @return
	 */
	@RequestMapping(params="goResetPwdMail")
	public ModelAndView goResetPwdMail(){
		return new ModelAndView("login/goResetPwdMail");
	}
	
	/**
	 * 发送重置密码邮件
	 * @return
	 */
	@RequestMapping(params="sendResetPwdMail")
	@ResponseBody
	public AjaxJson sendResetPwdMail(String email,HttpServletRequest request){
		AjaxJson ajaxJson = new AjaxJson();
		try {
			if(StringUtils.isEmpty(email)){
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("邮件地址不能为空");
				return ajaxJson;
			}
			TSUser user = systemService.findUniqueByProperty(TSUser.class, "email", email);
			if(user == null){
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("用户名对应的用户信息不存在");
				return ajaxJson;
			}
			String hql = "from TSPasswordResetkey bean where bean.username = ? and bean.isReset = 0 order by bean.createDate desc limit 1";
			List<TSPasswordResetkey> resetKeyList = systemService.findHql(hql,user.getUserName());
			if(resetKeyList != null && !resetKeyList.isEmpty()){
				TSPasswordResetkey resetKey = resetKeyList.get(0);
				Date now = new Date();
				if(resetKey.getEmail().equals(email) && (now.getTime() - resetKey.getCreateDate().getTime()) < (1000*60*60*3 - 1000*60*5)){
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("已发送重置密码邮件，请稍候再次尝试发送");
					return ajaxJson;
					
				}
			}
			TSPasswordResetkey passwordResetKey = new TSPasswordResetkey();
			passwordResetKey.setEmail(email);
			passwordResetKey.setUsername(user.getUserName());
			passwordResetKey.setCreateDate(new Date());
			passwordResetKey.setIsReset(0);
			userService.save(passwordResetKey);
			String content = ResourceUtil.getConfigByName("resetpwd.mail.content");
			if(content.indexOf("${username}") > -1){
				content = content.replace("${username}", user.getUserName());
			}
			String url = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort() + request.getContextPath() +"/loginController.do?goResetPwd&key=" + passwordResetKey.getId();
			
			//配置邮件模板参数
			Map<String, Object> mailConfig = new HashMap<String, Object>();
			mailConfig.put("title", ResourceUtil.getConfigByName("resetpwd.mail.title"));
			mailConfig.put("content",content);
			mailConfig.put("url",url);
			mailConfig.put("commentUrl","http://www.jeecg.org");
			
			String mailContent = new FreemarkerHelper().parseTemplate("export/mail/password_reset.ftl", mailConfig);
			
			MailUtil.sendEmail(ResourceUtil.getConfigByName("mail.smtpHost"), email,"邮箱重置密码", mailContent, ResourceUtil.getConfigByName("mail.sender"), ResourceUtil.getConfigByName("mail.user"), ResourceUtil.getConfigByName("mail.pwd"));
			ajaxJson.setMsg("成功发送密码重置邮件");

			
		} catch (Exception e) {
			if("javax.mail.AuthenticationFailedException".equals(e.getClass().getName())){
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("发送邮件失败：邮箱账号信息设置错误" );
				log.error("重置密码发送邮件失败：邮箱账号信息设置错误",e);
			}else{
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("发送邮件失败：" + e.getMessage());
				log.error("发送邮件失败：" + e.getMessage(),e);
			}
				
		}
		return ajaxJson;
	}
	
	@RequestMapping(params = "goPwdInit")
	public String goPwdInit() {
		return "login/pwd_init";
	}

    /**
	 * 首页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request) {
		
		SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
		//ACE ACE2 DIY时需要在home.jsp头部引入依赖的js及css文件
		if("ace".equals(sysTheme.getStyle())||"diy".equals(sysTheme.getStyle())||"acele".equals(sysTheme.getStyle())){
			request.setAttribute("show", "1");
		} else {//default及shortcut不需要引入依赖文件，所有需要屏蔽
			request.setAttribute("show", "0");
		}
		return new ModelAndView("main/home");
	}
	
    /**
	 * ACE首页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "acehome")
	public ModelAndView acehome(HttpServletRequest request) {
		
		SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
		//ACE ACE2 DIY时需要在home.jsp头部引入依赖的js及css文件
		if("ace".equals(sysTheme.getStyle())||"diy".equals(sysTheme.getStyle())||"acele".equals(sysTheme.getStyle())){
			request.setAttribute("show", "1");
		} else {//default及shortcut不需要引入依赖文件，所有需要屏蔽
			request.setAttribute("show", "0");
		}
		return new ModelAndView("main/acehome");
	}
	
	/**
	 * HPLUS首页跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "hplushome")
	public ModelAndView hplushome(HttpServletRequest request) {
		return new ModelAndView("main/hplushome");
	}
	
	/**
	 * fineUI首页跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "fineuiHome")
	public ModelAndView fineuiHome(HttpServletRequest request) {
		return new ModelAndView("main/fineui_home");
	}
	
	/**
	 * 无权限页面提示跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "noAuth")
	public ModelAndView noAuth(HttpServletRequest request) {
		return new ModelAndView("common/noAuth");
	}
	
	/**
	 * @Title: top
	 * @Description: bootstrap头部菜单请求
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(params = "top")
	public ModelAndView top(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUser();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(
					new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", userService.getFunctionMap(user.getId()));
		return new ModelAndView("main/bootstrap_top");
	}
	
	/**
	 * @Title: top
	 * @author gaofeng
	 * @Description: shortcut头部菜单请求
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(params = "shortcut_top")
	public ModelAndView shortcut_top(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUser();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(
					new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", userService.getFunctionMap(user.getId()));
		return new ModelAndView("main/shortcut_top");
	}
	
	/**
	 * @Description: shortcut头部菜单一级菜单列表，并将其用ajax传到页面，实现动态控制一级菜单列表
	 * @return AjaxJson
	 * @throws
	 */
    @RequestMapping(params = "primaryMenu")
    @ResponseBody
	public String getPrimaryMenu() {
		List<TSFunction> primaryMenu = userService.getFunctionMap(ResourceUtil.getSessionUser().getId()).get(0);
		//Shortcut一级菜单图标个性化设置（TODO 暂时写死）
        String floor = userService.getShortcutPrimaryMenu(primaryMenu);
		return floor;
	}

	/**
	 * @Description: shortcut头部菜单二级菜单列表，并将其用ajax传到页面，实现动态控制二级菜单列表
	 * @return AjaxJson
	 * @throws
	 */
	@RequestMapping(params = "primaryMenuDiy")
	@ResponseBody
	public String getPrimaryMenuDiy() {
		//取二级菜单
		List<TSFunction> primaryMenu = userService.getFunctionMap(ResourceUtil.getSessionUser().getId()).get(1);
		//Shortcut二级菜单图标个性化设置（TODO 暂时写死）
		String floor = userService.getShortcutPrimaryMenuDiy(primaryMenu);
		return floor;
	}
	/**
	 * 云桌面返回：用户权限菜单
	 */
	@RequestMapping(params = "getPrimaryMenuForWebos")
	@ResponseBody
	public AjaxJson getPrimaryMenuForWebos() {
		AjaxJson j = new AjaxJson();
		//将菜单加载到Session，用户只在登录的时候加载一次
		Object getPrimaryMenuForWebos =  ContextHolderUtils.getSession().getAttribute("getPrimaryMenuForWebos");
		if(oConvertUtils.isNotEmpty(getPrimaryMenuForWebos)){
			j.setMsg(getPrimaryMenuForWebos.toString());
		}else{
			String PMenu = ListtoMenu.getWebosMenu(userService.getFunctionMap(ResourceUtil.getSessionUser().getId()));
			ContextHolderUtils.getSession().setAttribute("getPrimaryMenuForWebos", PMenu);
			j.setMsg(PMenu);
		}
		return j;
	}

	/**
	 * ACE登录界面
	 * @return
	 */
	@RequestMapping(params = "login3")
	public String login3(){
		return "login/login3";
	}

	/**
	 * AdminLTE返回：用户权限菜单
	 */
	@RequestMapping(params = "getPrimaryMenuForAdminlte", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson getPrimaryMenuForAdminlte(String functionId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {

			//List<TSFunction> functions = this.systemService.findByProperty(TSFunction.class, "TSFunction.id", functionId);
			String userid = ResourceUtil.getSessionUser().getId();
			List<TSFunction> functions = userService.getSubFunctionList(userid, functionId);

			JSONArray jsonArray = new JSONArray();
			if(functions != null && functions.size() > 0) {
				for (TSFunction function : functions) {
					JSONObject jsonObjectInfo = new JSONObject();
					jsonObjectInfo.put("id", function.getId());

					jsonObjectInfo.put("text",oConvertUtils.getString(MutiLangUtil.getLang(function.getFunctionName())));
					jsonObjectInfo.put("url",oConvertUtils.getString(function.getFunctionUrl()));
					jsonObjectInfo.put("targetType", "iframe-tab");
					jsonObjectInfo.put("icon", "fa " + oConvertUtils.getString(function.getFunctionIconStyle()));

					jsonObjectInfo.put("children", getChildOfAdminLteTree(function));
					jsonArray.add(jsonObjectInfo);
				}
			}
			j.setObj(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}
	
	public JSONArray getChildOfAdminLteTree(TSFunction function) {
		JSONArray jsonArray = new JSONArray();
		List<TSFunction> functions = this.systemService.findByProperty(TSFunction.class, "TSFunction.id", function.getId());
		if(functions != null && functions.size() > 0) {
			for (TSFunction tsFunction : functions) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", tsFunction.getId());
				jsonObject.put("text", MutiLangUtil.getLang(tsFunction.getFunctionName()));
				jsonObject.put("url", tsFunction.getFunctionUrl());
				jsonObject.put("targetType", "iframe-tab");
				jsonObject.put("icon", "fa " + tsFunction.getFunctionIconStyle());
				jsonObject.put("children", getChildOfAdminLteTree(tsFunction));
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray;
	}
	
	/**
	 * AdminLTE首页跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "adminlteHome")
	public ModelAndView adminlteHome(HttpServletRequest request) {
		return new ModelAndView("main/adminlte_home");
	}

	
	@RequestMapping(params = "getAPictureInfo")
	@ResponseBody
	public ResponseMessage<?> getAPictureInfo(HttpServletRequest request) {
		String sql=" select name,river_lake_type,mapCenterLngLat,division_divisionCode,"
				+ " (select count(*) from t_sl_base_river_lake_patrol p where p.base_river_lake_id=d.id) as count0 "
				+ " from t_sl_base_river_lake d where LENGTH(d.mapCenterLngLat)>0 and LENGTH(d.mapZoom)>0 "
				+ " and d.river_lake_type<>21"
				+ " union "
				+ " select f.monitoringSection as name,d.river_lake_type,d.mapCenterLngLat,d.division_divisionCode,"
				+ " f.waterQuality as count0 "
				+ " from t_sl_base_river_lake d left "
				+ " join t_sl_rl_project_water_function f on d.id=f.id "
				+ " where LENGTH(d.mapCenterLngLat)>0 and LENGTH(d.mapZoom)>0 "
				+ " and d.river_lake_type=21 and  LENGTH(f.monitoringSection )>0 ";
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("home_info_01", ResourceUtil.getCacheTypes("home_info_01"));
		resultMap.put("info_data", systemService.findForJdbc(sql));
		return Result.success(resultMap);
	}
	
	 /* 父级权限列表
	 * 
	 * @param request
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "setPFunction")
	@ResponseBody
	public List<ComboTree> setPFunction(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSLDivisionEntity.class);
		if(null != request.getParameter("selfId")){
			cq.notEq("id", request.getParameter("selfId"));
		}
		if (comboTree.getId() != null) {
			cq.eq("parent.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("parent");
		}

		cq.addOrder("divisionOrder", SortDirection.asc);

		cq.add();
		List<TSLDivisionEntity> divisionsList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("code", "name", "childrenList");

		TSLDivisionEntity defaultDivision = new TSLDivisionEntity();
		defaultDivision.setId("");
		defaultDivision.setName("请选择行政区划");
		divisionsList.add(0, defaultDivision);

		comboTrees = systemService.ComboTree(divisionsList, comboTreeModel, null, true);
		return comboTrees;

	}
}