package com.saili.hzz.web.cgform.service.enhance;
import java.io.Serializable;
import java.util.List;

import com.saili.hzz.web.cgform.entity.enhance.CgformEnhanceJavaEntity;
import com.saili.hzz.core.common.service.CommonService;

public interface CgformEnhanceJavaServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(CgformEnhanceJavaEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(CgformEnhanceJavaEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(CgformEnhanceJavaEntity t);

 	/**
 	 * 
 	 * @param buttonCode
 	 * @param formId
 	 * @return
 	 */
	public CgformEnhanceJavaEntity getCgformEnhanceJavaEntityByCodeFormId(
			String buttonCode, String formId);

	/**
 	 * 
 	 * @param buttonCode
 	 * @param formId
 	 * @return
 	 */
	public CgformEnhanceJavaEntity getCgformEnhanceJavaEntityByCodeFormId(
			String buttonCode, String formId, String event);

	/**
	 * 判断按纽编码是否存在
	 * @param cgformEnhanceJavaEntity
	 * @return
	 */
	public List<CgformEnhanceJavaEntity> checkCgformEnhanceJavaEntity(
			CgformEnhanceJavaEntity cgformEnhanceJavaEntity);
	
	/**
	 * 判断Class是否可以实例化，spring-bean是否可以实例化
	 * @param cgformEnhanceJavaEntity
	 * @return
	 */
	public boolean checkClassOrSpringBeanIsExist(CgformEnhanceJavaEntity cgformEnhanceJavaEntity);
}
