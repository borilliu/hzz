package com.saili.hzz.demo.service;
import com.saili.hzz.demo.entity.JeecgDemoEntity;
import com.saili.hzz.core.common.service.CommonService;

import java.io.Serializable;

public interface JeecgDemoServiceI extends CommonService{
	
 	public void delete(JeecgDemoEntity entity) throws Exception;
 	
 	public Serializable save(JeecgDemoEntity entity) throws Exception;
 	
 	public void saveOrUpdate(JeecgDemoEntity entity) throws Exception;
 	
 	public void jdbcBatchSave() throws Exception;
 	
	public void jdbcProcedure() throws Exception;

}
