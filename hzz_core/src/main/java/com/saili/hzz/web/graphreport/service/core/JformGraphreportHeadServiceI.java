package com.saili.hzz.web.graphreport.service.core;

import java.util.List;

import com.saili.hzz.web.graphreport.entity.core.JformGraphreportHeadEntity;
import com.saili.hzz.web.graphreport.entity.core.JformGraphreportItemEntity;
import com.saili.hzz.core.common.service.CommonService;

public interface JformGraphreportHeadServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(JformGraphreportHeadEntity jformGraphreportHead,
                        List<JformGraphreportItemEntity> jformGraphreportItemList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(JformGraphreportHeadEntity jformGraphreportHead,
						   List<JformGraphreportItemEntity> jformGraphreportItemList);
	public void delMain(JformGraphreportHeadEntity jformGraphreportHead);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(JformGraphreportHeadEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(JformGraphreportHeadEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(JformGraphreportHeadEntity t);
}
