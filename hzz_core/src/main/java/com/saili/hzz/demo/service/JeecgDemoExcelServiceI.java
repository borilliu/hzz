package com.saili.hzz.demo.service;
import java.io.Serializable;

import com.saili.hzz.core.common.service.CommonService;

import com.saili.hzz.demo.entity.JeecgDemoExcelEntity;

public interface JeecgDemoExcelServiceI extends CommonService{
	
 	public void delete(JeecgDemoExcelEntity entity) throws Exception;
 	
 	public Serializable save(JeecgDemoExcelEntity entity) throws Exception;
 	
 	public void saveOrUpdate(JeecgDemoExcelEntity entity) throws Exception;
 	
}
