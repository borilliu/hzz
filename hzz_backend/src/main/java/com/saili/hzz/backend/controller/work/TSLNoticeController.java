package com.saili.hzz.backend.controller.work;

import com.saili.hzz.core.entity.TSLNoticeEntity;
import com.saili.hzz.backend.service.work.TSLNoticeServiceI;
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
import java.util.List;
import java.util.Map;


/**   
 * @Title: Controller  
 * @Description: 公告管理信息表
 * @author onlineGenerator
 * @date 2019-10-30 14:56:05
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tSlNoticeController")
public class TSLNoticeController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(TSLNoticeController.class);

	@Autowired
	private TSLNoticeServiceI tSLNoticeService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 公告管理信息表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("modules/hzz/work/notice/tSlNoticeList");
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
	public void datagrid(TSLNoticeEntity tSlNotice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSLNoticeEntity.class, dataGrid);
		//查询条件组装器
		com.saili.hzz.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSlNotice, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSLNoticeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除公告管理信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSLNoticeEntity tSlNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tSlNotice = systemService.getEntity(TSLNoticeEntity.class, tSlNotice.getId());
		message = "公告管理信息表删除成功";
		try{
			tSLNoticeService.delete(tSlNotice);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "公告管理信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除公告管理信息表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "公告管理信息表删除成功";
		try{
			for(String id:ids.split(",")){
				TSLNoticeEntity tSlNotice = systemService.getEntity(TSLNoticeEntity.class,
				id
				);
				tSLNoticeService.delete(tSlNotice);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "公告管理信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加公告管理信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSLNoticeEntity tSlNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "公告管理信息表添加成功";
		try{
			tSLNoticeService.save(tSlNotice);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "公告管理信息表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新公告管理信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSLNoticeEntity tSlNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "公告管理信息表更新成功";
		TSLNoticeEntity t = tSLNoticeService.get(TSLNoticeEntity.class, tSlNotice.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tSlNotice, t);
			tSLNoticeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "公告管理信息表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 公告管理信息表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSLNoticeEntity tSlNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSlNotice.getId())) {
			tSlNotice = tSLNoticeService.getEntity(TSLNoticeEntity.class, tSlNotice.getId());
			req.setAttribute("tSlNoticePage", tSlNotice);
		}
		return new ModelAndView("modules/hzz/work/notice/tSlNotice-add");
	}
	/**
	 * 公告管理信息表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSLNoticeEntity tSlNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSlNotice.getId())) {
			tSlNotice = tSLNoticeService.getEntity(TSLNoticeEntity.class, tSlNotice.getId());
			req.setAttribute("tSlNoticePage", tSlNotice);
		}
		return new ModelAndView("modules/hzz/work/notice/tSlNotice-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tSlNoticeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TSLNoticeEntity tSlNotice,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TSLNoticeEntity.class, dataGrid);
		com.saili.hzz.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSlNotice, request.getParameterMap());
		List<TSLNoticeEntity> tSlNotices = this.tSLNoticeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"公告管理信息表");
		modelMap.put(NormalExcelConstants.CLASS,TSLNoticeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("公告管理信息表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tSlNotices);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TSLNoticeEntity tSlNotice,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"公告管理信息表");
    	modelMap.put(NormalExcelConstants.CLASS,TSLNoticeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("公告管理信息表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<TSLNoticeEntity> listTSLNoticeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TSLNoticeEntity.class,params);
				for (TSLNoticeEntity tSlNotice : listTSLNoticeEntitys) {
					tSLNoticeService.save(tSlNotice);
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
	
	
}
