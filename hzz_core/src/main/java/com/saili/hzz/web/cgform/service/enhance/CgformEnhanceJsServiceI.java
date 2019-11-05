package com.saili.hzz.web.cgform.service.enhance;

import com.saili.hzz.web.cgform.entity.enhance.CgformEnhanceJsEntity;

import com.saili.hzz.core.common.service.CommonService;

public interface CgformEnhanceJsServiceI extends CommonService{

	/**
	 * 根据cgJsType和formId查找数据
	 * @param cgJsType
	 * @param formId
	 * @return
	 */
	public CgformEnhanceJsEntity getCgformEnhanceJsByTypeFormId(String cgJsType, String formId);
}
