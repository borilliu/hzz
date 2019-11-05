package com.saili.hzz.tag.core.factory;

import java.util.Map;

import com.saili.hzz.tag.core.easyui.DataGridTag;
import com.saili.hzz.core.online.util.FreemarkerHelper;

/**
 * 
 * @author zhoujf
 * @version V1.0
 */
public abstract class ComponentFactory {


	
	public String invoke(String templatePath, DataGridTag dataGridTag) throws Exception {
		FreemarkerHelper free = new FreemarkerHelper();
		Map<String, Object> data = getData(dataGridTag);
		String content = free.parseTemplate(templatePath, data);
		return content;
	}
	

	protected abstract Map<String, Object> getData(DataGridTag dataGridTag);
	
}
