package com.saili.hzz.web.cgreport.service.core;

import java.util.List;

import com.saili.hzz.web.cgreport.entity.core.CgreportConfigHeadEntity;
import com.saili.hzz.web.cgreport.entity.core.CgreportConfigItemEntity;
import com.saili.hzz.web.cgreport.entity.core.CgreportConfigParamEntity;
import com.saili.hzz.core.common.service.CommonService;

public interface CgreportConfigHeadServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(CgreportConfigHeadEntity cgreportConfigHead,
                        List<CgreportConfigItemEntity> cgreportConfigItemList, List<CgreportConfigParamEntity> cgreportConfigParamList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(CgreportConfigHeadEntity cgreportConfigHead,
	        List<CgreportConfigItemEntity> cgreportConfigItemList,List<CgreportConfigParamEntity> cgreportConfigParamList);
	public void delMain (CgreportConfigHeadEntity cgreportConfigHead);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(CgreportConfigHeadEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(CgreportConfigHeadEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(CgreportConfigHeadEntity t);
}
