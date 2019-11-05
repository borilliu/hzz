package com.saili.hzz.web.system.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.saili.hzz.core.annotation.Ehcache;
import com.saili.hzz.core.constant.Globals;
import com.saili.hzz.core.util.DateUtils;
import com.saili.hzz.web.system.dao.JeecgDictDao;
import com.saili.hzz.web.system.pojo.base.*;
import com.saili.hzz.web.system.service.CacheServiceI;
import com.saili.hzz.web.system.service.SystemService;
import com.saili.hzz.web.system.util.OrgConstants;
import com.saili.hzz.core.entity.*;
import com.saili.hzz.core.enums.RiverLakeTypeEnum;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import com.saili.hzz.core.common.dao.jdbc.JdbcDao;
import com.saili.hzz.core.common.hibernate.qbc.CriteriaQuery;
import com.saili.hzz.core.common.service.impl.CommonServiceImpl;
import com.saili.hzz.core.util.BrowserUtils;
import com.saili.hzz.core.util.ContextHolderUtils;
import com.saili.hzz.core.util.IpUtil;
import com.saili.hzz.core.util.LogUtil;
import com.saili.hzz.core.util.MutiLangUtil;
import com.saili.hzz.core.util.ResourceUtil;
import com.saili.hzz.core.util.StringUtil;
import com.saili.hzz.core.util.YouBianCodeUtil;
import com.saili.hzz.core.util.oConvertUtils;
import com.saili.hzz.web.system.pojo.base.DictEntity;
import com.saili.hzz.web.system.pojo.base.TSDatalogEntity;
import com.saili.hzz.web.system.pojo.base.TSDepart;
import com.saili.hzz.web.system.pojo.base.TSDepartAuthGroupEntity;
import com.saili.hzz.web.system.pojo.base.TSDepartAuthgFunctionRelEntity;
import com.saili.hzz.web.system.pojo.base.TSFunction;
import com.saili.hzz.web.system.pojo.base.TSIcon;
import com.saili.hzz.web.system.pojo.base.TSLog;
import com.saili.hzz.web.system.pojo.base.TSOperation;
import com.saili.hzz.web.system.pojo.base.TSRole;
import com.saili.hzz.web.system.pojo.base.TSRoleFunction;
import com.saili.hzz.web.system.pojo.base.TSRoleOrg;
import com.saili.hzz.web.system.pojo.base.TSRoleUser;
import com.saili.hzz.web.system.pojo.base.TSType;
import com.saili.hzz.web.system.pojo.base.TSTypegroup;
import com.saili.hzz.web.system.pojo.base.TSUser;
import com.saili.hzz.web.system.pojo.base.TSUserOrg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("systemService")
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {
	private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

	@Autowired
	private JeecgDictDao jeecgDictDao;
	@Autowired
	private CacheServiceI cacheService;
	@Autowired
	private IdentityService identityService;

	@Override
	@Transactional(readOnly = true)
	public TSUser checkUserExits(TSUser user) throws Exception {
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DictEntity> queryDict(String dicTable, String dicCode, String dicText){
		List<DictEntity> dictList = null;
		//step.1 如果没有字典表则使用系统字典表
		if(StringUtil.isEmpty(dicTable)){
			dictList = jeecgDictDao.querySystemDict(dicCode);
			for(DictEntity t:dictList){
				t.setTypename(MutiLangUtil.getLang(t.getTypename()));
			}
		}else {
			dicText = StringUtil.isEmpty(dicText, dicCode);
			dictList = jeecgDictDao.queryCustomDict(dicTable, dicCode, dicText);
		}
		return dictList;
	}

	/**
	 * 添加日志
	 */
	@Override
	@Transactional
	public void addLog(String logcontent, Short operatetype, Short loglevel) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		TSLog log = new TSLog();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);

		log.setNote(IpUtil.getIpAddr(request));

		log.setBroswer(broswer);
		/*start dangzhenghui 201703016TASK #1784 【online bug】Online 表单保存的时候，报错*/
		log.setOperatetime(new Date());
		/* end dangzhenghui 201703016TASK #1784 【online bug】Online 表单保存的时候，报错*/
//		log.setTSUser(ResourceUtil.getSessionUser());
		/*start chenqian 201708031TASK #2317 【改造】系统日志表，增加两个字段，避免关联查询 [操作人账号] [操作人名字]*/
		TSUser u = ResourceUtil.getSessionUser();
		if(u!=null){
			log.setUserid(u.getId());
			log.setUsername(u.getUserName());
			log.setRealname(u.getRealName());
		}

		commonDao.save(log);
	}

	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 *
	 * @param typecode
	 * @param typename
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public TSType getType(String typecode, String typename, TSTypegroup tsTypegroup) {
		//TSType actType = commonDao.findUniqueByProperty(TSType.class, "typecode", typecode,tsTypegroup.getId());
		List<TSType> ls = commonDao.findHql("from TSType where typecode = ? and typegroupid = ?",typecode,tsTypegroup.getId());
		TSType actType = null;
		if (ls == null || ls.size()==0) {
			actType = new TSType();
			actType.setTypecode(typecode);
			actType.setTypename(typename);
			actType.setTSTypegroup(tsTypegroup);
			commonDao.save(actType);
		}else{
			actType = ls.get(0);
		}
		return actType;

	}

	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 *
	 * @param typecode
	 * @param typename
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public TSTypegroup getTypeGroup(String typegroupcode, String typgroupename) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupcode);
		if (tsTypegroup == null) {
			tsTypegroup = new TSTypegroup();
			tsTypegroup.setTypegroupcode(typegroupcode);
			tsTypegroup.setTypegroupname(typgroupename);
			commonDao.save(tsTypegroup);
		}
		return tsTypegroup;
	}

	@Override
	@Transactional(readOnly = true)
	public TSTypegroup getTypeGroupByCode(String typegroupCode) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupCode);
		return tsTypegroup;
	}


	@Override
	@Transactional(readOnly = true)
	public void initAllTypeGroups() {
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		Map<String, TSTypegroup> typeGroupsList = new HashMap<String, TSTypegroup>();
		Map<String, List<TSType>> typesList = new HashMap<String, List<TSType>>();
		for (TSTypegroup tsTypegroup : typeGroups) {
			tsTypegroup.setTSTypes(null);
			typeGroupsList.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
			List<TSType> types = this.commonDao.findHql("from TSType where TSTypegroup.id = ? order by orderNum" , tsTypegroup.getId());
			for(TSType t:types){
				t.setTSType(null);
				t.setTSTypegroup(null);
				t.setTSTypes(null);
			}
			typesList.put(tsTypegroup.getTypegroupcode().toLowerCase(), types);
		}

		cacheService.put(CacheServiceI.FOREVER_CACHE,ResourceUtil.DICT_TYPE_GROUPS_KEY,typeGroupsList);
		cacheService.put(CacheServiceI.FOREVER_CACHE,ResourceUtil.DICT_TYPES_KEY,typesList);

		logger.info("  ------ 初始化字典组 【系统缓存】-----------typeGroupsList-----size: [{}]",typeGroupsList.size());
		logger.info("  ------ 初始化字典 【系统缓存】-----------typesList-----size: [{}]",typesList.size());
	}

	@Override
	@Transactional(readOnly = true)
	public void refleshTypesCach(TSType type) {
		Map<String, List<TSType>> typesList = null;
		TSTypegroup result = null;
		Object obj = cacheService.get(CacheServiceI.FOREVER_CACHE,ResourceUtil.DICT_TYPES_KEY);
		if(obj!=null){
			typesList = (Map<String, List<TSType>>) obj;
		}else{
			typesList = new HashMap<String, List<TSType>>();
		}
		TSTypegroup tsTypegroup = type.getTSTypegroup();
		TSTypegroup typeGroupEntity = this.commonDao.get(TSTypegroup.class, tsTypegroup.getId());

		List<TSType> tempList = this.commonDao.findHql("from TSType where TSTypegroup.id = ? order by orderNum" , tsTypegroup.getId());
		List<TSType> types = new ArrayList<TSType>();
		for(TSType t:tempList){
			TSType tt = new TSType();
			tt.setTSType(null);
			tt.setTSTypegroup(null);
			tt.setTSTypes(null);
			tt.setId(t.getId());
			tt.setOrderNum(t.getOrderNum());
			tt.setTypecode(t.getTypecode());
			tt.setTypename(t.getTypename());
			types.add(tt);
		}

		typesList.put(typeGroupEntity.getTypegroupcode().toLowerCase(), types);
		cacheService.put(CacheServiceI.FOREVER_CACHE,ResourceUtil.DICT_TYPES_KEY,typesList);
		logger.info("  ------ 重置字典缓存【系统缓存】  ----------- typegroupcode: [{}] ",typeGroupEntity.getTypegroupcode().toLowerCase());
	}

	@Override
	@Transactional(readOnly = true)
	public void refleshTypeGroupCach() {
		Map<String, TSTypegroup> typeGroupsList = new HashMap<String, TSTypegroup>();
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			typeGroupsList.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
		}
		cacheService.put(CacheServiceI.FOREVER_CACHE,ResourceUtil.DICT_TYPE_GROUPS_KEY,typeGroupsList);
		logger.info("  ------ 重置字典分组缓存&字典缓存【系统缓存】  ------ refleshTypeGroupCach --------  ");
	}

	/**
	 * 刷新字典分组缓存&字典缓存
	 */
	@Override
	@Transactional(readOnly = true)
	public void refreshTypeGroupAndTypes() {
		logger.info("  ------ 重置字典分组缓存&字典缓存【系统缓存】  ------ refreshTypeGroupAndTypes --------  ");
		this.initAllTypeGroups();
	}


	/**
	 * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonDao.get(TSRole.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id", role.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			if (null != tsRoleFunction.getOperation()) {
				String[] operationArry = tsRoleFunction.getOperation().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}


	/**
	 * 获取页面控件权限控制的
	 * JS片段
	 * @param out
	 */
	@Override
	@Transactional(readOnly = true)
	public String getAuthFilterJS() {
		StringBuilder out = new StringBuilder();
		out.append("<script type=\"text/javascript\">");
		out.append("$(document).ready(function(){");
		if(ResourceUtil.getSessionUser().getUserName().equals("admin")|| !Globals.BUTTON_AUTHORITY_CHECK){
			return "";
		}else{
			HttpServletRequest request = ContextHolderUtils.getRequest();
			Set<String> operationCodes = (Set<String>) request.getAttribute(Globals.OPERATIONCODES);
			if (null!=operationCodes) {
				for (String MyoperationCode : operationCodes) {
					if (oConvertUtils.isEmpty(MyoperationCode))
						break;
					TSOperation operation = this.getEntity(TSOperation.class, MyoperationCode);
					if (operation.getOperationcode().startsWith(".") || operation.getOperationcode().startsWith("#")){
						if (operation.getOperationType().intValue()==Globals.OPERATION_TYPE_HIDE){
							//out.append("$(\""+name+"\").find(\"#"+operation.getOperationcode().replaceAll(" ", "")+"\").hide();");
							out.append("$(\""+operation.getOperationcode().replaceAll(" ", "")+"\").hide();");
						}else {
							//out.append("$(\""+name+"\").find(\"#"+operation.getOperationcode().replaceAll(" ", "")+"\").find(\":input\").attr(\"disabled\",\"disabled\");");
							out.append("$(\""+operation.getOperationcode().replaceAll(" ", "")+"\").attr(\"disabled\",\"disabled\");");
							out.append("$(\""+operation.getOperationcode().replaceAll(" ", "")+"\").find(\":input\").attr(\"disabled\",\"disabled\");");
						}
					}
				}
			}else{
				return "";
			}

		}
		out.append("});");
		out.append("</script>");
		return out.toString();
	}

	@Override
	@Transactional(readOnly = true)
	public void flushRoleFunciton(String id, TSFunction newFunction) {
		TSFunction functionEntity = this.getEntity(TSFunction.class, id);
		if (functionEntity.getTSIcon() == null || !StringUtil.isNotEmpty(functionEntity.getTSIcon().getId())) {
			return;
		}
		TSIcon oldIcon = this.getEntity(TSIcon.class, functionEntity.getTSIcon().getId());
		if (!oldIcon.getIconClas().equals(newFunction.getTSIcon().getIconClas())) {
			// 刷新缓存
			HttpSession session = ContextHolderUtils.getSession();
			TSUser user = ResourceUtil.getSessionUser();
			List<TSRoleUser> rUsers = this.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				session.removeAttribute(role.getId());
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
    public String generateOrgCode(String id, String pid) {

        int orgCodeLength = 2; // 默认编码长度
        if ("3".equals(ResourceUtil.getOrgCodeLengthType())) { // 类型2-编码长度为3，如001
            orgCodeLength = 3;
        }


        String  newOrgCode = "";
        if(!StringUtils.hasText(pid)) { // 第一级编码
            String sql = "select max(t.org_code) orgCode from t_s_depart t where t.parentdepartid is null";
            Map<String, Object> pOrgCodeMap = commonDao.findOneForJdbc(sql);
            if(pOrgCodeMap.get("orgCode") != null) {
                String curOrgCode = pOrgCodeMap.get("orgCode").toString();
                newOrgCode = String.format("%0" + orgCodeLength + "d", Integer.valueOf(curOrgCode) + 1);
            } else {
                newOrgCode = String.format("%0" + orgCodeLength + "d", 1);
            }
        } else { // 下级编码
            String sql = "select max(t.org_code) orgCode from t_s_depart t where t.parentdepartid = ?";
            Map<String, Object> orgCodeMap = commonDao.findOneForJdbc(sql, pid);
            if(orgCodeMap.get("orgCode") != null) { // 当前基本有编码时
                String curOrgCode = orgCodeMap.get("orgCode").toString();
                String pOrgCode = curOrgCode.substring(0, curOrgCode.length() - orgCodeLength);
                String subOrgCode = curOrgCode.substring(curOrgCode.length() - orgCodeLength, curOrgCode.length());
                newOrgCode = pOrgCode + String.format("%0" + orgCodeLength + "d", Integer.valueOf(subOrgCode) + 1);
            } else { // 当前级别没有编码时
                String pOrgCodeSql = "select max(t.org_code) orgCode from t_s_depart t where t.id = ?";
                Map<String, Object> pOrgCodeMap = commonDao.findOneForJdbc(pOrgCodeSql, pid);
                String curOrgCode = pOrgCodeMap.get("orgCode").toString();
                newOrgCode = curOrgCode + String.format("%0" + orgCodeLength + "d", 1);
            }
        }

        return newOrgCode;
    }

	@Override
	@Transactional(readOnly = true)
	public Set<String> getDataRuleIdsByRoleIdAndFunctionId(String roleId,String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonDao.get(TSRole.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id", role.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			if (null != tsRoleFunction.getDataRule()) {
				String[] operationArry = tsRoleFunction.getDataRule().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	/**
	 * 加载所有图标
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public  void initAllTSIcons() {
		List<TSIcon> list = this.loadAll(TSIcon.class);
		for (TSIcon tsIcon : list) {
			ResourceUtil.allTSIcons.put(tsIcon.getId(), tsIcon);
		}
	}
	/**
	 * 更新图标
	 * @param icon
	 */
	@Override
	public void upTSIcons(TSIcon icon) {
		ResourceUtil.allTSIcons.put(icon.getId(), icon);
	}
	/**
	 * 更新图标
	 * @param icon
	 */
	@Override
	public void delTSIcons(TSIcon icon) {
		ResourceUtil.allTSIcons.remove(icon.getId());
	}

	@Override
	public void addDataLog(String tableName, String dataId, String dataContent) {

		int versionNumber = 0;

		Integer integer = null;
		String sql = "select max(VERSION_NUMBER) as mvn from t_s_data_log where TABLE_NAME = ? and DATA_ID = ?";
		Map<String,Object> maxVersion = commonDao.findOneForJdbc(sql, tableName ,dataId);
		if(maxVersion.get("mvn")!=null){
			integer = Integer.parseInt(maxVersion.get("mvn").toString());
		}

		if (integer != null) {
			versionNumber = integer.intValue();
		}

		TSDatalogEntity tsDatalogEntity = new TSDatalogEntity();
		tsDatalogEntity.setTableName(tableName);
		tsDatalogEntity.setDataId(dataId);
		tsDatalogEntity.setDataContent(dataContent);
		tsDatalogEntity.setVersionNumber(versionNumber + 1);
		commonDao.save(tsDatalogEntity);
	}

	/**
	 * 获取二级管理员页面控件权限授权配置【二级管理员后台权限配置功能】
	 * @param groupId 部门角色组ID
	 * @param functionId 选中菜单ID
	 * @Param type 0:部门管理员组/1:部门角色
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Set<String> getDepartAuthGroupOperationSet(String groupId,String functionId,String type) {
		Set<String> operationCodes = new HashSet<String>();
		TSDepartAuthGroupEntity functionGroup = null;
		if(OrgConstants.GROUP_DEPART_ROLE.equals(type)) {
			TSRole role = commonDao.get(TSRole.class, groupId);
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id", role.getId());
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> functionGroups = getListByCriteriaQuery(cq1, false);
			if (null != functionGroups && functionGroups.size() > 0) {
				TSRoleFunction tsFunctionGroup = functionGroups.get(0);
				if (null != tsFunctionGroup.getOperation()) {
					String[] operationArry = tsFunctionGroup.getOperation().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						operationCodes.add(operationArry[i]);
					}
				}
			}
		} else {
			functionGroup = commonDao.get(TSDepartAuthGroupEntity.class, groupId);
			CriteriaQuery cq1 = new CriteriaQuery(TSDepartAuthgFunctionRelEntity.class);
			cq1.eq("tsDepartAuthGroup.id", functionGroup.getId());
			cq1.eq("tsFunction.id", functionId);
			cq1.add();
			List<TSDepartAuthgFunctionRelEntity> functionGroups = getListByCriteriaQuery(cq1, false);
			if (null != functionGroups && functionGroups.size() > 0) {
				TSDepartAuthgFunctionRelEntity tsFunctionGroup = functionGroups.get(0);
				if (null != tsFunctionGroup.getOperation()) {
					String[] operationArry = tsFunctionGroup.getOperation().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						operationCodes.add(operationArry[i]);
					}
				}
			}
		}
		return operationCodes;
	}

	/**
	 * 获取二级管理员数据权限授权配置【二级管理员后台权限配置功能】
	 * @param groupId 部门角色组ID
	 * @param functionId 选中菜单ID
	 * @Param type  0:部门管理员组/1:部门角色
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Set<String> getDepartAuthGroupDataRuleSet(String groupId, String functionId,String type) {
		Set<String> dataRuleCodes = new HashSet<String>();
		TSDepartAuthGroupEntity functionGroup = null;
		if(OrgConstants.GROUP_DEPART_ROLE.equals(type)) {
			TSRole role = commonDao.get(TSRole.class, groupId);
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id", role.getId());
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> functionGroups = getListByCriteriaQuery(cq1, false);
			if (null != functionGroups && functionGroups.size() > 0) {
				TSRoleFunction tsFunctionGroup = functionGroups.get(0);
				if (null != tsFunctionGroup.getDataRule()) {
					String[] dataRuleArry = tsFunctionGroup.getDataRule().split(",");
					for (int i = 0; i < dataRuleArry.length; i++) {
						dataRuleCodes.add(dataRuleArry[i]);
					}
				}
			}
		} else {
			functionGroup = commonDao.get(TSDepartAuthGroupEntity.class, groupId);
			CriteriaQuery cq1 = new CriteriaQuery(TSDepartAuthgFunctionRelEntity.class);
			cq1.eq("tsDepartAuthGroup.id", functionGroup.getId());
			cq1.eq("tsFunction.id", functionId);
			cq1.add();
			List<TSDepartAuthgFunctionRelEntity> functionGroups = getListByCriteriaQuery(cq1, false);
			if (null != functionGroups && functionGroups.size() > 0) {
				TSDepartAuthgFunctionRelEntity tsFunctionGroup = functionGroups.get(0);
				if (null != tsFunctionGroup.getDatarule()) {
					String[] dataRuleArry = tsFunctionGroup.getDatarule().split(",");
					for (int i = 0; i < dataRuleArry.length; i++) {
						dataRuleCodes.add(dataRuleArry[i]);
					}
				}
			}
		}
		return dataRuleCodes;
	}

	/**
	 * 【AuthInterceptor】根据用户请求URL，查询数据库中对应的菜单ID
	 */
	@Override
	@Ehcache(cacheName="sysAuthCache")
	public String getFunctionIdByUrl(String url,String menuPath) {
		//查询请求对应的菜单ID
		//解决rest风格下 权限失效问题
		String functionId="";
		String realRequestPath = null;
		if(url.endsWith(".do")||url.endsWith(".action")){
			realRequestPath=menuPath;
		}else {
			realRequestPath=url;
		}

		//----自定义表单页面控件权限控制---------------
		if(realRequestPath.indexOf("autoFormController/af/")>-1 && realRequestPath.indexOf("?")!=-1){
			realRequestPath = realRequestPath.substring(0, realRequestPath.indexOf("?"));
		}
		List<TSFunction> functions = this.findByProperty(TSFunction.class, "functionUrl", realRequestPath);
		if (functions.size()>0){
			functionId = functions.get(0).getId();
		}
		logger.debug("-----[ 读取数据库获取访问请求的菜单ID ]-------functionId: "+functionId +"------ url: "+url+"-----menuPath: "+menuPath);
		return functionId;
	}

	/**
	 * 【AuthInterceptor】判断用户是否有菜单访问权限
	 * @param requestPath       用户请求URL
	 * @param clickFunctionId   菜单ID(未使用)
	 * @param userid  			用户id
	 * @param orgId   			用户登录机构ID
	 * @return
	 */
	@Override
	@Ehcache(cacheName="sysAuthCache")
	public boolean loginUserIsHasMenuAuth(String requestPath,String clickFunctionId,String userid,String orgId){
        //step.1 先判断请求是否配置菜单，没有配置菜单默认不作权限控制[注意：这里不限制权限类型菜单]
        String hasMenuSql = "select count(*) from t_s_function where functiontype = 0 and functionurl = ?";
        Long hasMenuCount = this.getCountForJdbcParam(hasMenuSql,requestPath);
    	logger.debug("-----[ 读取数据库判断访问权限 ]-------requestPath----------"+requestPath+"------------hasMenuCount--------"+ hasMenuCount);
        if(hasMenuCount<=0){
        	return true;
        }

        //step.2 判断菜单是否有角色权限
        Long authSize = Long.valueOf(0);
		String sql = "SELECT count(*) FROM t_s_function f,t_s_role_function  rf,t_s_role_user ru " +
					" WHERE f.id=rf.functionid AND rf.roleid=ru.roleid AND " +
					"ru.userid=? AND f.functionurl = ?";
		authSize = this.getCountForJdbcParam(sql,userid,requestPath);
		if(authSize <=0){
			//step.3 判断菜单是否有组织机构角色权限
            Long orgAuthSize = Long.valueOf(0);
            String functionOfOrgSql = "SELECT count(*) from t_s_function f, t_s_role_function rf, t_s_role_org ro  " +
                    "WHERE f.ID=rf.functionid AND rf.roleid=ro.role_id " +
                    "AND ro.org_id=? AND f.functionurl = ?";
            orgAuthSize = this.getCountForJdbcParam(functionOfOrgSql,orgId,requestPath);
			return orgAuthSize > 0;
        }else{
			return true;
		}
	}

	/**
	 * 【AuthInterceptor】获取登录用户数据权限IDS
	 */
	@Override
	@Ehcache(cacheName="sysAuthCache")
	@Transactional(readOnly = true)
	public Set<String> getLoginDataRuleIdsByUserId(String userId,String functionId,String orgId) {
		Set<String> dataRuleIds = new HashSet<String>();
		List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id", role.getId());
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
			if (null != rFunctions && rFunctions.size() > 0) {
				TSRoleFunction tsRoleFunction = rFunctions.get(0);
				if (oConvertUtils.isNotEmpty(tsRoleFunction.getDataRule())) {
					String[] dataRuleArry = tsRoleFunction.getDataRule().split(",");
					for (int i = 0; i < dataRuleArry.length; i++) {
						dataRuleIds.add(dataRuleArry[i]);
					}
				}
			}
		}

		List<TSRoleOrg> tsRoleOrg = findByProperty(TSRoleOrg.class, "tsDepart.id", orgId);
		for (TSRoleOrg roleOrg : tsRoleOrg) {
			TSRole role = roleOrg.getTsRole();
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id", role.getId());
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
			if (null != rFunctions && rFunctions.size() > 0) {
				TSRoleFunction tsRoleFunction = rFunctions.get(0);
				if (oConvertUtils.isNotEmpty(tsRoleFunction.getDataRule())) {
					String[] dataRuleArry = tsRoleFunction.getDataRule().split(",");
					for (int i = 0; i < dataRuleArry.length; i++) {
						dataRuleIds.add(dataRuleArry[i]);
					}
				}
			}
		}

		logger.debug("-----[ 读取数据库获取数据权限集合IDS ]-------dataRuleIds: "+dataRuleIds+"--------userId: "+userId+"------functionId: "+ functionId);
		return dataRuleIds;
	}

	/**
	 * 【AuthInterceptor】获取登录用户的页面控件权限（表单权限、按钮权限）
	 * {逻辑说明： 查询菜单的页面控件权限，排除授权用户的页面控件权限，剩下未授权的页面控件权限}
	 *  @param userId 		用户ID
	 *  @param functionId 	菜单ID
	 */
	@Override
	@Ehcache(cacheName="sysAuthCache")
	@Transactional(readOnly = true)
	public List<TSOperation> getLoginOperationsByUserId(String userId, String functionId, String orgId) {
		String hql="FROM TSOperation where functionid = ?";
		List<TSOperation> operations = findHql(hql,functionId);
		if(operations == null || operations.size()<1){
			return null;
		}
		List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);

		for(TSRoleUser ru : rUsers){
			TSRole role = ru.getTSRole();
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id", role.getId());
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
			if (null != rFunctions && rFunctions.size() > 0) {
				TSRoleFunction tsRoleFunction = rFunctions.get(0);
				if (oConvertUtils.isNotEmpty(tsRoleFunction.getOperation())) {
					String[] operationArry = tsRoleFunction.getOperation().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						for(int j=0;j<operations.size();j++){
							if(operationArry[i].equals(operations.get(j).getId())){
								operations.remove(j);
								break;
							}
						}
					}
				}
			}
		}

		List<TSRoleOrg> tsRoleOrgs = findByProperty(TSRoleOrg.class, "tsDepart.id", orgId);
		for (TSRoleOrg tsRoleOrg : tsRoleOrgs) {
			TSRole role = tsRoleOrg.getTsRole();
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id", role.getId());
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
			if (null != rFunctions && rFunctions.size() > 0) {
				TSRoleFunction tsRoleFunction = rFunctions.get(0);
				if (oConvertUtils.isNotEmpty(tsRoleFunction.getOperation())) {
					String[] operationArry = tsRoleFunction.getOperation().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						for (int j = 0; j < operations.size(); j++) {
							if (operationArry[i].equals(operations.get(j).getId())) {
								operations.remove(j);
								break;
							}
						}
					}
				}
			}
		}


		logger.debug("-----[ 读取数据库获取操作权限集合operations ]-------operations: "+operations+"-------userId: "+userId+"------functionId: "+ functionId);
		return operations;
	}
	@Override
	public void saveRLRiver(TSLBaseRiverLake entity, String[] hzids){
		String parentCode = null;
		if (StringUtil.isEmpty(entity.getCode())) {
			if(entity.getParent()!=null&&oConvertUtils.isNotEmpty(entity.getParent().getCode())){
				parentCode = entity.getParent().getCode();
				String localMaxCode  = getURLRiverMaxLocalCode(parentCode,entity.getRiverLakeType());
				entity.setCode(YouBianCodeUtil.getSubYouBianCode(parentCode, localMaxCode));
			}else{
				String localMaxCode  = getURLRiverMaxLocalCode(null,entity.getRiverLakeType());
				entity.setParent(null);
				entity.setCode(YouBianCodeUtil.getNextYouBianCode(localMaxCode));
			}
		}


		if(StringUtil.isNotEmpty(entity.getId())){
			commonDao.executeSql("delete from t_sl_base_river_lake_user where base_river_lake_id=?", entity.getId());
			this.commonDao.updateEntitie(entity);
		}else{
			this.commonDao.save(entity);
		}

		//this.save(entity);
		this.saveUserRLRiverList(entity,hzids);
	}
	private void saveUserRLRiverList(TSLBaseRiverLake entity,String[] hzids) {
		if(hzids!=null && hzids.length>0){
			List<TSLBaseRiverLakeUser> userRLRiverList = new ArrayList<TSLBaseRiverLakeUser>();
			for (String hzid : hzids) {
				//if(StringUtils.isBlank(hzid))continue;
				RiverLakeChiefEntity riverLakeChief = new RiverLakeChiefEntity();
				riverLakeChief.setId(hzid);

        		TSLBaseRiverLakeUser userOrg = new TSLBaseRiverLakeUser();
        		userOrg.setRiverLakeChief(riverLakeChief);
        		userOrg.setTslBaseRiverLake(entity);

        		userRLRiverList.add(userOrg);

			}
			if (!userRLRiverList.isEmpty()) {
        		commonDao.batchSave(userRLRiverList);
        	}
		}
	}
	@Transactional(readOnly = true)
	private synchronized String getURLRiverMaxLocalCode(String parentCode,String riverLakeType){
		if(oConvertUtils.isEmpty(parentCode)){
			parentCode = "";
		}
		if(oConvertUtils.isEmpty(riverLakeType)){
			riverLakeType = "";
		}
		int localCodeLength = parentCode.length() + YouBianCodeUtil.zhanweiLength;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT code FROM t_sl_base_river_lake");
		if(ResourceUtil.getJdbcUrl().indexOf(JdbcDao.DATABSE_TYPE_SQLSERVER)!=-1){
			//sb.append(" where river_lake_type='"+riverLakeType+"' and LEN(code) = ").append(localCodeLength);
			sb.append(" where LEN(code) = ").append(localCodeLength);
		}else{
			sb.append(" where LENGTH(code) = ").append(localCodeLength);
		}

		if(oConvertUtils.isNotEmpty(parentCode)){
			sb.append(" and  code like '").append(parentCode).append("%'");
		}

		sb.append(" ORDER BY code DESC ");
		List<Map<String, Object>> objMapList = this.findForJdbc(sb.toString(), 1, 1);
		String returnCode = null;
		if(objMapList!=null&& objMapList.size()>0){
			returnCode = (String)objMapList.get(0).get("code");
		}

		return returnCode;
	}

	@Override
	@Transactional
	public void saveUserType(TSLUserTypeEntity entity){
		String parentCode = null;
		if(entity.getParent()!=null&&oConvertUtils.isNotEmpty(entity.getParent().getCode())){
			parentCode = entity.getParent().getCode();
			String localMaxCode  = getUserTypeMaxLocalCode(parentCode);
			entity.setCode(YouBianCodeUtil.getSubYouBianCode(parentCode, localMaxCode));
		}else{
			String localMaxCode  = getUserTypeMaxLocalCode(null);
			entity.setParent(null);
			entity.setCode(YouBianCodeUtil.getNextYouBianCode(localMaxCode));
		}

		this.save(entity);
	}
	@Transactional(readOnly = true)
	private synchronized String getUserTypeMaxLocalCode(String parentCode){
		if(oConvertUtils.isEmpty(parentCode)){
			parentCode = "";
		}
		int localCodeLength = parentCode.length() + YouBianCodeUtil.zhanweiLength;
		StringBuilder sb = new StringBuilder();

		if(ResourceUtil.getJdbcUrl().indexOf(JdbcDao.DATABSE_TYPE_SQLSERVER)!=-1){
			sb.append("SELECT code FROM t_sl_userType");
			sb.append(" where LEN(code) = ").append(localCodeLength);
		}else{
			sb.append("SELECT code FROM t_sl_userType");
			sb.append(" where LENGTH(code) = ").append(localCodeLength);
		}

		if(oConvertUtils.isNotEmpty(parentCode)){
			sb.append(" and  code like '").append(parentCode).append("%'");
		}

		sb.append(" ORDER BY code DESC ");
		List<Map<String, Object>> objMapList = this.findForJdbc(sb.toString(), 1, 1);
		String returnCode = null;
		if(objMapList!=null&& objMapList.size()>0){
			returnCode = (String)objMapList.get(0).get("code");
		}

		return returnCode;
	}

	@Override
	@Transactional
	public void updateByJSONObject(JSONObject jo){
		String sttable=jo.getString(UPDATE_BY_JSONOBJECT_TABLE);
		String sttype=jo.getString(UPDATE_BY_JSONOBJECT_TABLE_TYPE);
		if ("TSLBaseRiverLakePatrolEvent".equals(sttable)){
			//回访
			if ("doComplaintReturn".equals(sttype)){
				String stid=jo.getString("id");
				String streturnDate=jo.getString("returnDate");
				String massSatisP=jo.getString("massSatisP");
				TSLBaseRiverLakePatrolEvent entity =  this.getEntity(TSLBaseRiverLakePatrolEvent.class,  stid);
				String eventStat="25";
				try{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					entity.setReturnDate(sdf.parse(streturnDate));
				}catch(Exception ex){

				}
				entity.setMassSatisP(massSatisP);
				entity.setEventStatus(eventStat);
				commonDao.updateEntitie(entity);

				TSLBaseRiverLakePatrolEventProcess processEntity=new TSLBaseRiverLakePatrolEventProcess();
				TSUser u = ResourceUtil.getSessionUser();
				if (u==null){
					String stuserId=jo.getString("userid");
					u=this.get(TSUser.class, stuserId);
				}
				if(u!=null){
					processEntity.setTsSubmitUser(u);
				}
				String complainantName=entity.getComplainantName();
				String code=entity.getCode();

				String stdeptname="";
				List<TSUserOrg> ss=this.findByProperty(TSUserOrg.class, "tsUser.id", u.getId());
				for(TSUserOrg s:ss){
					String stdeptname1=s.getTsDepart().getDepartname();
					stdeptname=stdeptname1;

				}
				//[张先生]的投诉[TS2018000107]已被[徐州河长办]的[小刘]受理
				String processMemo="["+complainantName+"]的投诉["+code+"]已被";
				if (stdeptname.length()>0){
					processMemo=processMemo+"["+stdeptname+"]的";
				}
				processMemo=processMemo+"["+u.getRealName()+"]回访完成";
				processEntity.setProcessMemo(processMemo);

				Date today=new Date();
				processEntity.setProcessDate(today);
				processEntity.setApprovalMemo("");
				processEntity.setApprovalIsYN("Y");
				processEntity.setEventStat(eventStat);
				processEntity.setTslBaseRiverLakePatrolEvent(entity);
				commonDao.save(processEntity);
			}
			//处理
			if ("doComplaintProcess".equals(sttype)){
				String stid=jo.getString("id");
				String handleDept=jo.getString("handleDept");
				String handlePhone=jo.getString("handlePhone");
				String handleName=jo.getString("handleName");
				String handleType=jo.getString("handleType");
				String handleIsTF=jo.getString("handleIsTF");
				String handleDate=jo.getString("handleDate");
				String handleMemo=jo.getString("handleMemo");
				TSLBaseRiverLakePatrolEvent entity =  this.getEntity(TSLBaseRiverLakePatrolEvent.class,  stid);
				String eventStat="24";
				entity.setEventStatus(eventStat);
				try{
					entity.setHandleDept(handleDept);
					entity.setHandlePhone(handlePhone);
					entity.setHandleName(handleName);
					entity.setHandleType(handleType);
					entity.setHandleIsTF(handleIsTF);
					entity.setHandleMemo(handleMemo);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date hd=sdf.parse(handleDate);
					entity.setHandleDate(hd);
					java.util.Date ed=entity.getEventDate();
					int finishedDay= DateUtils.dateDiff('d',hd,ed);
					entity.setFinishedDay(finishedDay);
					List<TSLBaseRiverLakePatrolEventProcess> processList11=this.findByPropertyisOrder(TSLBaseRiverLakePatrolEventProcess.class, "tslBaseRiverLakePatrolEvent", entity,"processDate",false);
					if (processList11.size()>0){
						if ("23".equals(processList11.get(0).getEventStat())){
							//如果最后一第是转派就可以做为计算时间
							java.util.Date processDate=processList11.get(0).getProcessDate();
							int handleHour= DateUtils.dateDiff('h',hd,processDate);
							entity.setHandleHour(handleHour);
						}
					}
					List<TSLBaseRiverLakePatrolEventProcessDU> userDUList11=this.findByProperty(TSLBaseRiverLakePatrolEventProcessDU.class, "tslBaseRiverLakePatrolEvent", entity);
					if (userDUList11.size()>0){
						for(TSLBaseRiverLakePatrolEventProcessDU d:userDUList11){
							if (d.getiType()!=11){
								d.setiType(11);
								commonDao.updateEntitie(d);
							}
						}
					}
				}catch(Exception ex){

				}
				commonDao.updateEntitie(entity);

				TSLBaseRiverLakePatrolEventProcess processEntity=new TSLBaseRiverLakePatrolEventProcess();
				TSUser u = ResourceUtil.getSessionUser();
				if (u==null){
					String stuserId=jo.getString("userid");
					u=this.get(TSUser.class, stuserId);
				}
				if(u!=null){
					processEntity.setTsSubmitUser(u);
				}
				String complainantName=entity.getComplainantName();
				String code=entity.getCode();

				String stdeptname="";
				List<TSUserOrg> ss=this.findByProperty(TSUserOrg.class, "tsUser.id", u.getId());
				for(TSUserOrg s:ss){
					String stdeptname1=s.getTsDepart().getDepartname();
					stdeptname=stdeptname1;

				}
				//[张先生]的投诉[TS2018000107]已被[徐州河长办]的[小刘]受理
				String processMemo="["+complainantName+"]的投诉["+code+"]已被";
				if (stdeptname.length()>0){
					processMemo=processMemo+"["+stdeptname+"]的";
				}
				processMemo=processMemo+"["+u.getRealName()+"]处理完成";
				processEntity.setProcessMemo(processMemo);

				Date today=new Date();
				processEntity.setProcessDate(today);
				processEntity.setApprovalMemo(handleMemo);
				processEntity.setApprovalIsYN("Y");
				processEntity.setEventStat(eventStat);
				processEntity.setTslBaseRiverLakePatrolEvent(entity);
				commonDao.save(processEntity);
			}
			//转派
			if ("doComplaintTransfer".equals(sttype)){
				String stid=jo.getString("id");
				String stcontent=jo.getString("content");
				String sthzids=jo.getString("hzids");
				String sthzname=jo.getString("hzname");
				sthzname =StringUtil.trim(sthzname,",");

				TSLBaseRiverLakePatrolEvent entity =  this.getEntity(TSLBaseRiverLakePatrolEvent.class,  stid);
				String eventStat="23";
				entity.setEventStatus(eventStat);
				commonDao.updateEntitie(entity);
				TSLBaseRiverLakePatrolEventProcess processEntity=new TSLBaseRiverLakePatrolEventProcess();
				TSUser u = ResourceUtil.getSessionUser();
				if (u==null){
					String stuserId=jo.getString("userid");
					u=this.get(TSUser.class, stuserId);
				}
				if(u!=null){
					processEntity.setTsSubmitUser(u);
				}
				String complainantName=entity.getComplainantName();
				String code=entity.getCode();

				String stdeptname="";
				List<TSUserOrg> ss=this.findByProperty(TSUserOrg.class, "tsUser.id", u.getId());
				for(TSUserOrg s:ss){
					String stdeptname1=s.getTsDepart().getDepartname();
					stdeptname=stdeptname1;

				}
				//[张先生]的投诉[TS2018000107]已被[徐州河长办]的[小刘]受理
				String processMemo="["+complainantName+"]的投诉["+code+"]已被";
				if (stdeptname.length()>0){
					processMemo=processMemo+"["+stdeptname+"]的";
				}
				processMemo=processMemo+"["+u.getRealName()+"]转派给["+sthzname+"]";
				processEntity.setProcessMemo(processMemo);

				Date today=new Date();
				processEntity.setProcessDate(today);
				processEntity.setApprovalMemo(stcontent);
				processEntity.setApprovalIsYN("Y");
				processEntity.setEventStat(eventStat);
				processEntity.setTslBaseRiverLakePatrolEvent(entity);
				commonDao.save(processEntity);

				String[] hzids=sthzids.split(",");
				if(hzids!=null && hzids.length>0){

					List<TSLBaseRiverLakePatrolEventProcessDU> userDUList = new ArrayList<TSLBaseRiverLakePatrolEventProcessDU>();
					for (String hzid : hzids) {
						TSLBaseRiverLakePatrolEventProcessDU userDU = new TSLBaseRiverLakePatrolEventProcessDU();
						if (hzid.length()>=30){
							TSUser user = this.findUniqueByProperty(TSUser.class, "id", hzid);
							userDU.setTsHandleUser(user);
							userDU.setProcessType("10");
						}else{
							TSDepart dept=this.findUniqueByProperty(TSDepart.class, "orgCode", hzid);
							userDU.setTsHandleDepart(dept);
							userDU.setProcessType("11");
						}
						userDU.setTslBaseRiverLakePatrolEvent(entity);
						userDU.setTslBaseRiverLakePatrolEventProcess(processEntity);
						userDU.setiType(10);
						userDUList.add(userDU);
					}
					if (!userDUList.isEmpty()) {
						List<TSLBaseRiverLakePatrolEventProcessDU> userDUList11=this.findByProperty(TSLBaseRiverLakePatrolEventProcessDU.class, "tslBaseRiverLakePatrolEvent", entity);
						if (userDUList11.size()>0){
							for(TSLBaseRiverLakePatrolEventProcessDU d:userDUList11){
								if (d.getiType()!=11){
									d.setiType(11);
									commonDao.updateEntitie(d);
								}
							}
						}
		        		commonDao.batchSave(userDUList);
		        	}
				}

			}
			//送审
			if ("processComplaint".equals(sttype)){
				String stid=jo.getString("id");
				String ststatus=jo.getString("status");
				TSLBaseRiverLakePatrolEvent entity =  this.getEntity(TSLBaseRiverLakePatrolEvent.class,  stid);
				String eventStat=ststatus;
				entity.setEventStatus(eventStat);

				TSLBaseRiverLakePatrolEventProcess processEntity=new TSLBaseRiverLakePatrolEventProcess();
				TSUser u = ResourceUtil.getSessionUser();
				if (u==null){
					String stuserId=jo.getString("userid");
					u=this.get(TSUser.class, stuserId);
				}
				if(u!=null){
					processEntity.setTsSubmitUser(u);
				}
				String complainantName=entity.getComplainantName();
				String code=entity.getCode();

				String stdeptname="";
				List<TSUserOrg> ss=this.findByProperty(TSUserOrg.class, "tsUser.id", u.getId());
				for(TSUserOrg s:ss){
					String stdeptname1=s.getTsDepart().getDepartname();
					stdeptname=stdeptname1;

				}
				//[张先生]的投诉[TS2018000107]已被[徐州河长办]的[小刘]受理
				String processMemo="["+complainantName+"]的投诉["+code+"]已被";
				if (stdeptname.length()>0){
					processMemo=processMemo+"["+stdeptname+"]的";
				}
				processMemo=processMemo+"["+u.getRealName()+"]送审";
				processEntity.setProcessMemo(processMemo);

				Date today=new Date();
				processEntity.setProcessDate(today);
				processEntity.setApprovalMemo("");
				processEntity.setApprovalIsYN("Y");
				processEntity.setEventStat(eventStat);
				processEntity.setTslBaseRiverLakePatrolEvent(entity);
				commonDao.save(processEntity);
			}
			//审核
			if ("doComplaintCheck".equals(sttype)){
				String stid=jo.getString("id");
				String stcontent=jo.getString("content");
				String ststatus=jo.getString("status");
				TSLBaseRiverLakePatrolEvent entity =  this.getEntity(TSLBaseRiverLakePatrolEvent.class,  stid);
				if ("Y".equals(ststatus)){
					String eventStat="22";
					entity.setEventStatus(eventStat);
					commonDao.updateEntitie(entity);
					TSLBaseRiverLakePatrolEventProcess processEntity=new TSLBaseRiverLakePatrolEventProcess();
					TSUser u = ResourceUtil.getSessionUser();
					if (u==null){
						String stuserId=jo.getString("userid");
						u=this.get(TSUser.class, stuserId);
					}
					if(u!=null){
						processEntity.setTsSubmitUser(u);
					}
					String complainantName=entity.getComplainantName();
					String code=entity.getCode();

					String stdeptname="";
					List<TSUserOrg> ss=this.findByProperty(TSUserOrg.class, "tsUser.id", u.getId());
					for(TSUserOrg s:ss){
						String stdeptname1=s.getTsDepart().getDepartname();
						stdeptname=stdeptname1;

					}
					//[张先生]的投诉[TS2018000107]已被[徐州河长办]的[小刘]受理
					String processMemo="["+complainantName+"]的投诉["+code+"]已被";
					if (stdeptname.length()>0){
						processMemo=processMemo+"["+stdeptname+"]的";
					}
					processMemo=processMemo+"["+u.getRealName()+"]受理";
					processEntity.setProcessMemo(processMemo);

					Date today=new Date();
					processEntity.setProcessDate(today);
					processEntity.setApprovalMemo(stcontent);
					processEntity.setApprovalIsYN("Y");
					processEntity.setEventStat(eventStat);
					processEntity.setTslBaseRiverLakePatrolEvent(entity);
					commonDao.save(processEntity);
				}else{
					String eventStat="200";
					entity.setEventStatus(eventStat);
					commonDao.updateEntitie(entity);
					TSLBaseRiverLakePatrolEventProcess processEntity=new TSLBaseRiverLakePatrolEventProcess();
					TSUser u = ResourceUtil.getSessionUser();
					if (u==null){
						String stuserId=jo.getString("userid");
						u=this.get(TSUser.class, stuserId);
					}
					if(u!=null){
						processEntity.setTsSubmitUser(u);
					}
					String complainantName=entity.getComplainantName();
					String code=entity.getCode();

					String stdeptname="";
					List<TSUserOrg> ss=this.findByProperty(TSUserOrg.class, "tsUser.id", u.getId());
					for(TSUserOrg s:ss){
						String stdeptname1=s.getTsDepart().getDepartname();
						stdeptname=stdeptname1;

					}
					//张先生的投诉[TS2018000107]已被徐州河长办的小刘拒绝受理
					String processMemo=complainantName+"的投诉["+code+"]已";
					if (stdeptname.length()>0){
						processMemo=processMemo+stdeptname+"的";
					}
					processMemo=processMemo+u.getRealName()+"拒绝受理";
					processEntity.setProcessMemo(processMemo);

					Date today=new Date();
					processEntity.setProcessDate(today);
					processEntity.setApprovalMemo(stcontent);
					processEntity.setApprovalIsYN("N");
					processEntity.setEventStat(eventStat);
					processEntity.setTslBaseRiverLakePatrolEvent(entity);
					commonDao.save(processEntity);
				}
			}
		}
	}

	/**
	 *  首页信息收集接口实现
	 */
	@Override
	@Transactional
	public void homeInfoCollection() {
		LogUtil.info("===============首页信息收集开始=================");
		try {
			List<TSType> typeList = ResourceUtil.getCacheTypes("home_info_00");
			TSType type;
			TSType typeSave;

			String sql = "select river_lake_type,count(*) as count from t_sl_base_river_lake group by river_lake_type";
			List<Map<String, Object>> sqlList = findForJdbc(sql);
			for(int i=0;i<sqlList.size();i++){
				String st1=sqlList.get(i).get("river_lake_type").toString();
				String st2=sqlList.get(i).get("count").toString();
				if (RiverLakeTypeEnum.RIVER.getCode().equals(st1)){
					type=typeList.get(1);
					typeSave=getEntity(TSType.class, type.getId());
					typeSave.setTypename(st2);
					commonDao.save(typeSave);
				}
				if (RiverLakeTypeEnum.LAKE.getCode().equals(st1)){
					type=typeList.get(3);
					typeSave=getEntity(TSType.class, type.getId());
					typeSave.setTypename(st2);
					commonDao.save(typeSave);
				}
				if (RiverLakeTypeEnum.RESERVOIR.getCode().equals(st1)){
					type=typeList.get(4);
					typeSave=getEntity(TSType.class, type.getId());
					typeSave.setTypename(st2);
					commonDao.save(typeSave);
				}
				if (RiverLakeTypeEnum.WATER_FUNCTION.getCode().equals(st1)){
					type=typeList.get(6);
					typeSave=getEntity(TSType.class, type.getId());
					typeSave.setTypename(st2);
					commonDao.save(typeSave);
				}
			}
			sql = "select count(*) as count from t_sl_rl_river where riverLength>=5000";
			sqlList = findForJdbc(sql);
			for(int i=0;i<sqlList.size();i++){
				String st2=sqlList.get(i).get("count").toString();
				type=typeList.get(2);
				typeSave=getEntity(TSType.class, type.getId());
				typeSave.setTypename(st2);
				commonDao.save(typeSave);
			}
			sql = "select count(distinct user_id) as count from t_sl_base_river_lake_user ";
			sqlList = findForJdbc(sql);
			for(int i=0;i<sqlList.size();i++){
				String st2=sqlList.get(i).get("count").toString();
				type=typeList.get(5);
				typeSave=getEntity(TSType.class, type.getId());
				typeSave.setTypename(st2);
				commonDao.save(typeSave);
			}

			sql = "select sum(IF(LENGTH(primaryName)>0,1,0)) as count0,sum(IF(LENGTH(secondaryName)>0,1,0)) as count1,sum(IF(LENGTH(monitoringSection)>0,1,0)) as count2 from t_sl_rl_project_water_function";
			sqlList = findForJdbc(sql);
			for(int i=0;i<sqlList.size();i++){
				String st0=sqlList.get(i).get("count0").toString();
				String st1=sqlList.get(i).get("count1").toString();
				String st2=sqlList.get(i).get("count2").toString();
				type=typeList.get(7);
				typeSave=getEntity(TSType.class, type.getId());
				typeSave.setTypename(st0);
				commonDao.save(typeSave);
				type=typeList.get(8);
				typeSave=getEntity(TSType.class, type.getId());
				typeSave.setTypename(st1);
				commonDao.save(typeSave);
				type=typeList.get(9);
				typeSave=getEntity(TSType.class, type.getId());
				typeSave.setTypename(st2);
				commonDao.save(typeSave);
			}

			sql = "select count(*) as count0,sum(IF(water_evaluate=10,1,0)) as count1 from t_sl_rl_project_water_function_quality";
			sqlList = findForJdbc(sql);
			for(int i=0;i<sqlList.size();i++){
				String st0=sqlList.get(i).get("count0").toString();
				String st1=sqlList.get(i).get("count1").toString();

				try{
					int b=Integer.parseInt(st0);
					int a=Integer.parseInt(st1);
					double f1 = new BigDecimal(((float)a/b)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					DecimalFormat formatter = new DecimalFormat("#,###,###.##");
					type=typeList.get(10);
					typeSave=getEntity(TSType.class, type.getId());
					typeSave.setTypename(formatter.format(f1));
					commonDao.save(typeSave);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			sql = "select count(*) as count0 from t_sl_base_river_lake_patrol";
			sqlList = findForJdbc(sql);
			for(int i=0;i<sqlList.size();i++){
				String st0=sqlList.get(i).get("count0").toString();
				try{
					type=typeList.get(13);
					typeSave=getEntity(TSType.class, type.getId());
					typeSave.setTypename(st0);
					commonDao.save(typeSave);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			/*巡河累计发现各类问题
			 * 96322受理投诉累计发现各类问题
			 */
			sql = "select source_type,count(*) as count0,sum(IF(event_status=25,1,0)) as count1,sum(IF(event_status=24,1,0)) as count2 from t_sl_base_river_lake_patrol_event where source_type='10' or source_type='13' group by source_type ";
			sqlList = findForJdbc(sql);
			for(int i=0;i<sqlList.size();i++){
				String source_type=sqlList.get(i).get("source_type").toString();
				String st0=sqlList.get(i).get("count0").toString();
				String st1=sqlList.get(i).get("count1").toString();
				String st2=sqlList.get(i).get("count2").toString();
				try{
					if ("10".equals(source_type)){
						int b=Integer.parseInt(st0);
						int a1=Integer.parseInt(st1);
						int a2=Integer.parseInt(st2);
						int a=a1+a2;
						double f1 = new BigDecimal(((float)a/b)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						DecimalFormat formatter = new DecimalFormat("#,###,###.##");
						type=typeList.get(14);
						typeSave=getEntity(TSType.class, type.getId());
						typeSave.setTypename(b+"");
						commonDao.save(typeSave);
						type=typeList.get(15);
						typeSave=getEntity(TSType.class, type.getId());
						typeSave.setTypename(a+"");
						commonDao.save(typeSave);
						type=typeList.get(16);
						typeSave=getEntity(TSType.class, type.getId());
						typeSave.setTypename(formatter.format(f1));
						commonDao.save(typeSave);
					}
					if ("13".equals(source_type)){
						int b=Integer.parseInt(st0);
						int a1=Integer.parseInt(st1);
						int a2=Integer.parseInt(st2);
						int a=a1+a2;
						double f1 = new BigDecimal(((float)a/b)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						DecimalFormat formatter = new DecimalFormat("#,###,###.##");
						type=typeList.get(17);
						typeSave=getEntity(TSType.class, type.getId());
						typeSave.setTypename(b+"");
						commonDao.save(typeSave);
						type=typeList.get(18);
						typeSave=getEntity(TSType.class, type.getId());
						typeSave.setTypename(a+"");
						commonDao.save(typeSave);
						type=typeList.get(19);
						typeSave=getEntity(TSType.class, type.getId());
						typeSave.setTypename(formatter.format(f1));
						commonDao.save(typeSave);
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			typeList = ResourceUtil.getCacheTypes("home_info_00");
			type=typeList.get(0);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename("徐州");
			commonDao.save(typeSave);
			//统一刷新
			refleshTypesCach(typeSave);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try{
			String sql = " select d.id,d.name,d.mapCenterLngLat,count(*) as count0,"+
						 "sum(IF(water_evaluate=10,1,0)) as count1 from t_sl_rl_project_water_function_quality a"+
						 " left join t_sl_rl_project_water_function b on a.project_water_function_id=b.id"+
						 " left join t_sl_base_river_lake c on b.id=c.id"+
						 " left join t_sl_base_river_lake d on c.PARENT_CODE=d.code"+
						 " where LENGTH(d.mapCenterLngLat)>0 and LENGTH(d.mapZoom)>0"+
						 " group by d.id ";
			List<Map<String, Object>> sqlList = findForJdbc(sql);
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();

			JSONArray jsonArray10 = new JSONArray();
			JSONArray jsonArray11 = new JSONArray();
			for(int i=0;i<sqlList.size();i++){
				String st0=sqlList.get(i).get("id").toString();
				String st1=sqlList.get(i).get("name").toString();
				String st2=sqlList.get(i).get("mapCenterLngLat").toString();
				String st3=sqlList.get(i).get("count0").toString();
				String st4=sqlList.get(i).get("count1").toString();

				try{
					int b=Integer.parseInt(st3);
					int a=Integer.parseInt(st4);
					double f1 = new BigDecimal(((float)a/b)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					DecimalFormat formatter = new DecimalFormat("#,###,###.##");
					JSONObject jsonObject1 = new JSONObject();
					jsonObject1.put("name", st1);
					jsonObject1.put("value", formatter.format(f1));
					jsonArray.add(jsonObject1);
					jsonArray11.add(formatter.format(f1));

				}catch(Exception ex){
					ex.printStackTrace();
				}
				JSONObject mapCenterLngLat = JSONObject.fromObject(st2);
				String lng=mapCenterLngLat.getString("lng");
				String lat=mapCenterLngLat.getString("lat");
				JSONArray jsonArray1 = new JSONArray();
				jsonArray1.add(lng);
				jsonArray1.add(lat);
				jsonObject.put(st1, jsonArray1);

				jsonArray10.add(st1);
			}
			List<TSType> typeList = ResourceUtil.getCacheTypes("home_info_01");
			TSType type;
			TSType typeSave;
			type=typeList.get(0);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray.toString());
			commonDao.save(typeSave);
			type=typeList.get(1);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonObject.toString());
			commonDao.save(typeSave);
			//统一刷新
			refleshTypesCach(typeSave);

			typeList = ResourceUtil.getCacheTypes("home_info_05");
			type=typeList.get(0);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray10.toString());
			commonDao.save(typeSave);
			type=typeList.get(1);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray11.toString());
			commonDao.save(typeSave);
			//统一刷新
			refleshTypesCach(typeSave);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try{
			List<TSType> typeList = ResourceUtil.getCacheTypes("home_info_02");
			TSType type;
			TSType typeSave;
			String sql = " select b.id,b.divisionName,sum(a.should_number) as should_number,sum(a.visited_number) as visited_number"+
						 " from t_sl_patrol_report a left join t_sl_division b on a.division_divisionCode=b.code"+
						 " group by b.id,b.divisionName";
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray0 = new JSONArray();
			JSONArray jsonArray1 = new JSONArray();
			JSONArray jsonArray2 = new JSONArray();
			JSONArray jsonArray3 = new JSONArray();

			List<Map<String, Object>> sqlList = findForJdbc(sql);
			for(int i=0;i<sqlList.size();i++){
				String st0=sqlList.get(i).get("divisionName").toString();
				String st1=sqlList.get(i).get("should_number").toString();
				String st2=sqlList.get(i).get("visited_number").toString();
				int b=Integer.parseInt(st1);
				int a=Integer.parseInt(st2);
				double f1 = new BigDecimal(((float)a/b)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				DecimalFormat formatter = new DecimalFormat("#,###,###.##");
				jsonObject.put(st0, formatter.format(f1));
				jsonArray0.add(st0);
				jsonArray1.add(st1);
				jsonArray2.add(st2);
				jsonArray3.add(formatter.format(f1));

			}
			type=typeList.get(0);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonObject.toString());
			commonDao.save(typeSave);
			refleshTypesCach(typeSave);

			typeList = ResourceUtil.getCacheTypes("home_info_03");
			type=typeList.get(0);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray0.toString());
			commonDao.save(typeSave);
			type=typeList.get(1);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray1.toString());
			commonDao.save(typeSave);
			type=typeList.get(2);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray2.toString());
			commonDao.save(typeSave);
			type=typeList.get(3);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray3.toString());
			commonDao.save(typeSave);

			refleshTypesCach(typeSave);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try{
			List<TSType> typeList = ResourceUtil.getCacheTypes("home_info_04");
			TSType type;
			TSType typeSave;
			String sql = " select d.id,d.name,"+
						 " sum(IF(water_quality=10,1,0)) as count0,"+
					 	 " sum(IF(water_quality=11,1,0)) as count1,"+
					 	 " sum(IF(water_quality=12,1,0)) as count2,"+
					 	 " sum(IF(water_quality=13,1,0)) as count3,"+
					 	 " sum(IF(water_quality=14,1,0)) as count4"+
					 	 " from t_sl_rl_project_water_function_quality a"+
					 	 " left join t_sl_rl_project_water_function b on a.project_water_function_id=b.id"+
					 	 " left join t_sl_base_river_lake c on b.id=c.id"+
					 	 " left join t_sl_base_river_lake d on c.PARENT_CODE=d.code"+
					 	 " group by d.id";
			List<Map<String, Object>> sqlList = findForJdbc(sql);
			JSONArray jsonArray0 = new JSONArray();
			JSONArray jsonArray1 = new JSONArray();
			JSONArray jsonArray2 = new JSONArray();
			JSONArray jsonArray3 = new JSONArray();
			JSONArray jsonArray4 = new JSONArray();
			JSONArray jsonArray5 = new JSONArray();
			for(int i=0;i<sqlList.size();i++){
				//String st0=sqlList.get(i).get("id").toString();
				String st1=sqlList.get(i).get("name").toString();
				String st20=sqlList.get(i).get("count0").toString();
				String st21=sqlList.get(i).get("count1").toString();
				String st22=sqlList.get(i).get("count2").toString();
				String st23=sqlList.get(i).get("count3").toString();
				String st24=sqlList.get(i).get("count4").toString();

				int b20=Integer.parseInt(st20);
				int b21=Integer.parseInt(st21);
				int b22=Integer.parseInt(st22);
				int b23=Integer.parseInt(st23);
				int b24=Integer.parseInt(st24);

				jsonArray0.add(st1);
				int b=b20+b21+b22+b23+b24;
				int a=b20;
				double f1 = new BigDecimal(((float)a/b)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				DecimalFormat formatter = new DecimalFormat("#,###,###.##");
				jsonArray1.add(formatter.format(f1));

				a=b21;
				f1 = new BigDecimal(((float)a/b)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				jsonArray2.add(formatter.format(f1));

				a=b22;
				f1 = new BigDecimal(((float)a/b)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				jsonArray3.add(formatter.format(f1));

				a=b23;
				f1 = new BigDecimal(((float)a/b)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				jsonArray4.add(formatter.format(f1));

				a=b24;
				f1 = new BigDecimal(((float)a/b)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				jsonArray5.add(formatter.format(f1));
			}

			type=typeList.get(0);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray0.toString());
			commonDao.save(typeSave);
			type=typeList.get(1);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray1.toString());
			commonDao.save(typeSave);
			type=typeList.get(2);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray2.toString());
			commonDao.save(typeSave);
			type=typeList.get(3);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray3.toString());
			commonDao.save(typeSave);
			type=typeList.get(4);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray4.toString());
			commonDao.save(typeSave);
			type=typeList.get(5);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray5.toString());
			commonDao.save(typeSave);
			//统一刷新
			refleshTypesCach(typeSave);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try{
			List<TSType> questTypeList = ResourceUtil.getCacheTypes("questType".toLowerCase());

			List<TSType> typeList = ResourceUtil.getCacheTypes("home_info_06");
			TSType type;
			TSType typeSave;
			String sql = " select quest_type,count(*) as count0,"+
						 " sum(IF(event_status=25,1,0)) as count1,"+
						 " sum(IF(event_status=24,1,0)) as count2 "+
						 " from t_sl_base_river_lake_patrol_event group by quest_type";
			List<Map<String, Object>> sqlList = findForJdbc(sql);
			JSONArray jsonArray0 = new JSONArray();
			JSONArray jsonArray1 = new JSONArray();
			JSONArray jsonArray2 = new JSONArray();
			JSONArray jsonArray3 = new JSONArray();
			for(int i=0;i<sqlList.size();i++){
				String st1=sqlList.get(i).get("quest_type").toString();
				String st20=sqlList.get(i).get("count0").toString();
				String st21=sqlList.get(i).get("count1").toString();
				String st22=sqlList.get(i).get("count2").toString();
				int b20=Integer.parseInt(st20);
				int b21=Integer.parseInt(st21);
				int b22=Integer.parseInt(st22);
				int b=b21+b22;
				for(int j=0;j<questTypeList.size();j++){
					if (questTypeList.get(j).getTypecode().equals(st1)){
						jsonArray0.add(questTypeList.get(j).getTypename());
						jsonArray1.add(b20);
						jsonArray2.add(b);
						double f1 = new BigDecimal(((float)b/b20)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						DecimalFormat formatter = new DecimalFormat("#,###,###.##");
						jsonArray3.add(formatter.format(f1));
						break;
					}
				}
			}
			type=typeList.get(0);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray0.toString());
			commonDao.save(typeSave);
			type=typeList.get(1);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray1.toString());
			commonDao.save(typeSave);
			type=typeList.get(2);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray2.toString());
			commonDao.save(typeSave);
			type=typeList.get(3);
			typeSave=getEntity(TSType.class, type.getId());
			typeSave.setTypename(jsonArray3.toString());
			commonDao.save(typeSave);
			//统一刷新
			refleshTypesCach(typeSave);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogUtil.info("===============首页信息收集结束=================");
	}


	///////////////// Synchronized to the Activiti //////////////////

	// 已废弃，同步见：ActGroupEntityServiceFactory.java、ActUserEntityServiceFactory.java

	/**
	 * 是需要同步Activiti数据，如果从未同步过，则同步数据。
	 */
	private static boolean isSynActivitiIndetity = true;
	@Override
	public void afterPropertiesSet() throws Exception {
		if (!Globals.isSynActivitiIndetity()){
			return;
		}
		if (isSynActivitiIndetity){
			isSynActivitiIndetity = false;
			// 同步角色数据
			List<Group> groupList = identityService.createGroupQuery().list();
			if (groupList.size() == 0){
				List<TSRole> roleList = getList(TSRole.class);
				Iterator<TSRole> roles = roleList.iterator();
				while(roles.hasNext()) {
					TSRole role = roles.next();
					saveActivitiGroup(role);
				}
			}
			// 同步用户数据
			List<org.activiti.engine.identity.User> userList = identityService.createUserQuery().list();
			if (userList.size() == 0){
				List<TSUser> buniessUserList = getList(TSUser.class);
				Iterator<TSUser> users = buniessUserList.iterator();
				while(users.hasNext()) {
					saveActivitiUser(users.next());
				}
			}
		}
	}

	@Override
	public void saveActivitiGroup(TSRole role) {
		if (!Globals.isSynActivitiIndetity()){
			return;
		}
		String groupId = role.getRoleCode();

		// 先删除原Activiti角色
		identityService.deleteGroup(role.getRoleCode());

		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			group = identityService.newGroup(groupId);
		}
		group.setName(role.getRoleName());
		group.setType(role.getRoleType());
		identityService.saveGroup(group);

		// 删除用户与用户组关系
		List<org.activiti.engine.identity.User> activitiUserList = identityService.createUserQuery().memberOfGroup(groupId).list();
		for (org.activiti.engine.identity.User activitiUser : activitiUserList){
			identityService.deleteMembership(activitiUser.getId(), groupId);
		}

		// 创建用户与用户组关系
		List<TSRoleUser> roleUserList = this.commonDao.findByProperty(TSRoleUser.class, "TSRole.id", role.getId());
		List<TSUser> userList = roleUserList.stream().map(TSRoleUser::getTSUser).collect(Collectors.toList());
		for (TSUser e : userList){
			String userId = e.getUserName();
			// 如果该用户不存在，则创建一个
			org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
			if (activitiUser == null){
				activitiUser = identityService.newUser(userId);
				activitiUser.setFirstName(e.getRealName());
				activitiUser.setLastName(org.jeecgframework.p3.core.utils.common.StringUtils.EMPTY);
				activitiUser.setEmail(e.getEmail());
				activitiUser.setPassword(org.jeecgframework.p3.core.utils.common.StringUtils.EMPTY);
				identityService.saveUser(activitiUser);
			}
			identityService.createMembership(userId, groupId);
		}
	}

	@Override
	public void deleteActivitiGroup(TSRole role) {
		if (!Globals.isSynActivitiIndetity()){
			return;
		}
		if(role!=null) {
			String groupId = role.getRoleCode();
			identityService.deleteGroup(groupId);
		}
	}

	@Override
	public void saveActivitiUser(TSUser user) {
		if (!Globals.isSynActivitiIndetity()){
			return;
		}
		String userId = user.getUserName();//ObjectUtils.toString(user.getId());
		org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
		if (activitiUser == null) {
			activitiUser = identityService.newUser(userId);
		}
		activitiUser.setFirstName(user.getRealName());
		activitiUser.setLastName(org.jeecgframework.p3.core.utils.common.StringUtils.EMPTY);
		activitiUser.setEmail(user.getEmail());
		activitiUser.setPassword(org.jeecgframework.p3.core.utils.common.StringUtils.EMPTY);
		identityService.saveUser(activitiUser);

		// 删除用户与用户组关系
		List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
		for (Group group : activitiGroups) {
			identityService.deleteMembership(userId, group.getId());
		}
		// 创建用户与用户组关系
		List<TSRole> roleList = this.commonDao.listRole(user);
		for (TSRole role : roleList) {
			String groupId = role.getRoleCode();
			// 如果该用户组不存在，则创建一个
			Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
			if(group == null) {
				group = identityService.newGroup(groupId);
				group.setName(role.getRoleName());
				group.setType(role.getRoleType());
				identityService.saveGroup(group);
			}
			identityService.createMembership(userId, role.getRoleCode());
		}
	}

	@Override
	public void deleteActivitiUser(TSUser user) {
		if (!Globals.isSynActivitiIndetity()){
			return;
		}
		if(user!=null) {
			String userId = user.getUserName();
			identityService.deleteUser(userId);
		}
	}

	///////////////// Synchronized to the Activiti end //////////////////
}
