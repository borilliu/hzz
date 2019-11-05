package com.saili.hzz.demo.service;
import com.saili.hzz.demo.entity.JfromOrderEntity;
import com.saili.hzz.demo.entity.JfromOrderLineEntity;

import java.util.List;
import com.saili.hzz.core.common.service.CommonService;

public interface JfromOrderServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(JfromOrderEntity jfromOrder,
	        List<JfromOrderLineEntity> jfromOrderLineList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(JfromOrderEntity jfromOrder, List<JfromOrderLineEntity> jfromOrderLineList);
	
	public void delMain (JfromOrderEntity jfromOrder);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(JfromOrderEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(JfromOrderEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(JfromOrderEntity t);
}
