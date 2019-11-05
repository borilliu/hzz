package com.saili.hzz.backend.service.work;
import com.saili.hzz.core.entity.ContentEntity;
import com.saili.hzz.core.common.service.CommonService;

import java.io.Serializable;

public interface ContentServiceI extends CommonService{
	
 	public void delete(ContentEntity entity) throws Exception;
 	
 	public Serializable save(ContentEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ContentEntity entity) throws Exception;
 	
}
