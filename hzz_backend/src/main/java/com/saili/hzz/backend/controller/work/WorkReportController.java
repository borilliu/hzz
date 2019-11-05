package com.saili.hzz.backend.controller.work;

import com.saili.hzz.core.entity.WorkReportEntity;
import com.saili.hzz.backend.service.work.WorkReportServiceI;
import com.saili.hzz.core.common.controller.BaseController;
import com.saili.hzz.core.common.exception.BusinessException;
import com.saili.hzz.core.common.hibernate.qbc.CriteriaQuery;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.core.constant.Globals;
import com.saili.hzz.core.util.MyBeanUtils;
import com.saili.hzz.core.util.ResourceUtil;
import com.saili.hzz.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import com.saili.hzz.tag.core.easyui.TagUtil;
import com.saili.hzz.web.cgform.entity.upload.CgUploadEntity;
import com.saili.hzz.web.cgform.service.config.CgFormFieldServiceI;
import com.saili.hzz.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: Controller  
 * @Description: 工作简报信息表
 * @author onlineGenerator
 * @date 2019-10-30 16:08:53
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/workReportController")
public class WorkReportController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(WorkReportController.class);

	@Autowired
	private WorkReportServiceI workReportService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	


	/**
	 * 工作简报信息表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("modules/hzz/work/workreport/workReportList");
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
	public void datagrid(WorkReportEntity workReport,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WorkReportEntity.class, dataGrid);
		//查询条件组装器
		com.saili.hzz.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, workReport, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.workReportService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除工作简报信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WorkReportEntity workReport, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		workReport = systemService.getEntity(WorkReportEntity.class, workReport.getId());
		message = "工作简报信息表删除成功";
		try{
			workReportService.delete(workReport);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "工作简报信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除工作简报信息表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "工作简报信息表删除成功";
		try{
			for(String id:ids.split(",")){
				WorkReportEntity workReport = systemService.getEntity(WorkReportEntity.class, 
				id
				);
				workReportService.delete(workReport);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "工作简报信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加工作简报信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WorkReportEntity workReport, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "工作简报信息表添加成功";
		try{
			workReportService.save(workReport);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "工作简报信息表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(workReport);
		return j;
	}
	
	/**
	 * 更新工作简报信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WorkReportEntity workReport, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "工作简报信息表更新成功";
		WorkReportEntity t = workReportService.get(WorkReportEntity.class, workReport.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(workReport, t);
			workReportService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "工作简报信息表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 工作简报信息表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WorkReportEntity workReport, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(workReport.getId())) {
			workReport = workReportService.getEntity(WorkReportEntity.class, workReport.getId());
			req.setAttribute("workReportPage", workReport);
		}
		return new ModelAndView("modules/hzz/work/workreport/workReport-add");
	}
	/**
	 * 工作简报信息表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WorkReportEntity workReport, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(workReport.getId())) {
			workReport = workReportService.getEntity(WorkReportEntity.class, workReport.getId());
			req.setAttribute("workReportPage", workReport);
		}
		return new ModelAndView("modules/hzz/work/workreport/workReport-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","workReportController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WorkReportEntity workReport,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WorkReportEntity.class, dataGrid);
		com.saili.hzz.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, workReport, request.getParameterMap());
		List<WorkReportEntity> workReports = this.workReportService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"工作简报信息表");
		modelMap.put(NormalExcelConstants.CLASS,WorkReportEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("工作简报信息表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,workReports);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WorkReportEntity workReport,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"工作简报信息表");
    	modelMap.put(NormalExcelConstants.CLASS,WorkReportEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("工作简报信息表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<WorkReportEntity> listWorkReportEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WorkReportEntity.class,params);
				for (WorkReportEntity workReport : listWorkReportEntitys) {
					workReportService.save(workReport);
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
	 * 获取文件附件信息
	 * 
	 * @param id workReport主键id
	 */
	@RequestMapping(params = "getFiles")
	@ResponseBody
	public AjaxJson getFiles(String id){
		List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", id);
		List<Map<String,Object>> files = new ArrayList<Map<String,Object>>(0);
		for(CgUploadEntity b:uploadBeans){
			String title = b.getAttachmenttitle();//附件名
			String fileKey = b.getId();//附件主键
			String path = b.getRealpath();//附件路径
			String field = b.getCgformField();//表单中作为附件控件的字段
			Map<String, Object> file = new HashMap<String, Object>();
			file.put("title", title);
			file.put("fileKey", fileKey);
			file.put("path", path);
			file.put("field", field==null?"":field);
			files.add(file);
		}
		AjaxJson j = new AjaxJson();
		j.setObj(files);
		return j;
	}
	
}
