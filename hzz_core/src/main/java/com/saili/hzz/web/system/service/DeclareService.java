package com.saili.hzz.web.system.service;

import java.util.List;

import com.saili.hzz.web.system.pojo.base.TSAttachment;

import com.saili.hzz.core.common.service.CommonService;

/**
 * 
 * @author  liuby
 *
 */
public interface DeclareService extends CommonService{
	
	public List<TSAttachment> getAttachmentsByCode(String businessKey, String description);
	
}
