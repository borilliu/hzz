package com.saili.hzz.web.cgform.service.config;
import java.io.Serializable;

import com.saili.hzz.web.cgform.entity.config.CgFormHeadEntity;
import com.saili.hzz.web.cgform.entity.config.CgFormIndexEntity;
import com.saili.hzz.core.common.service.CommonService;

public interface CgFormIndexServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(CgFormIndexEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(CgFormIndexEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(CgFormIndexEntity t);
 	
 	/**
 	 * 更新索引
 	 * @param cgFormHead
 	 * @return 
 	 * 
 	 */
	public boolean updateIndexes(CgFormHeadEntity cgFormHead);
	
	/**
	 * 创建索引
	 * @param cgFormHead
	 */
	public void createIndexes(CgFormHeadEntity cgFormHead);
}
