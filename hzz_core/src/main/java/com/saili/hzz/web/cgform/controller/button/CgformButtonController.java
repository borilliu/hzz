package com.saili.hzz.web.cgform.controller.button;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saili.hzz.core.constant.Globals;
import com.saili.hzz.core.extend.hqlsearch.HqlGenerateUtil;
import com.saili.hzz.tag.core.easyui.TagUtil;
import com.saili.hzz.web.cgform.entity.button.CgformButtonEntity;
import com.saili.hzz.web.cgform.service.button.CgformButtonServiceI;
import com.saili.hzz.web.system.service.SystemService;

import org.apache.log4j.Logger;
import com.saili.hzz.core.common.controller.BaseController;
import com.saili.hzz.core.common.hibernate.qbc.CriteriaQuery;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.core.util.IpUtil;
import com.saili.hzz.core.util.MyBeanUtils;
import com.saili.hzz.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: Controller
 * @Description: 表单自定义按钮
 * @author liuby
 * @date 2013-08-07 20:16:26
 * @version V1.0   
 *
 */
//@Scope("prototype")
@Controller
@RequestMapping("/cgformButtonController")
public class CgformButtonController extends BaseController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CgformButtonController.class);

	@Autowired
	private CgformButtonServiceI cgformButtonService;
	@Autowired
	private SystemService systemService;


	/**
	 * 表单自定义按钮列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "cgformButton")
	public ModelAndView cgformButton(HttpServletRequest request) {
		String formId = request.getParameter("formId");
		String tableName = request.getParameter("tableName");
		request.setAttribute("formId", formId);
		request.setAttribute("tableName", tableName);
		return new ModelAndView("jeecg/cgform/button/cgformButtonList");
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
	public void datagrid(CgformButtonEntity cgformButton, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CgformButtonEntity.class, dataGrid);
		//查询条件组装器
		HqlGenerateUtil.installHql(cq, cgformButton, request.getParameterMap());
		this.cgformButtonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除表单自定义按钮
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CgformButtonEntity cgformButton, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		cgformButton = systemService.getEntity(CgformButtonEntity.class, cgformButton.getId());
		message = "删除成功";
		cgformButtonService.delete(cgformButton);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		logger.info("["+IpUtil.getIpAddr(request)+"][online表单自定义按钮删除]"+message);
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加表单自定义按钮
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CgformButtonEntity cgformButton, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if("add".equalsIgnoreCase(cgformButton.getButtonCode())
				||"update".equalsIgnoreCase(cgformButton.getButtonCode())
				||"delete".equalsIgnoreCase(cgformButton.getButtonCode())){
			message = "按钮编码不能是add/update/delete";
			j.setMsg(message);
			return j;
		}
		List<CgformButtonEntity> list =  cgformButtonService.checkCgformButton(cgformButton);
		if(list!=null&&list.size()>0){
			message = "按钮编码已经存在";
			j.setMsg(message);
			return j;
		}
		if (StringUtil.isNotEmpty(cgformButton.getId())) {
			message = "更新成功";
			CgformButtonEntity t = cgformButtonService.get(CgformButtonEntity.class, cgformButton.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(cgformButton, t);
				cgformButtonService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			cgformButtonService.save(cgformButton);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		logger.info("["+IpUtil.getIpAddr(request)+"][online表单自定义按钮添加编辑]"+message);
		j.setMsg(message);
		return j;
	}

	/**
	 * 表单自定义按钮列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CgformButtonEntity cgformButton, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(cgformButton.getId())) {
			cgformButton = cgformButtonService.getEntity(CgformButtonEntity.class, cgformButton.getId());
		}
		req.setAttribute("cgformButtonPage", cgformButton);
		return new ModelAndView("jeecg/cgform/button/cgformButton");
	}
}
