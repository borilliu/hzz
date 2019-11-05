package com.saili.hzz.backend.controller.base;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saili.hzz.core.extend.hqlsearch.HqlGenerateUtil;
import com.saili.hzz.backend.service.base.DivisionService;
import com.saili.hzz.core.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.entity.TSLDivisionExcelView;

import com.saili.hzz.core.common.controller.BaseController;
import com.saili.hzz.core.common.dao.jdbc.JdbcDao;
import com.saili.hzz.core.common.hibernate.qbc.CriteriaQuery;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.ComboTree;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.core.common.model.json.TreeGrid;
import com.saili.hzz.core.constant.Globals;
import com.saili.hzz.core.util.YouBianCodeUtil;
import com.saili.hzz.core.util.oConvertUtils;
import com.saili.hzz.tag.vo.datatable.SortDirection;
import com.saili.hzz.tag.vo.easyui.ComboTreeModel;
import com.saili.hzz.tag.vo.easyui.TreeGridModel;
import com.saili.hzz.web.system.service.SystemService;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.hibernate.criterion.Restrictions;
import com.saili.hzz.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import com.saili.hzz.core.util.ExceptionUtil;
import com.saili.hzz.core.util.MutiLangUtil;


/**   
 * @Title: Controller  
 * @Description: 行政区划
 * @author liuby
 * @date 2018-12-28 10:55:13
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/slDivisionController")
public class SlDivisionController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SlDivisionController.class);
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private DivisionService divisionService;

	
	private static final String CONTROLLER_LIST = "modules/hzz/slDivision/slDivisionList";
	private static final String CONTROLLER_ADD_OR_UPDATE = "modules/hzz/slDivision/slDivision";

	/**
	 * 行政区划列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public String list(HttpServletRequest request) {
		return CONTROLLER_LIST;
	}
	
	@RequestMapping(params = "divisiongrid")
	@ResponseBody
	public Object divisiongrid(TSLDivisionEntity tSLDivision,HttpServletRequest request, HttpServletResponse response, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(TSLDivisionEntity.class);
		if("yes".equals(request.getParameter("isSearch"))){
			treegrid.setId(null);
			tSLDivision.setId(null);
		} 
		if(null != tSLDivision.getName()){
			//org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSLDivision);
			//cq.like("divisionName", tSLDivision.getDivisionName());
			cq.add(Restrictions.sqlRestriction("name like '%" + tSLDivision.getName() + "%'"));
		}
		if (treegrid.getId() != null) {
			cq.eq("parent.code", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("parent");
		}

		cq.addOrder("sort", SortDirection.asc);
		cq.add();
		List<TreeGrid> divisionList =null;
		divisionList=systemService.getListByCriteriaQuery(cq, false);
		if(divisionList.size()==0 && tSLDivision.getName()!=null){
			cq = new CriteriaQuery(TSLDivisionEntity.class);
			TSLDivisionEntity parDivision = new TSLDivisionEntity();
			tSLDivision.setParent(parDivision);
			HqlGenerateUtil.installHql(cq, tSLDivision);
			divisionList =systemService.getListByCriteriaQuery(cq, false);
		}

		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("name");
		treeGridModel.setParentText("parent_name");
		treeGridModel.setParentId("parent_code");
		treeGridModel.setSrc("id");
		treeGridModel.setIdField("code");
		treeGridModel.setChildList("childrenList");
		Map<String,Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("code", "code");
        fieldMap.put("sort", "sort");
		fieldMap.put("remarks", "remarks");
        treeGridModel.setFieldMap(fieldMap);
        treeGrids = systemService.treegrid(divisionList, treeGridModel);

        JSONArray jsonArray = new JSONArray();
        for (TreeGrid treeGrid : treeGrids) {
            jsonArray.add(JSON.parse(treeGrid.toJson()));
        }
        return jsonArray;
	}
	
	@RequestMapping(params = "goAdd")
	public String goAdd(TSLDivisionEntity division, HttpServletRequest req) {
		List<TSLDivisionEntity> divisionList = systemService.getList(TSLDivisionEntity.class);
		req.setAttribute("divisionList", divisionList);
        req.setAttribute("pid", division.getId());
		return CONTROLLER_ADD_OR_UPDATE;
	}
	
	@RequestMapping(params = "goUpdate")
	public String goUpdate(TSLDivisionEntity division, HttpServletRequest req) {
		List<TSLDivisionEntity> divisionList = systemService.getList(TSLDivisionEntity.class);
		req.setAttribute("divisionList", divisionList);
		if (StringUtils.isNotEmpty(division.getId())) {
			//division = systemService.getEntity(TSLDivision.class, division.getId());
			division = systemService.findUniqueByProperty(TSLDivisionEntity.class,
					"code",division.getId());
			req.setAttribute("division", division);
		}
		return CONTROLLER_ADD_OR_UPDATE;
	}
	
	/**
	 * 添加
	 *
	 * @return
	 */
	@RequestMapping(params = "doSave")
	@ResponseBody
	public AjaxJson doSave(TSLDivisionEntity division, HttpServletRequest request) {
		String message = null;
		// 设置上级部门
		String pid = request.getParameter("parent.code");
		if (pid.equals("")) {
			division.setParent(null);
		}
		AjaxJson j = new AjaxJson();
		if (StringUtils.isNotEmpty(division.getId())) {
			systemService.saveOrUpdate(division);
			message = MutiLangUtil.paramUpdSuccess("base.division");
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);

		} else {

//			String orgCode = systemService.generateOrgCode(depart.getId(), pid);
//			depart.setOrgCode(orgCode);
			if(oConvertUtils.isNotEmpty(pid)){
				TSLDivisionEntity paretDivision = systemService.findUniqueByProperty(TSLDivisionEntity.class, "code", pid);
				String localMaxCode  = getMaxLocalCode(paretDivision.getCode());
				division.setCode(YouBianCodeUtil.getSubYouBianCode(paretDivision.getCode(), localMaxCode));
			}else{
				String localMaxCode  = getMaxLocalCode(null);
				division.setCode(YouBianCodeUtil.getNextYouBianCode(localMaxCode));
			}

			systemService.save(division);
			message = MutiLangUtil.paramAddSuccess("base.division");
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

        }
		j.setMsg(message);
		return j;
		
				
	}

	private synchronized String getMaxLocalCode(String parentCode){
		if(oConvertUtils.isEmpty(parentCode)){
			parentCode = "";
		}
		int localCodeLength = parentCode.length() + YouBianCodeUtil.zhanweiLength;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT code FROM t_sl_division");

		if(ResourceUtil.getJdbcUrl().indexOf(JdbcDao.DATABSE_TYPE_SQLSERVER)!=-1){
			sb.append(" where LEN(code) = ").append(localCodeLength);
		}else{
			sb.append(" where LENGTH(code) = ").append(localCodeLength);
		}

		if(oConvertUtils.isNotEmpty(parentCode)){
			sb.append(" and  code like '").append(parentCode).append("%'");
		}

		sb.append(" ORDER BY code DESC");
		List<Map<String, Object>> objMapList = systemService.findForJdbc(sb.toString(), 1, 1);
		String returnCode = null;
		if(objMapList!=null && objMapList.size()>0){
			returnCode = (String)objMapList.get(0).get("code");
		}

		return returnCode;
	}
	
	
	@RequestMapping(params = "goDel")
	@ResponseBody
	public AjaxJson goDel(TSLDivisionEntity division, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//division = systemService.getEntity(TSLDivision.class, division.getId());
		division =systemService.findUniqueByProperty(TSLDivisionEntity.class, "code", division.getId());
        message = MutiLangUtil.paramDelSuccess("base.division");
        if (division.getChildrenList().size() == 0) {
            systemService.delete(division);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } else {
            message = MutiLangUtil.paramDelFail("base.division,!");
        }
        j.setMsg(message);
		return j;
	}
	
	/**
	 * 父级权限列表
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
		/*if (comboTree.getId() != null) {
			cq.eq("parent.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("parent");
		}*/
		// 只查询江苏省与其下级地区
		cq.eq("code", Constants.ROOT_DIVISION_CODE);
		cq.addOrder("sort", SortDirection.asc);

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

    /**
     * 行政区划菜单树
     *
     * @param request
     * @param comboTree
     * @return
     */
    @RequestMapping(params = "divisionTree")
    @ResponseBody
    public List<ComboTree> divisionTree(HttpServletRequest request, ComboTree comboTree) {
        List<ComboTree> results = new ArrayList<ComboTree>();
		String divisionCode = StringUtils.isNotBlank(request.getParameter("selfId")) ? request.getParameter("selfId") : Constants.ROOT_DIVISION_CODE;
        List<TSLDivisionEntity> divisionsList = divisionService.parseDivisionTree(divisionCode);

        TSLDivisionEntity defaultDivision = new TSLDivisionEntity();
        defaultDivision.setId("");
        defaultDivision.setName("请选择行政区划");
        divisionsList.add(0, defaultDivision);

        ComboTreeModel comboTreeModel = new ComboTreeModel("code", "name", "childrenList");
        results = systemService.ComboTree(divisionsList, comboTreeModel, null, true);
        return results;
    }
	
	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TSLDivisionEntity tslDivision, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TSLDivisionEntity.class, dataGrid);
		HqlGenerateUtil.installHql(cq, tslDivision, request.getParameterMap());
		cq.addOrder("sort", SortDirection.asc);
		List<TSLDivisionEntity> tsDivisions = this.systemService.getListByCriteriaQuery(cq,false);
		
		modelMap.put(NormalExcelConstants.FILE_NAME,"行政区划表");
		modelMap.put(NormalExcelConstants.CLASS,TSLDivisionEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("行政区划表列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tsDivisions);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "uploadDivision")
	public ModelAndView uploadDivision(HttpServletRequest req) {
		req.setAttribute("controller_name","slDivisionController");
		req.setAttribute("method_name", "importDivisionExcel");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	@RequestMapping(params = "importDivisionExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importDivisionExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			try {
				List<TSLDivisionExcelView> list = ExcelImportUtil.importExcel(file.getInputStream(),TSLDivisionExcelView.class,params);
				Map<String,TSLDivisionEntity> divisionMap=new HashMap<String, TSLDivisionEntity>();
				saveDivisionExcelView(list,divisionMap);
				if(divisionMap.isEmpty()){
					j.setMsg("必须有一个或一个以上的祖先节点！");
					return j;
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	private void saveDivisionExcelView(List<TSLDivisionExcelView> list,Map<String,TSLDivisionEntity> divisionMap){
		Iterator<TSLDivisionExcelView> iterator = list.iterator();
		while(iterator.hasNext()){
			TSLDivisionExcelView next = iterator.next();
			String parentId=next.getParentdivisionid();
			if(StringUtils.isEmpty(parentId)){
				TSLDivisionEntity depart=generateDivision(next,null);
				divisionMap.put(next.getId(), depart);
				iterator.remove();
			}else if(divisionMap.containsKey(parentId)){
				TSLDivisionEntity parentDivision=divisionMap.get(parentId);
				TSLDivisionEntity division=generateDivision(next,parentDivision);
				divisionMap.put(next.getId(), division);
				iterator.remove();
			}
		}
		if(divisionMap.isEmpty()){
			return;
		}
		if(!list.isEmpty()) {
			saveDivisionExcelView(list,divisionMap);
		}
	}
	
	private TSLDivisionEntity generateDivision(TSLDivisionExcelView next,TSLDivisionEntity parentDivision){
		TSLDivisionEntity division=new TSLDivisionEntity();
		division.setName(next.getDivisionName());
		division.setRemarks(next.getDivisionMemo());
		division.setSort(next.getDivisionOrder());
		if(parentDivision!=null){
			String localMaxCode  = getMaxLocalCode(parentDivision.getCode());
			division.setCode(YouBianCodeUtil.getSubYouBianCode(parentDivision.getCode(), localMaxCode));
			division.setParent(parentDivision);
		}else{
			String localMaxCode  = getMaxLocalCode(null);
			division.setCode(YouBianCodeUtil.getNextYouBianCode(localMaxCode));
		}
		this.systemService.save(division);
		return division;
	}

	@RequestMapping("/listCity")
	@ResponseBody
	public List<TSLDivisionEntity> listCity() {
		// 获取江苏省下面所有的市
		return divisionService.listByParentCode(Constants.ROOT_DIVISION_CODE);
	}

	@RequestMapping("/listByParentCode")
	@ResponseBody
	public List<TSLDivisionEntity> listByParentCode(String divisionCode) {
		// 获取指定区域下面的子集行政区划
		return divisionService.listByParentCode(divisionCode);
	}

	@RequestMapping("/listDivision")
	@ResponseBody
	public TSLDivisionEntity listDivision(String parentCode) {
		List<TSLDivisionEntity> divisionEntityList = divisionService.listContainParentCode(parentCode);
		TSLDivisionEntity division = divisionService.parseDivisionTree(parentCode, divisionEntityList);
		return division;
	}
}
