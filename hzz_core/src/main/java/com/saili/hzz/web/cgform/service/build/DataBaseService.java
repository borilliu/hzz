package com.saili.hzz.web.cgform.service.build;

import java.util.List;
import java.util.Map;

import com.saili.hzz.web.cgform.entity.enhance.CgformEnhanceJavaEntity;
import com.saili.hzz.web.cgform.exception.BusinessException;

/**
 * 
 * @author  liuby
 *
 */
public interface DataBaseService {

	public void insertTable(String tableName, Map<String, Object> data) throws BusinessException ;

	public int updateTable(String tableName,Object id, Map<String, Object> data) throws BusinessException ;

	
	public Map<String, Object>  findOneForJdbc(String tableName, String id);
	
	public Map<String, Object> insertTableMore(Map<String,List<Map<String,Object>>> mapMore,String mainTableName) throws BusinessException;
	
	public boolean updateTableMore(Map<String,List<Map<String,Object>>> mapMore,String mainTableName) throws BusinessException;
	
	/**
	 * sql业务增强
	 * 
	 */
	public void executeSqlExtend(String formId,String buttonCode,Map<String, Object> data) throws BusinessException;

	public Object getPkValue(String tableName);

	/**
	 * java业务增强
	 * @param formId
	 * @param buttonCode
	 * @param data
	 */
	public void executeJavaExtend(String formId, String buttonCode,Map<String, Object> data) throws BusinessException;

	/**
	 * java业务增强
	 * @param formId
	 * @param buttonCode
	 * @param data
	 * @param event
	 */
	public void executeJavaExtend(String formId, String buttonCode,Map<String, Object> data, String event) throws BusinessException;

	public List<CgformEnhanceJavaEntity> getCgformEnhanceJavaEntityByFormId(String formId);

}
