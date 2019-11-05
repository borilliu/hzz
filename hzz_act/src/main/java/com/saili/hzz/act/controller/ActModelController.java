/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.saili.hzz.act.controller;

import com.saili.hzz.act.service.ActModelService;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.tag.core.easyui.TagUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 流程模型相关Controller
 * @author whuab_mc
 * @version 2019-07-18
 */
@Controller
@RequestMapping(value = "/act/model")
public class ActModelController {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ActModelService actModelService;

	@RequestMapping(value = "redirectModeler")
	public void redirectModeler(String modelId, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + "/act/process-editor/modeler.jsp?modelId=" + modelId);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("跳转到流程编辑工具页失败！：", e);
		}
	}
	/**
	 * 流程模型列表
	 */
	@RequestMapping(params = "list")
	public String modelList() {
		return "modules/act/actModelList";
	}

	@RequestMapping(params = "dataGrid")
	public void patrolDataGrid(HttpServletResponse response, String category, DataGrid dataGrid) {

		actModelService.modelList(dataGrid, category);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 创建模型
	 */
	@RequestMapping(params = "create", method = RequestMethod.GET)
	public String create(Model model) {
		return "modules/act/actModelCreate";
	}

	@RequestMapping(value = "test")
	public String test(HttpServletRequest request, HttpServletResponse response, String modelId) {
		request.setAttribute("modelId", modelId);
		return "act/process-editor/modeler";
	}
	/**
	 * 创建模型
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(String name, String key, String description, String category, HttpServletRequest request, HttpServletResponse response) {
		try {
			org.activiti.engine.repository.Model modelData = actModelService.create(name, key, description, category);
			response.sendRedirect(request.getContextPath() + "/act/process-editor/modeler.jsp?modelId=" + modelData.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建模型失败：", e);
		}
	}

    /**
     * 创建模型
     */
    @RequestMapping(params = "createformvalidate", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson createformvalidate(String name, String key, String description, String category, HttpServletRequest request, HttpServletResponse response) {
		String message = null;
		AjaxJson result = new AjaxJson();
		message = "创建模型成功";
        try {
			/*org.activiti.engine.repository.Model modelData = actModelService.create(name, key, description, category);
			response.sendRedirect(request.getContextPath() + "/act/process-editor/modeler.jsp?modelId=" + modelData.getId());*/
            response.sendRedirect(request.getContextPath() + "/act/process-editor/modeler.jsp?modelId=12342");
        } catch (Exception e) {
            e.printStackTrace();
			message = "创建模型失败";
            logger.error("创建模型失败：", e);
        }
        result.setMsg(message);
		return result;
    }

	/**
	 * 根据Model部署流程
	 */
	@RequestMapping(params = "deploy")
	@ResponseBody
	public String deploy(String id, RedirectAttributes redirectAttributes) {
		String message = "送审成功";
		AjaxJson j = new AjaxJson();
		try {
			j.setSuccess(true);
			message = actModelService.deploy(id);
			redirectAttributes.addFlashAttribute("message", message);
		} catch (Exception e) {
			logger.info(e.getMessage());
			message = "送审失败";
			j.setSuccess(false);
		}
		j.setMsg(message);
//		return "redirect:/act/process";
		return j.getJsonStr();
	}
	
	/**
	 * 导出model的xml文件
	 */
	@RequestMapping(value = "export")
	public void export(String id, HttpServletResponse response) {
		actModelService.export(id, response);
	}

	/**
	 * 更新Model分类
	 */
	@RequestMapping(params = "updateCategory")
	public String updateCategory(String id, String category, RedirectAttributes redirectAttributes) {
		actModelService.updateCategory(id, category);
		redirectAttributes.addFlashAttribute("message", "设置成功，模块ID=" + id);
		return "redirect:/act/model";
	}
	
	/**
	 * 删除Model
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(params = "delete")
	@ResponseBody
	public String delete(String id, RedirectAttributes redirectAttributes) {
		String message = "送审成功";
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		actModelService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除成功，模型ID=" + id);
		j.setMsg("删除成功，模型ID=" + id);
		return j.getJsonStr();
	}
}
