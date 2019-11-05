package com.saili.hzz.backend.service.impl.work;

import com.saili.hzz.core.entity.ContentEntity;
import com.saili.hzz.backend.service.work.ContentServiceI;
import com.saili.hzz.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service("contentService")
@Transactional
public class ContentServiceImpl extends CommonServiceImpl implements ContentServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
 	public void delete(ContentEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public Serializable save(ContentEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(ContentEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
}