package com.saili.hzz.demo.service;
import com.saili.hzz.core.common.service.CommonService;

import com.saili.hzz.demo.entity.MultiUploadEntity;

import java.io.Serializable;

public interface MultiUploadServiceI extends CommonService{
	
 	public void delete(MultiUploadEntity entity) throws Exception;
 	
 	public Serializable save(MultiUploadEntity entity) throws Exception;
 	
 	public void saveOrUpdate(MultiUploadEntity entity) throws Exception;
 	
}
