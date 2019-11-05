package com.saili.hzz.backend.service.work;
import com.saili.hzz.core.entity.ProblemEntity;
import com.saili.hzz.core.common.service.CommonService;

import java.io.Serializable;

public interface ProblemServiceI extends CommonService{
	
 	public void delete(ProblemEntity entity) throws Exception;
 	
 	public Serializable save(ProblemEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ProblemEntity entity) throws Exception;
 	
}
