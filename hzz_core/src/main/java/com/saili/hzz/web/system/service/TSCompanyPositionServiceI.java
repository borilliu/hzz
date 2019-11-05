package com.saili.hzz.web.system.service;
import java.io.Serializable;

import com.saili.hzz.web.system.pojo.base.TSCompanyPositionEntity;
import com.saili.hzz.core.common.service.CommonService;

public interface TSCompanyPositionServiceI extends CommonService{
	
 	public void delete(TSCompanyPositionEntity entity) throws Exception;
 	
 	public Serializable save(TSCompanyPositionEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TSCompanyPositionEntity entity) throws Exception;
 	
}
