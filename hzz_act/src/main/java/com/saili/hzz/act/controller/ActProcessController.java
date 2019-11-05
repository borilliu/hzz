/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.saili.hzz.act.controller;

import com.saili.hzz.act.service.ActProcessService;
import org.apache.commons.lang3.StringUtils;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 流程定义相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "/act/process")
public class ActProcessController {

	@Autowired
	private ActProcessService actProcessService;

	/**
	 * 流程定义列表
	 */
	@RequestMapping(value = {"list", ""})
	public String processList(String category, Model model, DataGrid dataGrid) {
		return "modules/act/actProcessList";
	}

    /**
     * 流程定义列表
     */
    @RequestMapping(params = "dataGrid")
    public void dataGrid(String category, HttpServletResponse response, DataGrid dataGrid) {
        /*
         * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
         */
        actProcessService.processList(dataGrid, category);
        TagUtil.datagrid(response, dataGrid);
    }
	
	/**
	 * 运行中的实例列表
	 */
	@RequestMapping(value = "running")
	public String runningList(String procInsId, String procDefKey, Model model, DataGrid dataGrid) {
		return "modules/act/actProcessRunningList";
	}

	/**
	 * 运行中的实例列表
	 */
	@RequestMapping(value = "running", params = "dataGrid")
	public void runningDataGridList(String procInsId, String procDefKey, HttpServletResponse response, Model model, DataGrid dataGrid) {
		dataGrid = actProcessService.runningList(dataGrid, procInsId, procDefKey);
//		model.addAttribute("dataGrid", dataGrid);
		model.addAttribute("procInsId", procInsId);
		model.addAttribute("procDefKey", procDefKey);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 创建模型
	 */
	@RequestMapping(params = "deleteReson", method = RequestMethod.GET)
	public String create(Model model) {
		return "modules/act/deleteReason";
	}

	/**
	 * 读取资源，通过部署ID
	 * @param procDefId  流程定义ID
	 * @param proInsId 流程实例ID
	 * @param resType 资源类型(xml|image)
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "resource/read")
	public void resourceRead(String procDefId, String proInsId, String resType, HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = actProcessService.resourceRead(procDefId, proInsId, resType);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 部署流程
	 */
	@RequestMapping(value = "/deploy", method= RequestMethod.GET)
	public String deploy(Model model) {
		return "modules/act/actProcessDeploy";
	}
	
	/**
	 * 部署流程 - 保存
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/deploy", method= RequestMethod.POST)
	public String deploy(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir,
                         String category, MultipartFile file, RedirectAttributes redirectAttributes) {

		String fileName = file.getOriginalFilename();
		
		if (StringUtils.isBlank(fileName)){
			redirectAttributes.addFlashAttribute("message", "请选择要部署的流程文件");
		}else{
			String message = actProcessService.deploy(exportDir, category, file);
			redirectAttributes.addFlashAttribute("message", message);
		}

		return "redirect:/act/process";
	}
	
	/**
	 * 设置流程分类
	 */
	@RequestMapping(value = "updateCategory")
	public String updateCategory(String procDefId, String category, RedirectAttributes redirectAttributes) {
		actProcessService.updateCategory(procDefId, category);
		return "redirect:/act/process";
	}

	/**
	 * 挂起、激活流程实例
	 */
	@RequestMapping(value = "update/{state}")
	@ResponseBody
	public String updateState(@PathVariable("state") String state, String procDefId, RedirectAttributes redirectAttributes) {
		AjaxJson result = new AjaxJson();
		result.setSuccess(true);
		String message = actProcessService.updateState(state, procDefId);
		result.setMsg(message);
		return result.getJsonStr();
	}
	
	/**
	 * 将部署的流程转换为模型
	 * @param procDefId
	 * @param redirectAttributes
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
	@RequestMapping(value = "convert/toModel")
	@ResponseBody
	public String convertToModel(String procDefId, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, XMLStreamException {
		AjaxJson result = new AjaxJson();
		result.setSuccess(true);
		try {
			org.activiti.engine.repository.Model modelData = actProcessService.convertToModel(procDefId);
			result.setMsg("转换模型成功，模型ID="+modelData.getId());
		} catch (Exception e) {
			result.setMsg("转换模型失败! 异常信息：" + e.getMessage());
			e.printStackTrace();
		}
		return result.getJsonStr();
	}
	
	/**
	 * 导出图片文件到硬盘
	 */
	@RequestMapping(value = "export/diagrams")
	@ResponseBody
	public List<String> exportDiagrams(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir) throws IOException {
		List<String> files = actProcessService.exportDiagrams(exportDir);
		return files;
	}

	/**
	 * 删除部署的流程，级联删除流程实例
	 * @param deploymentId 流程部署ID
	 */
	@RequestMapping(value = "delete")
	public String delete(String deploymentId) {
		AjaxJson result = new AjaxJson();
		result.setSuccess(true);
		actProcessService.deleteDeployment(deploymentId);
		result.setMsg("删除成功！");
		return result.toString();
	}
	
	/**
	 * 删除流程实例
	 * @param procInsId 流程实例ID
	 * @param reason 删除原因
	 */
	@RequestMapping(value = "deleteProcIns")
	public String deleteProcIns(String procInsId, String reason, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(reason)){
//			addMessage(redirectAttributes, "请填写删除原因");
		}else{
			actProcessService.deleteProcIns(procInsId, reason);
//			addMessage(redirectAttributes, "删除流程实例成功，实例ID=" + procInsId);
		}
		return "redirect:/act/process/running/";
	}
	
}
