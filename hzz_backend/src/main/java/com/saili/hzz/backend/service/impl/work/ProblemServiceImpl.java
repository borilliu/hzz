package com.saili.hzz.backend.service.impl.work;
import com.saili.hzz.backend.service.work.ProblemServiceI;
import com.saili.hzz.core.common.service.impl.CommonServiceImpl;
import com.saili.hzz.core.entity.ProblemEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Service("problemService")
@Transactional(readOnly = true)
public class ProblemServiceImpl extends CommonServiceImpl implements ProblemServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Transactional(readOnly = false)
 	public void delete(ProblemEntity entity) throws Exception{
 		super.delete(entity);
 	}

	@Transactional(readOnly = false)
 	public Serializable save(ProblemEntity entity) throws Exception{
 		entity.beforeAdd();
 		Serializable t = super.save(entity);
 		return t;
 	}

	@Transactional(readOnly = false)
 	public void saveOrUpdate(ProblemEntity entity) throws Exception{
 		entity.beforeUpdate();
 		super.saveOrUpdate(entity);
 	}
 	
}