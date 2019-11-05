package com.saili.hzz.app.controller;

import com.saili.hzz.core.util.*;
import com.saili.hzz.web.system.pojo.base.InterroleUserEntity;
import com.saili.hzz.web.system.pojo.base.TSNotice;
import com.saili.hzz.web.system.pojo.base.TSType;
import com.saili.hzz.web.system.pojo.base.TSUser;
import com.saili.hzz.core.entity.*;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.util.JSONPObject;
import com.saili.hzz.core.common.controller.BaseController;
import com.saili.hzz.core.common.exception.BusinessException;
import com.saili.hzz.core.common.hibernate.qbc.CriteriaQuery;
import com.saili.hzz.core.common.model.common.UploadFile;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.core.common.service.CommonExcelServiceI;
import com.saili.hzz.core.enums.SysThemesEnum;
import com.saili.hzz.core.online.def.CgReportConstant;
import com.saili.hzz.core.online.exception.CgReportNotFoundException;
import com.saili.hzz.core.online.util.CgReportQueryParamUtil;
import com.saili.hzz.core.online.util.FreemarkerHelper;
import com.saili.hzz.jwt.def.JwtConstants;
import com.saili.hzz.jwt.service.TokenManager;
import com.saili.hzz.jwt.util.ResponseMessage;
import com.saili.hzz.jwt.util.Result;
import com.saili.hzz.tag.core.easyui.TagUtil;
import com.saili.hzz.tag.vo.datatable.SortDirection;
import com.saili.hzz.web.cgform.entity.upload.CgUploadEntity;
import com.saili.hzz.web.cgform.service.upload.CgUploadServiceI;
import com.saili.hzz.web.cgreport.service.core.CgReportServiceI;
import com.saili.hzz.web.graphreport.service.core.GraphReportServiceI;
import com.saili.hzz.web.system.service.MutiLangServiceI;
import com.saili.hzz.web.system.service.SystemService;
import com.saili.hzz.web.system.service.UserService;
import com.saili.hzz.web.system.sms.entity.TSSmsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

