package com.saili.hzz.backend.service.impl.work;

import com.saili.hzz.core.entity.TSLNoticeEntity;
import com.saili.hzz.backend.service.work.TSLNoticeServiceI;
import com.saili.hzz.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service("tSLNoticeService")
@Transactional
public class TSLNoticeServiceImpl extends CommonServiceImpl implements TSLNoticeServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
 	public void delete(TSLNoticeEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public Serializable save(TSLNoticeEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TSLNoticeEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
}