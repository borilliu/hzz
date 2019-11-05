package com.saili.hzz.backend.service.impl.work;

import com.saili.hzz.core.entity.WorkReportEntity;
import com.saili.hzz.backend.service.work.WorkReportServiceI;
import com.saili.hzz.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service("workReportService")
@Transactional
public class WorkReportServiceImpl extends CommonServiceImpl implements WorkReportServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
 	public void delete(WorkReportEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public Serializable save(WorkReportEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(WorkReportEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
}