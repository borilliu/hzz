package com.saili.hzz.demo.service.impl;
import java.io.Serializable;

import com.saili.hzz.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saili.hzz.demo.entity.JeecgDemoExcelEntity;
import com.saili.hzz.demo.service.JeecgDemoExcelServiceI;

@Service("jeecgDemoExcelService")
@Transactional
public class JeecgDemoExcelServiceImpl extends CommonServiceImpl implements JeecgDemoExcelServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
 	public void delete(JeecgDemoExcelEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public Serializable save(JeecgDemoExcelEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(JeecgDemoExcelEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
}