@Controller
@RequestMapping("/app/slCrossController/")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class SlCrossController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SlCrossController.class);
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private MutiLangServiceI mutiLangService;
	@Autowired
	private UserService userService;
	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private CgUploadServiceI cgUploadService;
	@Autowired
	private GraphReportServiceI graphReportService;
	@Autowired
	private CgReportServiceI cgReportService;
	@Autowired
	private CommonExcelServiceI cgReportExcelService;

	@RequestMapping("doComplaintReturn")
	@ResponseBody
	public AjaxJson doComplaintReturn(@RequestBody Map<String,String> reqbody,HttpServletRequest request, HttpServletResponse response) {
		String pid = request.getParameter("parent.code");
		String message = null;
		AjaxJson j = new AjaxJson();
		//TSLBaseRiverLakePatrolEvent entity =  systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,  id);
		message = "回访成功";
		try{
			JSONObject jo = new JSONObject();
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE,"TSLBaseRiverLakePatrolEvent");
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE_TYPE,"doComplaintReturn");
			j.setSuccess(true);
		}catch(Exception e){
			j.setSuccess(false);
			e.printStackTrace();
			message = "回访失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/*
	@RequestMapping("toLogin")
	public String toLogin(HttpServletRequest request) {
		String returnURL = request.getParameter("ReturnURL");
		request.setAttribute("ReturnURL", returnURL);
		return "login/goResetPwdMail";
	}
	*/
	//http://192.168.0.24:8080/slRiverManager/slCrossController.do?foo1&callback=jsonpcallback
	String stSessionName="TestName";
	@RequestMapping("foo")
	@ResponseBody
	public Object foo(String callback,HttpSession httpSession,HttpServletResponse response) {
	    try {
	    	if (StringUtil.isEmpty(callback)) callback="";
	    	httpSession.setAttribute(stSessionName, "bbsdfsdf");
	        // 构造返回的数据data
	        Map<String, String> data = new HashMap<String, String>();
	        data.put("name", "LinHenk");
	        //Result.success(data);
	        //Result.error(data);
	        // 判断请求是否是josnp请求
	        if (StringUtils.isNotBlank(callback)) {
	            // jsonp请求
	            //参数一（String类型）：回调函数名，参数二（Object类型）：需要返回的数据
	        	//jsonpcallback({"message":"成功","respCode":"0","data":{"name":"LinHenk"},"ok":true})
	            JSONPObject jsonpObject = new JSONPObject(callback, Result.success(data));
	            //jsonpcallback({"message":"121212","respCode":"-1","data":null,"ok":false})
	        	//JSONPObject jsonpObject = new JSONPObject(callback, Result.error("报错了!!!"));
	            return jsonpObject;
	        }
	        // 非jsonp请求
	        return Result.success(data);
	        //return Result.error("121212");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	@RequestMapping("foo1")
	@ResponseBody
	public Object foo1(@RequestBody Map<String,String> reqbody,String callback,HttpSession httpSession,HttpServletResponse response) {
	    try {
	    	if (StringUtil.isEmpty(callback)) callback="";
	    	String st2=StringUtil.formatEmpty(httpSession.getAttribute(stSessionName));
	        // 构造返回的数据data
	        Map<String, String> data = new HashMap<String, String>();
	        data.put("name", "LinHenk");
	        //Result.success(data);
	        //Result.error(data);
	        // 判断请求是否是josnp请求
	        if (StringUtils.isNotBlank(callback)) {
	            // jsonp请求
	            //参数一（String类型）：回调函数名，参数二（Object类型）：需要返回的数据
	        	//jsonpcallback({"message":"成功","respCode":"0","data":{"name":"LinHenk"},"ok":true})
	            JSONPObject jsonpObject = new JSONPObject(callback, Result.success(data));
	            //jsonpcallback({"message":"121212","respCode":"-1","data":null,"ok":false})
	        	//JSONPObject jsonpObject = new JSONPObject(callback, Result.error("报错了!!!"));
	            return jsonpObject;
	        }
	        // 非jsonp请求
	        return Result.success(data);
	        //return Result.error("121212");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public final static long  keeptime=1800000;
	
	@RequestMapping("getHomeInfo")
	@ResponseBody
	public Object getHomeInfo(@RequestBody Map<String,String> reqbody,String callback,HttpServletRequest req,HttpServletResponse response) {
		try {
			Claims claims=(Claims)req.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)req.getAttribute(JwtConstants.CURRENT_USER_NAME);
			
			callback=StringUtil.formatEmpty(callback);
			username=StringUtil.formatEmpty(username);
			logger.info("[username==="+username+"===]");
			if (StringUtil.isNotEmpty(username)){
				//JSONObject json = JSONObject.fromObject(st1);

				StringBuilder sb = new StringBuilder();
				sb.append(" select a.notice_title,a.id,"+
						  " (select a2.realpath from cgform_uploadfiles a1 inner join t_s_attachment a2 on a1.id=a2.ID where a1.CGFORM_ID=a.id and CGFORM_FIELD='title_picture_file' LIMIT 1) as realpath"+
						  " from t_s_notice a where a.app_visible='10' order by a.create_time");
				List<Map<String, Object>> objMapList = systemService.findForJdbc(sb.toString());
				
				JSONArray jsonArray=new JSONArray();
				if(objMapList!=null && objMapList.size()>0){
					for(Map<String, Object> objMap:objMapList){
						String id=StringUtil.formatEmpty(objMap.get("id"));
						String notice_title=StringUtil.formatEmpty(objMap.get("notice_title"));
						String realpath=StringUtil.formatEmpty(objMap.get("realpath"));
						
						
						if (StringUtil.isNotEmpty(realpath)){
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("id", id);
							jsonObject.put("text", notice_title);
							jsonObject.put("src", realpath);
							jsonArray.add(jsonObject);
						}
					}
				}
				return new JSONPObject(callback, Result.success(jsonArray));
			}else{
				throw new Exception("没有登录");
			}
		} catch (Exception e) {
	        e.printStackTrace();
	        return new JSONPObject(callback, Result.error(e.getMessage()));
	    }
	}
	
	//getHomeInfo
	@RequestMapping("getRiverInfoByKeywords")
	@ResponseBody
	public Object getRiverInfoByKeywords(@RequestBody Map<String,String> reqbody,String callback,HttpServletRequest req,HttpServletResponse response) {
		try {
			Claims claims=(Claims)req.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)req.getAttribute(JwtConstants.CURRENT_USER_NAME);
			
			callback=StringUtil.formatEmpty(callback);
			username=StringUtil.formatEmpty(username);
			logger.info("[username==="+username+"===]");
			if (StringUtil.isNotEmpty(username)){
				//JSONObject json = JSONObject.fromObject(st1);
				String keywords=oConvertUtils.getString(reqbody.get("keywords"));
				
				StringBuilder sb = new StringBuilder();
				sb.append(" select id,code,name from t_sl_base_river_lake where code like '%"+keywords+"%' or name like '%"+keywords+"%'");
				List<Map<String, Object>> objMapList = systemService.findForJdbc(sb.toString());
				
				JSONArray jsonArray=new JSONArray();
				if(objMapList!=null && objMapList.size()>0){
					for(Map<String, Object> objMap:objMapList){
						String id=StringUtil.formatEmpty(objMap.get("id"));
						String code=StringUtil.formatEmpty(objMap.get("code"));
						String name=StringUtil.formatEmpty(objMap.get("name"));
						
						
						if (StringUtil.isNotEmpty(name)){
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("id", id);
							jsonObject.put("code", code);
							jsonObject.put("name", name);
							jsonArray.add(jsonObject);
						}
					}
				}
				return new JSONPObject(callback, Result.success(jsonArray));
			}else{
				throw new Exception("没有登录");
			}
		} catch (Exception e) {
	        e.printStackTrace();
	        return new JSONPObject(callback, Result.error(e.getMessage()));
	    }
	}
	@RequestMapping("getNewsInfo")
	@ResponseBody
	public Object getNewsInfo(@RequestBody Map<String,String> reqbody,String callback,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (StringUtil.isEmpty(callback)) callback="";
			//String userInfoStr=StringUtil.formatEmpty(httpSession.getAttribute(sessionUserInfo));
			Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			if (StringUtil.isNotEmpty(username)){
				//String id=oConvertUtils.getString(request.getParameter("id"));
				String id=oConvertUtils.getString(reqbody.get("id"));
				if (StringUtil.isNotEmpty(id)) {
					TSNotice tsNotice =  systemService.getEntity(TSNotice.class,  id);
					List<CgUploadEntity> cgUploadEntitys =systemService.findByProperty(CgUploadEntity.class, "cgformId", id);
					
					
					//JSONObject json10 = JSONObject.fromObject(tsNotice);
					JSONObject json11 = new JSONObject();
					json11.put("noticeContent", tsNotice.getNoticeContent());
					json11.put("noticeTitle", tsNotice.getNoticeTitle());
					//String st111=DateUtils.date2Str(tsNotice.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
					
					String st111=StringUtil.getTimeStateNew(tsNotice.getCreateTime().getTime()+"");
					json11.put("createTime", st111);
					String userId=tsNotice.getCreateUser();
					if (StringUtil.isNotEmpty(userId)){
						TSUser user=systemService.get(TSUser.class, userId);
						json11.put("user",user.getUserName() );
					}else{
						json11.put("user","管理员");
					}
					JSONArray jsonArray = new JSONArray();
		            for(CgUploadEntity a : cgUploadEntitys){
		                JSONObject json12= new JSONObject();
		                json12.put("realpath", a.getRealpath());
		                jsonArray.add(json12);
		            }
		            
					JSONObject attrMap = new JSONObject();
					
		            attrMap.put("tsNotice", json11);		            
					attrMap.put("cgUploadEntity", jsonArray);
					
					return new JSONPObject(callback, Result.success(attrMap));
				}else{
					throw new Exception("没有id");
				}
			}else{
				throw new Exception("没有登录");
			}
		}catch (Exception e) {
	        e.printStackTrace();
	        return new JSONPObject(callback, Result.error(e.getMessage()));
	    }
	}
	
	@RequestMapping("doDelProblem")
	@ResponseBody
	public Object doDelProblem(@RequestBody Map<String,String> reqbody,String callback,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (StringUtil.isEmpty(callback)) callback="";
			Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			if (StringUtil.isNotEmpty(username)){
				String id=oConvertUtils.getString(reqbody.get("id"));
				if (StringUtil.isNotEmpty(id)) {
					TSLBaseRiverLakePatrolEvent entity=systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,id);
					if (entity!=null){
						systemService.delete(entity);
					}else {
						throw new Exception("没有找到!");
					}
				}else {
					throw new Exception("没有找到!");
				}
			}else {
				throw new Exception("没有找到!");
			}
			JSONObject attrMap = new JSONObject();
			attrMap.put("message", "删除成功");
			return new JSONPObject(callback, Result.success(attrMap));
		}catch (Exception e) {
			e.printStackTrace();
			return new JSONPObject(callback, Result.error(e.getMessage()));
	    }
	}
	
	@RequestMapping("doSubmitProblem")
	@ResponseBody
	public Object doSubmitProblem(@RequestBody Map<String,String> reqbody,String callback,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (StringUtil.isEmpty(callback)) {
				callback="";
			}
			Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			if (StringUtil.isNotEmpty(username)){
				JSONObject aa=tokenManager.getUserInfo(username);
				String userId=aa.get("userId").toString();
				
				String id=oConvertUtils.getString(reqbody.get("id"));
				if (StringUtil.isNotEmpty(id)) {
					TSLBaseRiverLakePatrolEvent entity=systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,id);
					if (entity!=null){
						JSONObject jo = new JSONObject();
						jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE,"TSLBaseRiverLakePatrolEvent");
						jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE_TYPE,"processComplaint");
						jo.put("id",id);
						jo.put("status","21");
						jo.put("userid",userId);
						systemService.updateByJSONObject(jo);
					}else {
						throw new Exception("没有找到!");
					}
				}else {
					throw new Exception("没有找到!");
				}
			}else {
				throw new Exception("没有找到!");
			}
			JSONObject attrMap = new JSONObject();
			attrMap.put("message", "送审成功");
			return new JSONPObject(callback, Result.success(attrMap));
		}catch (Exception e) {
			e.printStackTrace();
			return new JSONPObject(callback, Result.error(e.getMessage()));
	    }
	}
	
	@RequestMapping("getProblemInfo")
	@ResponseBody
	public Object getProblemInfo(@RequestBody Map<String,String> reqbody,String callback,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (StringUtil.isEmpty(callback)) callback="";
			//String userInfoStr=StringUtil.formatEmpty(httpSession.getAttribute(sessionUserInfo));
			Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			if (StringUtil.isNotEmpty(username)){
				//String id=oConvertUtils.getString(request.getParameter("id"));
				String id=oConvertUtils.getString(reqbody.get("id"));
				if (StringUtil.isNotEmpty(id)) {
					TSLBaseRiverLakePatrolEvent domain =  systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,  id);
					List<CgUploadEntity> cgUploadEntitys =systemService.findByProperty(CgUploadEntity.class, "cgformId", id);
					//JSONObject json10 = JSONObject.fromObject(tsNotice);
					JSONObject json11 = new JSONObject();
					json11.put("name", domain.getName());
					json11.put("code", domain.getCode());
					json11.put("lakecode", domain.getTslBaseRiverLake().getCode());
					json11.put("lakename", domain.getTslBaseRiverLake().getName());
					//String st111=DateUtils.date2Str(tsNotice.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
					
					String st111=StringUtil.getTimeStateNew(domain.getCreateDate().getTime()+"");
					json11.put("createTime", st111);
					String usercode=domain.getCreateBy();
					if (StringUtil.isNotEmpty(usercode)){
						TSUser user=systemService.findUniqueByProperty(TSUser.class, "userName",usercode);
						json11.put("user",user.getUserName() );
					}else{
						json11.put("user","管理员");
					}
					
					ResourceUtil.getCacheTypesByDictionary("eventstatus",domain.getEventStatus(),json11);
	        		ResourceUtil.getCacheTypesByDictionary("questtype",domain.getQuestType(),json11);
	        		ResourceUtil.getCacheTypesByDictionary("sourcetype",domain.getSourceType(),json11);
	        		
	        		JSONArray jsonArray = new JSONArray();
		            for(CgUploadEntity a : cgUploadEntitys){
		                JSONObject json12= new JSONObject();
		                json12.put("realpath", a.getRealpath());
		                jsonArray.add(json12);
		            }
					JSONObject attrMap = new JSONObject();
					
		            attrMap.put("domain", json11);		            
		            attrMap.put("cgUploadEntity", jsonArray);
		            
					return new JSONPObject(callback, Result.success(attrMap));
				}else{
					throw new Exception("没有id");
				}
			}else{
				throw new Exception("没有登录");
			}
		}catch (Exception e) {
	        e.printStackTrace();
	        return new JSONPObject(callback, Result.error(e.getMessage()));
	    }
	}
	
	@RequestMapping("getRiverInfo")
	@ResponseBody
	public Object getRiverInfo(@RequestBody Map<String,String> reqbody,String callback,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (StringUtil.isEmpty(callback)) callback="";
			//String userInfoStr=StringUtil.formatEmpty(httpSession.getAttribute(sessionUserInfo));
			Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			if (StringUtil.isNotEmpty(username)){
				//String id=oConvertUtils.getString(request.getParameter("id"));
				String id=oConvertUtils.getString(reqbody.get("id"));
				if (StringUtil.isNotEmpty(id)) {
					TSLBaseRiverLake domain =  systemService.getEntity(TSLBaseRiverLake.class,  id);
					List<CgUploadEntity> cgUploadEntitys =systemService.findByProperty(CgUploadEntity.class, "cgformId", id);
					
					
					//JSONObject json10 = JSONObject.fromObject(tsNotice);
					JSONObject json11 = new JSONObject();
					json11.put("code", domain.getCode());
					json11.put("name", domain.getName());
					//String st111=DateUtils.date2Str(tsNotice.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
					
					TSLDivisionEntity dd=domain.getTSLDivision();
					if (dd!=null){
						json11.put("divisonName", dd.getName());
					}
					TSLBaseRiverLake parent=domain.getParent();
					if (parent!=null){
						json11.put("parentName", parent.getName());
					}
					
					String st1=domain.getMapCenterLngLat();
					//{"lng":117.274908,"lat":34.204217}
					json11.put("mapCenterLngLat", st1);
					try{
						JSONObject j=JSONObject.fromObject(st1);
						String lng=j.get("lng").toString();
						String lat=j.get("lat").toString();
						
						double[] dd11=ECGeoCoordinateTransformUtil.bd09togcj02(Double.parseDouble(lng),Double.parseDouble(lat));
						json11.put("longitude", dd11[0]);
						json11.put("latitude", dd11[1]);
						
					}catch(Exception ex){
						
					}
					
					
					List<TSLBaseRiverLakeUser> roleUser =domain.getBaseRiverLakeUserList();
        			if (roleUser.size() > 0) {
        				String roleName = "";
        				for (TSLBaseRiverLakeUser ru : roleUser) {
        					String userDutys=ru.getRiverLakeChief().getDuty();
        					if(StringUtils.isNotBlank(userDutys)){
        						roleName += ru.getRiverLakeChief().getName() +"("+userDutys+")"+ ",";
        					}else{
        						roleName += ru.getRiverLakeChief().getName() + ",";
        					}
                        }
                        roleName = roleName.substring(0, roleName.length() - 1);
                        json11.put("roleName", roleName);
        			}
        			json11.put("waterArea", domain.getWaterArea());
        			//json11.put("riverLength", domain.getri());
        			
        			
					String st111=StringUtil.getTimeStateNew(domain.getCreateDate().getTime()+"");
					json11.put("createTime", st111);
					String userId=domain.getCreateBy();
					if (StringUtil.isNotEmpty(userId)){
						TSUser user=systemService.findUniqueByProperty(TSUser.class, "userName",userId);
						json11.put("user",user.getUserName() );
					}else{
						json11.put("user","管理员");
					}
					JSONArray jsonArray = new JSONArray();
		            for(CgUploadEntity a : cgUploadEntitys){
		                JSONObject json12= new JSONObject();
		                json12.put("realpath", a.getRealpath());
		                jsonArray.add(json12);
		            }
		            
					JSONObject attrMap = new JSONObject();
					
		            attrMap.put("domain", json11);		            
					attrMap.put("cgUploadEntity", jsonArray);
					
					return new JSONPObject(callback, Result.success(attrMap));
				}else{
					throw new Exception("没有id");
				}
			}else{
				throw new Exception("没有登录");
			}
		}catch (Exception e) {
	        e.printStackTrace();
	        return new JSONPObject(callback, Result.error(e.getMessage()));
	    }
	}
	
	@RequestMapping("doUpdatePassword")
	@ResponseBody
	public Object doUpdatePassword(@RequestBody Map<String,String> reqbody,String callback,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (StringUtil.isEmpty(callback)) callback="";
			//String userInfoStr=StringUtil.formatEmpty(httpSession.getAttribute(sessionUserInfo));
			Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			if (StringUtil.isNotEmpty(username)){
				JSONObject aa=tokenManager.getUserInfo(username);
				String userId=aa.get("userId").toString();
				//String userOrgId=aa.get("userOrgId").toString();
				String password=oConvertUtils.getString(reqbody.get("password01"));
				String newpassword=oConvertUtils.getString(reqbody.get("password02"));
				if (StringUtil.isNotEmpty(password)&&StringUtil.isNotEmpty(newpassword)) {
					TSUser user=systemService.getEntity(TSUser.class,userId);
					logger.info("["+ IpUtil.getIpAddr(request)+"][修改密码] start");
					String pString = PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt());
					if (!pString.equals(user.getPassword())) {
						throw new Exception("密码不正确！");
					}
					try {
						user.setPassword(PasswordUtil.encrypt(user.getUserName(), newpassword, PasswordUtil.getStaticSalt()));
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
					systemService.updateEntitie(user);
					JSONObject attrMap = new JSONObject();
					return new JSONPObject(callback, Result.success(attrMap));
				}else {
					throw new Exception("密码为空");
				}
			}else {
				throw new Exception("没有");
			}
		}catch (Exception e) {
	        e.printStackTrace();
	        return new JSONPObject(callback, Result.error("错误!"));
	    }
	}
	
	 /**
     * @createtime 2017年8月20日17:15:41
     * @param request
     * @param response
     * @return 上传成功返回“success”，上传失败返回“error”
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "doSaveFiles", method = RequestMethod.POST)
    //public String doSaveFiles(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
    public String doSaveFiles(HttpServletRequest request, HttpServletResponse response, CgUploadEntity cgUploadEntity) throws IOException {
        System.out.println("执行upload");
        request.setCharacterEncoding("UTF-8");
        logger.info("执行图片上传");
        String questionTypeIndex = request.getParameter("questionTypeIndex");
        String introduct = request.getParameter("introduct");
        String options_type = request.getParameter("options_type");
        Map<String, Object> attributes = new HashMap<String, Object>();
        String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
        fileKey="";
		String id = oConvertUtils.getString(request.getParameter("cgFormId"));//动态表主键ID
		id="test";
		String tableName = oConvertUtils.getString(request.getParameter("cgFormName"));//动态表名
		tableName="t_sl_base_river_lake_patrol_event_photo";
		String cgField = oConvertUtils.getString(request.getParameter("cgFormField"));//动态表上传控件字段
		cgField="photo";
		logger.info("--cgUploadController--上传文件-----"+"{id:"+id+"}  {tableName："+tableName+"}  {cgField:"+cgField+"}");
		if(!StringUtil.isEmpty(id)){
			cgUploadEntity.setCgformId(id);
			cgUploadEntity.setCgformName(tableName);
			cgUploadEntity.setCgformField(cgField);
		}
		if (StringUtil.isNotEmpty(fileKey)) {
			cgUploadEntity.setId(fileKey);
			cgUploadEntity = systemService.getEntity(CgUploadEntity.class, fileKey);
		}
		UploadFile uploadFile = new UploadFile(request, cgUploadEntity);
		uploadFile.setCusPath("files");
		uploadFile.setSwfpath("swfpath");
		uploadFile.setByteField(null);//不存二进制内容
		cgUploadEntity = systemService.uploadFile(uploadFile);
		logger.info("--cgUploadController--saveFiles--上传文件----数据库保存转换成功-----");

		String realPath = cgUploadEntity.getRealpath();
		realPath = realPath.replace(File.separator, "/");
		cgUploadService.writeBack(id, tableName, cgField, fileKey, realPath);
		logger.info("--cgUploadController--saveFiles--上传文件----回写业务数据表字段文件路径-----");
		
        return "success";
    }
    
    @RequestMapping("doGetPatrolList")
	@ResponseBody
	public void doGetPatrolList(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		String column = oConvertUtils.getString(request.getParameter("column"));
    		String minId = oConvertUtils.getString(request.getParameter("minId"));
    		String pageSize = oConvertUtils.getString(request.getParameter("pageSize"));
    		String time = oConvertUtils.getString(request.getParameter("time"));
    		DataGrid dataGrid = new DataGrid();
    		dataGrid.setRows(Integer.parseInt(pageSize));//查出最新20条记录
    		CriteriaQuery cq = new CriteriaQuery(TSLBaseRiverLakePatrol.class, dataGrid);
    		//cq.eq("esType", "3");
    		//cq.addOrder("esSendtime", SortDirection.desc);
			//cq.add();
			systemService.getDataGridReturn(cq, true);
			List<TSLBaseRiverLakePatrol> list = dataGrid.getResults();
			//int size = list.size();
			for (Object o : dataGrid.getResults()) {
	        	if (o instanceof TSLBaseRiverLakePatrol) {
	        		TSLBaseRiverLakePatrol cfe = (TSLBaseRiverLakePatrol) o;
	        		cfe.setTslBaseRiverLake(null);
	        		cfe.setListGPS(null);
	        		cfe.setListGPSEvent(null);
	        		cfe.setListGPSPhote(null);
	        		cfe.setTslBaseRiverLake(null);
	        		cfe.setRiverLakeChief(null);
	        	}
	        }
			dataGrid.setField(column);
			
			TagUtil.datagrid(response, dataGrid);
    	} catch (Exception e) {
			logger.info("获取发送信息失败");
		}
    }
    @RequestMapping("doGetPatrolInfo")
   	@ResponseBody
   	public Object doGetPatrolInfo(HttpServletRequest request, HttpServletResponse response) {
    	String id = oConvertUtils.getString(request.getParameter("id"));
    	TSLBaseRiverLakePatrol cfe=systemService.get(TSLBaseRiverLakePatrol.class, id);	
    	cfe.setTslBaseRiverLake(null);
		cfe.setListGPS(null);
		cfe.setListGPSEvent(null);
		cfe.setListGPSPhote(null);
		cfe.setTslBaseRiverLake(null);
		cfe.setRiverLakeChief(null);
    	Map<String,Object> resultMap=new HashMap<String, Object>();
    	resultMap.put("cfe", cfe);
    	return Result.success(resultMap);
    }
    
    @RequestMapping("doGetContactsList")
   	@ResponseBody
   	public Object doGetContactsList(HttpServletRequest request, HttpServletResponse response) {
    	Map<String,Object> resultMap=new HashMap<String, Object>();
    	List<TSUser> userList =systemService.loadAll(TSUser.class);
    	JSONArray jsonArray = new JSONArray();
    	for(TSUser a : userList){
    		JSONObject json12= new JSONObject();
    		json12.put("userName", a.getRealName());
    		json12.put("id", a.getId());
    		json12.put("mobilephone", a.getMobilePhone());
    		json12.put("officePhone", a.getOfficePhone());
    		TSLDivisionEntity aa=a.getTSLDivision();
    		if (aa!=null){
    			json12.put("divisionName", aa.getName());
    		}else{
    			json12.put("divisionName", "");
    		}
    		TSLUserTypeEntity aa1=a.getTSLUserTypeEntity();
    		if (aa1!=null){
    			json12.put("userType", aa1.getName());
    		}else{
    			json12.put("userType", "");
    		}
    		
    		List<InterroleUserEntity> roleUser = systemService.findByProperty(InterroleUserEntity.class, "TSUser.id", a.getId());
    		if (roleUser.size() > 0) {
                String roleName = "";
                for (InterroleUserEntity ru : roleUser) {
                    roleName += ru.getInterroleEntity().getRoleName() + ",";
                }
                roleName = roleName.substring(0, roleName.length() - 1);
                json12.put("roleName", roleName);
            }else{
            	json12.put("roleName", "");
            }
    		
    		String[] ss= PinyinUtil.getHeadByString(a.getRealName(),true);
    		if (ss.length>0){
    			json12.put("PYFIRST", ss[0].toUpperCase());
    		}
    		jsonArray.add(json12);
    	}
    	
    	JSONArray jsonArray1 = new JSONArray();
    	for(int i=0;i<jsonArray.size();i++){
    		 JSONObject job = jsonArray.getJSONObject(i); 
    		 String stPYFIRST=oConvertUtils.getString(job.get("PYFIRST").toString());
    		 String stUserName=oConvertUtils.getString(job.get("userName").toString());
    		 String stId=oConvertUtils.getString(job.get("id").toString());
    		 
    		 String stmobilephone=oConvertUtils.getString(job.get("mobilephone"));
    		 String stofficePhone=oConvertUtils.getString(job.get("officePhone"));
    		 String stdivisionName=oConvertUtils.getString(job.get("divisionName"));
    		 String stuserType=oConvertUtils.getString(job.get("userType"));
    		 String stroleName=oConvertUtils.getString(job.get("roleName"));
    		 boolean bfind=false;
    		 for(int j=0;j<jsonArray1.size();j++){
    			 JSONObject job1= jsonArray1.getJSONObject(j);
    			 //"letter": "B",
				 //"data": [
				 //{id:"11121",name:"保山机场",phone1:"222",phone2:"3333",division:"三堡",useType:"河长",depart:"办公室"},
    			 String stletter=job1.get("letter").toString();
    			 if (stletter.equals(stPYFIRST)){
    				 bfind=true;
    				 JSONArray jsonArray2 =job1.getJSONArray("data");
    				 JSONObject json123= new JSONObject();
        			 json123.put("id", stId);
        			 json123.put("name", stUserName);
        			 json123.put("phone1", stmobilephone);
        			 json123.put("phone2", stofficePhone);
        			 json123.put("division", stdivisionName);
        			 json123.put("useType", stuserType);
        			 json123.put("depart", stroleName);
        			 jsonArray2.add(json123);
    				 break;
    			 }
    		 }
    		 if (!bfind){
    			 JSONObject json12= new JSONObject();
    			 json12.put("letter", stPYFIRST);
    			 JSONArray jsonArray2 = new JSONArray();
    			 JSONObject json123= new JSONObject();
    			 json123.put("id", stId);
    			 json123.put("name", stUserName);
    			 json123.put("phone1", stmobilephone);
    			 json123.put("phone2", stofficePhone);
    			 json123.put("division", stdivisionName);
    			 json123.put("useType", stuserType);
    			 json123.put("depart", stroleName);
    			 jsonArray2.add(json123);
    			 json12.put("data", jsonArray2);
    			 jsonArray1.add(json12);
    		 }
    	}
    	
    	List<JSONObject> list = new ArrayList<JSONObject>();
    	JSONObject jsonObj = null;
        for (int i = 0; i < jsonArray1.size(); i++) {
            jsonObj = (JSONObject) jsonArray1.get(i);
            list.add(jsonObj);
        }
        
        JSONArray sort_JsonArray = new JSONArray();
        //Collections.sort(list, new SortComparator(sortItem, sortType, sortDire));
        Collections.sort( list, new Comparator<JSONObject>() {
            //You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "letter";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = (String) a.get(KEY_NAME);
                    valB = (String) b.get(KEY_NAME);
                    //int int1 = Integer.parseInt(valA);
                    //int int2 = Integer.parseInt(valB);
                    //return int1 - int2;
                } 
                catch (JSONException e) {
                    //do something
                }

                return valA.compareTo(valB);
                //if you want to change the sort order, simply use the following:
                //return -valA.compareTo(valB);
            }
        });

        
        for (int i = 0; i < list.size(); i++) {
            jsonObj = list.get(i);
            sort_JsonArray.add(jsonObj);
        }
    	
    	resultMap.put("userList", sort_JsonArray);
    	return Result.success(resultMap);
    }
    @RequestMapping("doGetNewList")
   	@ResponseBody
   	public Object doGetNewList(HttpServletRequest request, HttpServletResponse response) {
    	Map<String,Object> resultMap=new HashMap<String, Object>();
    	List<TSType> typeList = ResourceUtil.getCacheTypes("noticetype");
    	resultMap.put("typeList", typeList);
    	//List<Map<String,Object>> maplist=systemService.findForJdbc("select * from t_s_notice where notice_type='"+rowId+"')", null);
    	return Result.success(resultMap);
    }
    
    @RequestMapping("doGetSmsList")
	@ResponseBody
	public void doGetSmsList(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			
    		String column = oConvertUtils.getString(request.getParameter("column"));
    		String minId = oConvertUtils.getString(request.getParameter("minId"));
    		String pageSize = oConvertUtils.getString(request.getParameter("pageSize"));
    		String time = oConvertUtils.getString(request.getParameter("time"));
    		DataGrid dataGrid = new DataGrid();
    		dataGrid.setRows(Integer.parseInt(pageSize));//查出最新20条记录
    		CriteriaQuery cq = new CriteriaQuery(TSSmsEntity.class, dataGrid);
    		cq.eq("esType", "3");
    		username="admin";
			cq.eq("esReceiver", username);
    		cq.addOrder("esSendtime", SortDirection.desc);
			cq.add();
    		
			systemService.getDataGridReturn(cq, true);
			List<TSSmsEntity> list = dataGrid.getResults();
			//int size = list.size();
			for (Object o : dataGrid.getResults()) {
	        	if (o instanceof TSNotice) {
	        		TSSmsEntity cfe = (TSSmsEntity) o;
	        		
	        	}
	        }
			dataGrid.setField(column);
			
			TagUtil.datagrid(response, dataGrid);
    	} catch (Exception e) {
			logger.info("获取发送信息失败");
		}
    }
    
    
    @RequestMapping("doGetRiverList")
	@ResponseBody
	public void doGetRiverList(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		
    		Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			
    		String column = oConvertUtils.getString(request.getParameter("column"));
    		String minId = oConvertUtils.getString(request.getParameter("minId"));
    		String pageSize = oConvertUtils.getString(request.getParameter("pageSize"));
    		String time = oConvertUtils.getString(request.getParameter("time"));
    		String current = oConvertUtils.getString(request.getParameter("current"));
    		
    		DataGrid dataGrid = new DataGrid();
    		dataGrid.setRows(Integer.parseInt(pageSize));//查出最新20条记录
    		CriteriaQuery cq = new CriteriaQuery(TSLBaseRiverLake.class, dataGrid);
    		if ("0".equals(current)) {
    			cq.eq("riverLakeType", "10");
			}
    		if ("1".equals(current)) {
    			cq.eq("riverLakeType", "11");
			}
    		if ("2".equals(current)) {
    			cq.eq("riverLakeType", "12");
			}
    		if ("3".equals(current)) {
    			cq.eq("riverLakeType", "13");
			}
    		if ("4".equals(current)) {
    			cq.eq("riverLakeType", "20");
			}
    		if ("5".equals(current)) {
    			cq.eq("riverLakeType", "21");
			}
    		if ("6".equals(current)) {
    			cq.eq("riverLakeType", "22");
			}
    		if ("7".equals(current)) {
    			cq.eq("riverLakeType", "23");
			}
    		//cq.eq("esType", "3");
    		//username="admin";
			//cq.eq("esReceiver", username);
    		//cq.addOrder("esSendtime", SortDirection.desc);
    		cq.addOrder("createDate", SortDirection.desc);
			cq.add();
    		
			systemService.getDataGridReturn(cq, true);
			List<TSLBaseRiverLake> list = dataGrid.getResults();
			//int size = list.size();
			for (Object o : dataGrid.getResults()) {
	        	//if (o instanceof TSNotice) {
	        		//TSSmsEntity cfe = (TSSmsEntity) o;
	        	//}
	        }
			dataGrid.setField(column);
			
			TagUtil.datagrid(response, dataGrid);
    	} catch (Exception e) {
			logger.info("获取发送信息失败");
		}
    }
    
    
    @RequestMapping("doUpdateSms")
	@ResponseBody
	public Object doUpdateSms(@RequestBody Map<String,String> reqbody,String callback,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (StringUtil.isEmpty(callback)) {
				callback="";
			}
			//String userInfoStr=StringUtil.formatEmpty(httpSession.getAttribute(sessionUserInfo));
			Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			if (StringUtil.isNotEmpty(username)){
				String id=oConvertUtils.getString(reqbody.get("id"));
				if (StringUtil.isNotEmpty(id)) {
					TSSmsEntity tSSms=systemService.getEntity(TSSmsEntity.class,id);
					if(tSSms.getIsRead() == 0){
						tSSms.setIsRead(1);
						systemService.saveOrUpdate(tSSms);
					}
					JSONObject attrMap = new JSONObject();
					return new JSONPObject(callback, Result.success(attrMap));
				}else {
					throw new Exception("不能为空!");
				}
			}else {
				throw new Exception("没有");
			}
		}catch (Exception e) {
	        e.printStackTrace();
	        return new JSONPObject(callback, Result.error("错误!"));
	    }
	}
    
    @RequestMapping("doGetNewList1")
	@ResponseBody
	public void doGetNewList1(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		String column = oConvertUtils.getString(request.getParameter("column"));
    		String minId = oConvertUtils.getString(request.getParameter("minId"));
    		String pageSize = oConvertUtils.getString(request.getParameter("pageSize"));
    		String time = oConvertUtils.getString(request.getParameter("time"));
    		String typecode = oConvertUtils.getString(request.getParameter("typecode"));
    		String typeid = oConvertUtils.getString(request.getParameter("typeid"));
    		DataGrid dataGrid = new DataGrid();
    		dataGrid.setRows(Integer.parseInt(pageSize));//查出最新20条记录
    		CriteriaQuery cq = new CriteriaQuery(TSNotice.class, dataGrid);
    		cq.eq("noticeType", typecode);
    		if (StringUtil.isNotEmpty(typeid)){
    			cq.lt("lid", typeid);
    		}
    		//cq.addOrder("esSendtime", SortDirection.desc);
    		cq.addOrder("lid", SortDirection.desc);
			cq.add();
    		
			systemService.getDataGridReturn(cq, true);
			List<TSNotice> list = dataGrid.getResults();
			//int size = list.size();
			for (Object o : dataGrid.getResults()) {
	        	if (o instanceof TSNotice) {
	        		TSNotice cfe = (TSNotice) o;
	        		String st111=StringUtil.getTimeStateNew(cfe.getCreateTime().getTime()+"");
	        		cfe.setNoticeMemo(st111);
	        		String userId=cfe.getCreateUser();
	        		if (StringUtil.isNotEmpty(userId)){
						TSUser user=systemService.get(TSUser.class, userId);
						cfe.setCreateUser(user.getUserName());
					}else{
						cfe.setCreateUser("管理员");
					}
	        	}
	        }
			dataGrid.setField(column);
			
			TagUtil.datagrid(response, dataGrid);
    	} catch (Exception e) {
			logger.info("获取发送信息失败");
		}
    }
    
    
    @RequestMapping("doGetProblemList")
	@ResponseBody
	public void doGetProblemList(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			if (StringUtil.isNotEmpty(username)){
				JSONObject aa=tokenManager.getUserInfo(username);
				String userId=aa.get("userId").toString();
				
				String column = oConvertUtils.getString(request.getParameter("column"));
	    		String minId = oConvertUtils.getString(request.getParameter("minId"));
	    		String pageSize = oConvertUtils.getString(request.getParameter("pageSize"));
	    		String time = oConvertUtils.getString(request.getParameter("time"));
	    		String id = oConvertUtils.getString(request.getParameter("id"));
	    		String typeid = oConvertUtils.getString(request.getParameter("typeid"));
	    		DataGrid dataGrid = new DataGrid();
	    		dataGrid.setRows(Integer.parseInt(pageSize));//查出最新20条记录
	    		CriteriaQuery cq = new CriteriaQuery(TSLBaseRiverLakePatrolEvent.class, dataGrid);
	    		if (StringUtil.isNotEmpty(typeid)){
	    			cq.lt("lid", typeid);
	    		}
	    		//cq.eq("noticeType", typecode);
	    		//if (StringUtil.isNotEmpty(typeid)){
	    		//	cq.lt("lid", typeid);
	    		//}
	    		//cq.addOrder("esSendtime", SortDirection.desc);
				/*
				已提交	10
				已上报	11
				已交办	12
				处置中	13
				已处置	14
				已结案	15
				未受理	20
				拒受理	200
				已受理	22
				已转派	23
				已办结	24
				已反馈	25
				送审		21
				
select * from t_sl_base_river_lake_patrol_event where 
(create_by='demo' and event_status in ('20','200','21','22'))
or 
(
id in (select base_river_lake_patrol_event_id from t_sl_base_river_lake_patrol_event_process_du where i_type=10 and process_type=11 and handle_depart_id in (select org_id from t_s_user_org where user_id='402880e74d75c4dd014d75d44af30005'))
or 
id in (select base_river_lake_patrol_event_id from t_sl_base_river_lake_patrol_event_process_du where i_type=10 and process_type=10 and handle_user_id='402880e74d75c4dd014d75d44af30005')
)
or 
(event_status in ('24','25'))
order by create_date desc
				
				*/
	    		if ("a01".equals(id)){
	    			//待处置
	    			//cq.sql(" (this_.PARENT_CODE is null or length(this_.PARENT_CODE)=0)  ");
	    			cq.sql(" (this_.create_by='demo' and this_.event_status in ('20','200','21','22'))  ");
	    		}
	    		if ("a02".equals(id)){
	    			//已交办
	    			cq.sql(" ("+
	    				   " this_.id in (select base_river_lake_patrol_event_id from t_sl_base_river_lake_patrol_event_process_du where i_type=10 and process_type=11 and handle_depart_id in (select org_id from t_s_user_org where user_id='"+userId+"'))"+
	    				   " or"+ 
	    				   " this_.id in (select base_river_lake_patrol_event_id from t_sl_base_river_lake_patrol_event_process_du where i_type=10 and process_type=10 and handle_user_id='"+userId+"')"+
	    				   " )  ");
	    		}
	    		if ("a03".equals(id)){
	    			//已结案
	    			cq.sql(" (this_.event_status in ('24','25'))  ");
	    		}
	    		//String[] sourceType = new String[]{"10","11", "12", "13"};
	    	    //cq.in("sourceType", sourceType);
	    	    cq.addOrder("createDate", SortDirection.desc);
				cq.add();
	    		
				systemService.getDataGridReturn(cq, true);
				List<TSLBaseRiverLakePatrolEvent> list = dataGrid.getResults();
				//int size = list.size();
				for (Object o : dataGrid.getResults()) {
		        	if (o instanceof TSLBaseRiverLakePatrolEvent) {
		        		TSLBaseRiverLakePatrolEvent cfe = (TSLBaseRiverLakePatrolEvent) o;
		        		JSONObject joall = new JSONObject();
		        		ResourceUtil.getCacheTypesByDictionary("eventStatus",cfe.getEventStatus(),joall);
		        		ResourceUtil.getCacheTypesByDictionary("questType",cfe.getQuestType(),joall);
		        		ResourceUtil.getCacheTypesByDictionary("sourceType",cfe.getSourceType(),joall);
		        		TSLBaseRiverLake ss=cfe.getTslBaseRiverLake();
		        		JSONObject jo = new JSONObject();
		        		jo.put("lakename",ss.getName());
		        		jo.put("lakecode",ss.getCode());
		        		joall.put("lake", jo);
		        		String jsonStr=joall.toString();
						cfe.setTempName(jsonStr);
		        	}
		        }
				dataGrid.setField(column);
				
				TagUtil.datagrid(response, dataGrid);
			}else{
				
			}
			
    		
    	} catch (Exception e) {
    		e.printStackTrace();
			logger.info("获取发送信息失败"+e.getMessage());
		}
    }
    
	
	@RequestMapping("doUpdateMobile")
	@ResponseBody
	public Object doUpdateMobile(@RequestBody Map<String,String> reqbody,String callback,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (StringUtil.isEmpty(callback)) callback="";
			//String userInfoStr=StringUtil.formatEmpty(httpSession.getAttribute(sessionUserInfo));
			Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			if (StringUtil.isNotEmpty(username)){
				JSONObject aa=tokenManager.getUserInfo(username);
				String userId=aa.get("userId").toString();
				String mobile01=oConvertUtils.getString(reqbody.get("mobile01"));
				String mobile02=oConvertUtils.getString(reqbody.get("mobile02"));
				if (StringUtil.isNotEmpty(mobile01)&&StringUtil.isNotEmpty(mobile02)) {
					TSUser user=systemService.getEntity(TSUser.class,userId);
					logger.info("["+IpUtil.getIpAddr(request)+"][修改手机号] start");
					if (!mobile01.equals(user.getMobilePhone())) {
						throw new Exception("原手机号不正确！");
					}
					try {
						user.setMobilePhone(mobile02);
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
					systemService.updateEntitie(user);
					JSONObject attrMap = new JSONObject();
					return new JSONPObject(callback, Result.success(attrMap));
				}else {
					throw new Exception("手机号不能为空!");
				}
			}else {
				throw new Exception("没有");
			}
		}catch (Exception e) {
	        e.printStackTrace();
	        return new JSONPObject(callback, Result.error("错误!"));
	    }
	}
	
	@RequestMapping("doUpdateEmail")
	@ResponseBody
	public Object doUpdateEmail(@RequestBody Map<String,String> reqbody,String callback,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (StringUtil.isEmpty(callback)) callback="";
			//String userInfoStr=StringUtil.formatEmpty(httpSession.getAttribute(sessionUserInfo));
			Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
			String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
			if (StringUtil.isNotEmpty(username)){
				JSONObject aa=tokenManager.getUserInfo(username);
				String userId=aa.get("userId").toString();
				String email01=oConvertUtils.getString(reqbody.get("email01"));
				String email02=oConvertUtils.getString(reqbody.get("email02"));
				if (StringUtil.isNotEmpty(email01)&&StringUtil.isNotEmpty(email02)) {
					TSUser user=systemService.getEntity(TSUser.class,userId);
					logger.info("["+IpUtil.getIpAddr(request)+"][修改电子邮箱] start");
					if (!email01.equals(user.getEmail())) {
						throw new Exception("原电子邮箱不正确！");
					}
					try {
						user.setEmail(email02);
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
					systemService.updateEntitie(user);
					JSONObject attrMap = new JSONObject();
					return new JSONPObject(callback, Result.success(attrMap));
				}else {
					throw new Exception("电子邮箱不能为空!");
				}
			}else {
				throw new Exception("没有");
			}
		}catch (Exception e) {
	        e.printStackTrace();
	        return new JSONPObject(callback, Result.error(e.getMessage()));
	    }
	}
	
	
	
	
	private static final String CONTROLLER_LIST_APICTURE = "modules/hzz/slRiverLake/aPicture/slAPicture";
	@RequestMapping("apicture")
	public String apicture(HttpServletRequest request) {
		Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
		String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
		if (StringUtil.isNotEmpty(username)){
			return CONTROLLER_LIST_APICTURE;	
		}
		return "";
	}
	
	@RequestMapping("getAPictureInfo")
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
	
	/**
	 * 动态报表展现入口
	 * @param id 动态配置ID-code
	 * @param request
	 * @param response
	 */
	@RequestMapping("list")
	public void list(String id, HttpServletRequest request,
			HttpServletResponse response) {
		Claims claims=(Claims)request.getAttribute(JwtConstants.CURRENT_TOKEN_CLAIMS);
		String username=(String)request.getAttribute(JwtConstants.CURRENT_USER_NAME);
		if (StringUtil.isEmpty(username)){
			return;	
		}
		
		//step.1 根据id获取该动态报表的配置参数
		Map<String, Object>  cgReportMap = null;
		try{
			cgReportMap = graphReportService.queryCgReportConfig(id);
		}catch (Exception e) {
			throw new CgReportNotFoundException("动态报表配置不存在!");
		}
		//step.2 获取列表ftl模板路径
		FreemarkerHelper viewEngine = new FreemarkerHelper();
		//step.3 组合模板+数据参数，进行页面展现
		loadVars(cgReportMap);

		//step.4 页面css js引用
		cgReportMap.put(CgReportConstant.CONFIG_IFRAME, getHtmlHead(request));

		String html = viewEngine.parseTemplate("/com/saili/hzz/web/graphreport/engine/core/graphreportlist01.ftl", cgReportMap);
		PrintWriter writer = null;
		try {
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-store");
			writer = response.getWriter();
			writer.println(html);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	/**
	 * 组装模版参数
	 * @param cgReportMap
	 */
	@SuppressWarnings("unchecked")
	private void loadVars(Map<String, Object> cgReportMap) {
		Map mainM = (Map) cgReportMap.get(CgReportConstant.MAIN);

		List<Map<String,Object>> fieldList2 = (List<Map<String, Object>>) cgReportMap.get(CgReportConstant.ITEMS);
		List<Map<String,Object>> fieldList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : fieldList2) {
			Map<String, Object> temp = new HashMap<String, Object>();
			for (Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				temp.put(key.toLowerCase(), entry.getValue());
			}
			fieldList.add(temp);
		}

		List<Map<String,Object>> queryList = new ArrayList<Map<String,Object>>(0);
		//图表数据
		List<Map<String,Object>> graphList = new ArrayList<Map<String,Object>>(0);
		//tab数据
		Set<String> tabSet = new HashSet<String>();
		List<String> tabList = new ArrayList<String>();
		
		for(Map<String,Object> fl:fieldList){
			fl.put(CgReportConstant.ITEM_FIELDNAME, ((String)fl.get(CgReportConstant.ITEM_FIELDNAME)).toLowerCase());
			String isQuery = (String) fl.get(CgReportConstant.ITEM_ISQUERY);
			if(CgReportConstant.BOOL_TRUE.equalsIgnoreCase(isQuery)){
				cgReportService.loadDic(fl);
				queryList.add(fl);
			}
			if("y".equals(fl.get("is_graph")) || "Y".equals(fl.get("is_graph"))) {
				graphList.add(fl);
				String tabName = (fl.get("tab_name") == null ? "" : fl.get("tab_name").toString());
				if(!tabSet.contains(tabName)) {
					tabList.add(tabName);
					tabSet.add(tabName);
				}
			}
		}
		cgReportMap.put(CgReportConstant.CONFIG_ID, mainM.get("code"));
		cgReportMap.put(CgReportConstant.CONFIG_NAME, mainM.get("name"));
		cgReportMap.put(CgReportConstant.CONFIG_FIELDLIST, fieldList);
		cgReportMap.put(CgReportConstant.CONFIG_QUERYLIST, queryList);
		cgReportMap.put("graphList", graphList);
		cgReportMap.put("tabList", tabList);
	}
	
	private String getHtmlHead(HttpServletRequest request){
		String basePath = ResourceUtil.getBasePath();
		HttpSession session = ContextHolderUtils.getSession();
		String lang = (String)session.getAttribute("lang");
		StringBuilder sb= new StringBuilder("");

		sb.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>");

		SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme(request);
		sb.append(SysThemesUtil.getReportTheme(sysThemesEnum));
		sb.append(SysThemesUtil.getCommonTheme(sysThemesEnum));
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery/jquery-1.8.3.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/dataformat.js\"></script>");
		sb.append(SysThemesUtil.getEasyUiTheme(sysThemesEnum));
		sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/easyui/themes/icon.css\" type=\"text/css\"></link>");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basePath+"/plug-in/accordion/css/accordion.css\">");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basePath+"/plug-in/accordion/css/icons.css\">");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/easyui/jquery.easyui.min.1.3.2.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/easyui/locale/zh-cn.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/syUtil.js\"></script>");
		sb.append(SysThemesUtil.getLhgdialogTheme(sysThemesEnum));

		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/layer/layer.js\"></script>");

		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/curdtools.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/easyuiextend.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/easyui/extends/datagrid-scrollview.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/My97DatePicker/WdatePicker.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/graphreport/highcharts3.0.6.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/graphreport/spin.min.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/graphreport/report.js\"></script>");
		return sb.toString();
	}
	
	/**
	 * 动态报表数据查询(不分页)
	 * @param configId 配置id-code
	 * @param request 
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("datagridGraph")
	public void datagridGraph(String configId, HttpServletRequest request, HttpServletResponse response) {
		//step.1 根据id获取该动态报表的配置参数
		Map<String, Object>  cgReportMap = null;
		try{
			cgReportMap = graphReportService.queryCgReportConfig(configId);
			if(cgReportMap.size()<=0){
				throw new CgReportNotFoundException("动态报表配置不存在!");
			}
		}catch (Exception e) {
			throw new CgReportNotFoundException("查找动态报表配置失败!"+e.getMessage());
		}
		PrintWriter writer = null;
		try {
			//step.2 获取该配置的查询SQL
			Map configM = (Map) cgReportMap.get(CgReportConstant.MAIN);
			String querySql = (String) configM.get("CGR_SQL");
			List<Map<String,Object>> items = (List<Map<String, Object>>) cgReportMap.get(CgReportConstant.ITEMS);
			//页面参数查询字段（占位符的条件语句）
			//Map pageSearchFields =  new LinkedHashMap<String,Object>();
			Map pageSearchFields =  new IdentityHashMap<String,Object>();

			//获取查询条件数据
			Map<String,Object> paramData = new HashMap<String, Object>();
			for(Map<String,Object> item:items){
				String isQuery = (String) item.get(CgReportConstant.ITEM_ISQUERY);
				if(CgReportConstant.BOOL_TRUE.equalsIgnoreCase(isQuery)){
					//step.3 装载查询条件
					CgReportQueryParamUtil.loadQueryParams(request, item, pageSearchFields,paramData);
				}
			}
			//step.4 进行查询返回结果
			List<Map<String, Object>> result= graphReportService.queryByCgReportSql(querySql, pageSearchFields,paramData, -1, -1);

			cgReportService.dealDic(result,items);
			cgReportService.dealReplace(result,items);
			
			//导出execel
			List<String> fields = new ArrayList<String>();
			List<Map<String, Object>> configItems = (List<Map<String, Object>>) cgReportMap.get(CgReportConstant.ITEMS);
			for (Map<String, Object> map : configItems) {
				if("Y".equals(map.get("is_show"))) {
					fields.add(map.get("field_txt").toString());
					fields.add(map.get("field_name").toString());
				}
			}
			if(exportExecel(request, response, configM.get("content").toString(), configM.get("content").toString(), fields.toArray(new String[fields.size()]), result, null)) {
				return;
			}
			
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
		
			writer = response.getWriter();
			writer.println(CgReportQueryParamUtil.getJson(result, -1L));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			try {
				writer.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	/**
	 * 导出execel
	 */
	private boolean exportExecel(HttpServletRequest request, HttpServletResponse response, 
			String title, String tagName, String[] fields, List<Map<String, Object>> list, Map<String, Object> params) {
		if(!"1".equals(request.getParameter("export"))) {
			return false;
		}
		
		if(params == null) {
			params = new HashMap<String, Object>();
		}
		if(StringUtil.isEmpty(tagName)) {
			tagName = title;
		}
		
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		try {
			//step.4 根据浏览器进行转码，使其支持中文文件名
			String browse = BrowserUtils.checkBrowse(request);
			if ("MSIE".equalsIgnoreCase(browse.substring(0, 4))) {
				response.setHeader("content-disposition",
						"attachment;filename=" + java.net.URLEncoder.encode(title, "UTF-8") + ".xls");
			} else {
				String newtitle = new String(title.getBytes("UTF-8"), "ISO8859-1");
				response.setHeader("content-disposition",
						"attachment;filename=" + newtitle + ".xls");
			}
			//step.5 产生工作簿对象
			HSSFWorkbook workbook = null;
			
			
			List<Map<String, Object>> fieldList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < fields.length; i++,i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("field_txt", fields[i]);
				map.put("field_name", fields[i + 1]);
				fieldList.add(map);
			}
			
			workbook = cgReportExcelService.exportExcel(tagName, fieldList, list);
			fOut = response.getOutputStream();
			workbook.write(fOut);
			//TODO 增加操作日志
			//systemService.addLog(MsgUtil.getOperationLogMsg("导出成功", title, params), Globals.Log_Type_EXPORT,
			//		Globals.Log_Leavel_INFO);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
		

}


