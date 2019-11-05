package com.saili.hzz.web.system.service;

import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.service.CommonService;
/**
 * 
 * @author  liuby
 *
 */
public interface FunctionService extends CommonService{

	/**
	 * 删除菜单
	 * @param function
	 * @return 
	 */
	public AjaxJson delFunction(String functionId);
}
