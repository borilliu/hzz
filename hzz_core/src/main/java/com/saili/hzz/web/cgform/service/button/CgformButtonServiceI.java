package com.saili.hzz.web.cgform.service.button;

import java.util.List;

import com.saili.hzz.web.cgform.entity.button.CgformButtonEntity;

import com.saili.hzz.core.common.service.CommonService;

/**
 * 
 * @author  liuby
 *
 */
public interface CgformButtonServiceI extends CommonService{
	
	/**
	 * 查询按钮list
	 * @param formId
	 * @return
	 */
	public List<CgformButtonEntity> getCgformButtonListByFormId(String formId);

	/**
	 * 校验按钮唯一性
	 * @param cgformButtonEntity
	 * @return
	 */
	public List<CgformButtonEntity> checkCgformButton(CgformButtonEntity cgformButtonEntity);
	
	
}
