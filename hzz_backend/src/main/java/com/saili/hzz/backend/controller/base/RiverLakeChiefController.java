package com.saili.hzz.backend.controller.base;
import com.saili.hzz.core.extend.hqlsearch.HqlGenerateUtil;
import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.backend.service.base.RiverLakeChiefService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saili.hzz.core.constant.Constants;
import com.saili.hzz.core.common.model.json.ComboTree;
import com.saili.hzz.core.common.model.json.ValidForm;
import com.saili.hzz.core.util.oConvertUtils;
import com.saili.hzz.web.system.pojo.base.TSUser;
import com.saili.hzz.web.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.saili.hzz.core.common.controller.BaseController;
import com.saili.hzz.core.common.exception.BusinessException;
import com.saili.hzz.core.common.hibernate.qbc.CriteriaQuery;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.core.constant.Globals;
import com.saili.hzz.core.util.StringUtil;
import com.saili.hzz.tag.core.easyui.TagUtil;
import com.saili.hzz.web.system.service.SystemService;
import com.saili.hzz.core.util.MyBeanUtils;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import com.saili.hzz.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;


/**   
 * @Title: Controller  
 * @Description: 河湖长名录
 * @author onlineGenerator
 * @date 2019-06-26 10:05:13
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/riverLakeChiefController")
public class RiverLakeChiefController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RiverLakeChiefController.class);

	@Autowired
	private RiverLakeChiefService riverLakeChiefService;
	@Autowired
	private SystemService systemService;
	@Autowired
    private UserService userService;

	@RequestMapping(value = "checkAccount")
	@ResponseBody
	public ValidForm checkAccount(String param) {
		ValidForm v = new ValidForm();
		v.setInfo("验证通过！");
		v.setStatus("y");
		String accountName = oConvertUtils.getString(param);
		riverLakeChiefService.checkAccount(accountName, v);
		return v;
	}

	/**
	 * 选择河长页面
	 *
	 * @return
	 */
	@RequestMapping(params = "riverLakeChiefs")
	public ModelAndView riverLakeChiefs(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("modules/hzz/components/riverLakeChiefChoose");
		String ids = oConvertUtils.getString(request.getParameter("ids"));
		mv.addObject("ids", ids);
		return mv;
	}

	/**
	 * 河湖长名录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("modules/hzz/riverlakechief/riverLakeChiefList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(RiverLakeChiefEntity riverLakeChief,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RiverLakeChiefEntity.class, dataGrid);
		//查询条件组装器
		HqlGenerateUtil.installHql(cq, riverLakeChief, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.riverLakeChiefService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除河湖长名录
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(RiverLakeChiefEntity riverLakeChief, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		riverLakeChief = systemService.getEntity(RiverLakeChiefEntity.class, riverLakeChief.getId());
		message = "河湖长名录删除成功";
		try{
			riverLakeChiefService.delete(riverLakeChief);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "河湖长名录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除河湖长名录
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "河湖长名录删除成功";
		try{
			for(String id:ids.split(",")){
				RiverLakeChiefEntity riverLakeChief = systemService.getEntity(RiverLakeChiefEntity.class, 
				id
				);
				riverLakeChiefService.delete(riverLakeChief);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "河湖长名录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加河湖长名录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(RiverLakeChiefEntity riverLakeChief, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "河湖长名录添加成功";
		try{
			List<TSUser> users = systemService.findByProperty(TSUser.class,"userName",riverLakeChief.getAccount().getUserName());
			if(users.size() != 0){
				riverLakeChief.setAccount(users.get(0));
			}
			riverLakeChiefService.save(riverLakeChief);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		catch(Exception e){
			e.printStackTrace();
			message = "河湖长名录添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新河湖长名录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(RiverLakeChiefEntity riverLakeChief, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "河湖长名录更新成功";
		RiverLakeChiefEntity t = riverLakeChiefService.get(RiverLakeChiefEntity.class, riverLakeChief.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(riverLakeChief, t);
			riverLakeChiefService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "河湖长名录更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 河湖长名录新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(RiverLakeChiefEntity riverLakeChief, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(riverLakeChief.getId())) {
			riverLakeChief = riverLakeChiefService.getEntity(RiverLakeChiefEntity.class, riverLakeChief.getId());
			req.setAttribute("riverLakeChiefPage", riverLakeChief);
		}
		return new ModelAndView("modules/hzz/riverlakechief/riverLakeChief-add");
	}
	/**
	 * 河湖长名录编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(RiverLakeChiefEntity riverLakeChief, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(riverLakeChief.getId())) {
			riverLakeChief = riverLakeChiefService.getEntity(RiverLakeChiefEntity.class, riverLakeChief.getId());
			req.setAttribute("riverLakeChiefPage", riverLakeChief);
		}
		return new ModelAndView("modules/hzz/riverlakechief/riverLakeChief-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","riverLakeChiefController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(RiverLakeChiefEntity riverLakeChief,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(RiverLakeChiefEntity.class, dataGrid);
		HqlGenerateUtil.installHql(cq, riverLakeChief, request.getParameterMap());
		List<RiverLakeChiefEntity> riverLakeChiefs = this.riverLakeChiefService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"河湖长名录");
		modelMap.put(NormalExcelConstants.CLASS,RiverLakeChiefEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("河湖长名录列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,riverLakeChiefs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(RiverLakeChiefEntity riverLakeChief,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"河湖长名录");
    	modelMap.put(NormalExcelConstants.CLASS,RiverLakeChiefEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("河湖长名录列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<RiverLakeChiefEntity> listRiverLakeChiefEntitys = ExcelImportUtil.importExcel(file.getInputStream(),RiverLakeChiefEntity.class,params);
				for (RiverLakeChiefEntity riverLakeChief : listRiverLakeChiefEntitys) {
					riverLakeChiefService.save(riverLakeChief);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(e.getMessage());
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

	/**
	 * 同级河长树
	 * @author whuab_mc
	 * @date 2019-08-29 14:16:48
	 * @return
	 */
	@RequestMapping("sameLevelRiverTree")
	@ResponseBody
	public List<ComboTree> sameLevelRiverTree() {
		List<ComboTree> results;
		try {
			TSUser user = ResourceUtil.getSessionUser();
			RiverLakeChiefEntity riverLakeChief = riverLakeChiefService.getByUser(user);
			String divisionCode = riverLakeChief.getDivisionCode();
			if (StringUtil.isEmpty(divisionCode)) {
				return null;
			}
			results = riverLakeChiefService.listSameLevelRiverTree(divisionCode);
		} catch (NullPointerException e) {
			results = riverLakeChiefService.listSameLevelRiverTree(Constants.ROOT_DIVISION_CODE);
		}
		return results;
	}

	/**
	 * 下级河长树
	 * @author whuab_mc
	 * @date 2019-08-29 14:16:32
	 * @return
	 */
	@RequestMapping("lowerLevelRiverTree")
	@ResponseBody
	public List<ComboTree> lowerLevelRiverTree() {
		List<ComboTree> results;
		try {
			TSUser user = ResourceUtil.getSessionUser();
			RiverLakeChiefEntity riverLakeChief = riverLakeChiefService.getByUser(user);
			String parentCode = riverLakeChief.getDivisionCode();
			if (StringUtil.isEmpty(parentCode)) {
			    return null;
            }
			results = riverLakeChiefService.listLowerLevelRiverTree(parentCode);
		} catch (NullPointerException e) {
			results = riverLakeChiefService.listLowerLevelRiverTree(Constants.ROOT_DIVISION_CODE);
		}
		return results;
	}
}
