package com.saili.hzz.web.cgform.controller.enhance;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saili.hzz.core.constant.Globals;
import com.saili.hzz.core.extend.hqlsearch.HqlGenerateUtil;
import com.saili.hzz.tag.core.easyui.TagUtil;
import com.saili.hzz.web.cgform.entity.enhance.CgformEnhanceJsEntity;
import com.saili.hzz.web.cgform.service.enhance.CgformEnhanceJsServiceI;
import com.saili.hzz.web.system.service.SystemService;

import org.apache.log4j.Logger;
import com.saili.hzz.core.common.controller.BaseController;
import com.saili.hzz.core.common.hibernate.qbc.CriteriaQuery;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.core.util.MyBeanUtils;
import com.saili.hzz.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: Controller
 * @Description: JS增强
 * @author liuby
 * @date 2013-08-11 09:47:30
 * @version V1.0   
 *
 */
//@Scope("prototype")
@Controller
@RequestMapping("/cgformEnhanceJsController")
public class CgformEnhanceJsController extends BaseController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CgformEnhanceJsController.class);

	@Autowired
	private CgformEnhanceJsServiceI cgformenhanceJsService;
	@Autowired
	private SystemService systemService;


	/**
	 * JS增强列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "cgformenhanceJs")
	public ModelAndView cgformenhanceJs(HttpServletRequest request) {
		return new ModelAndView("jeecg/cgform/enhance/cgformenhanceJsList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(CgformEnhanceJsEntity cgformenhanceJs, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CgformEnhanceJsEntity.class, dataGrid);
		//查询条件组装器
		HqlGenerateUtil.installHql(cq, cgformenhanceJs, request.getParameterMap());
		this.cgformenhanceJsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除JS增强
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CgformEnhanceJsEntity cgformenhanceJs, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		cgformenhanceJs = systemService.getEntity(CgformEnhanceJsEntity.class, cgformenhanceJs.getId());
		message = "删除成功";
		cgformenhanceJsService.delete(cgformenhanceJs);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}

	/**
	 * 查找数据
	 * 
	 * @return
	 */
	@RequestMapping(params = "doCgformEnhanceJs")
	@ResponseBody  
	public AjaxJson doCgformEnhanceJs(CgformEnhanceJsEntity cgformenhanceJs, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		CgformEnhanceJsEntity cgformenJs = cgformenhanceJsService.getCgformEnhanceJsByTypeFormId(cgformenhanceJs.getCgJsType(), cgformenhanceJs.getFormId());
		if(cgformenJs!=null){
			j.setObj(cgformenJs);
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
		}
		return j;
	}

	/**
	 * 添加JS增强
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CgformEnhanceJsEntity cgformenhanceJs, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(cgformenhanceJs.getId())) {
			message = "更新成功";
			CgformEnhanceJsEntity t = cgformenhanceJsService.get(CgformEnhanceJsEntity.class, cgformenhanceJs.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(cgformenhanceJs, t);
				cgformenhanceJsService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			cgformenhanceJsService.save(cgformenhanceJs);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * JS增强列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CgformEnhanceJsEntity cgformenhanceJs, HttpServletRequest req) {
		//根据cgJsType和formId初始化数据
		cgformenhanceJs.setCgJsType("form");
		if (StringUtil.isNotEmpty(cgformenhanceJs.getCgJsType())&&StringUtil.isNotEmpty(cgformenhanceJs.getFormId())) {
			CgformEnhanceJsEntity cgformenJs = cgformenhanceJsService.getCgformEnhanceJsByTypeFormId(cgformenhanceJs.getCgJsType(), cgformenhanceJs.getFormId());
			if(cgformenJs!=null){
				cgformenhanceJs = cgformenJs;
			}
		}
		req.setAttribute("cgformenhanceJsPage", cgformenhanceJs);
		return new ModelAndView("jeecg/cgform/enhance/cgformenhanceJs");
	}
}
