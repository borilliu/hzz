package com.saili.hzz.backend.service.work;
import com.saili.hzz.core.entity.WorkReportEntity;
import com.saili.hzz.core.common.service.CommonService;

import java.io.Serializable;

public interface WorkReportServiceI extends CommonService{
	
 	public void delete(WorkReportEntity entity) throws Exception;
 	
 	public Serializable save(WorkReportEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WorkReportEntity entity) throws Exception;
 	
}
