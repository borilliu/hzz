package com.saili.hzz.web.system.service;

import com.saili.hzz.web.system.pojo.base.TSCategoryEntity;
import com.saili.hzz.core.common.service.CommonService;

public interface CategoryServiceI extends CommonService{
	/**
	 * 保存分类管理
	 * @param category
	 */
	void saveCategory(TSCategoryEntity category);

}
