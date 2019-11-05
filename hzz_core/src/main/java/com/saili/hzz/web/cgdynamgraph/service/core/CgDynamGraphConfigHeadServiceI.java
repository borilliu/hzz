package com.saili.hzz.web.cgdynamgraph.service.core;

import java.util.List;

import com.saili.hzz.web.cgdynamgraph.entity.core.CgDynamGraphConfigHeadEntity;
import com.saili.hzz.web.cgdynamgraph.entity.core.CgDynamGraphConfigItemEntity;
import com.saili.hzz.web.cgdynamgraph.entity.core.CgDynamGraphConfigParamEntity;
import com.saili.hzz.core.common.service.CommonService;

public interface CgDynamGraphConfigHeadServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(CgDynamGraphConfigHeadEntity cgDynamGraphConfigHead,
                        List<CgDynamGraphConfigItemEntity> cgDynamGraphConfigItemList, List<CgDynamGraphConfigParamEntity> cgDynamGraphConfigParamList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(CgDynamGraphConfigHeadEntity cgDynamGraphConfigHead,
	        List<CgDynamGraphConfigItemEntity> cgDynamGraphConfigItemList,List<CgDynamGraphConfigParamEntity> cgDynamGraphConfigParamList);
	public void delMain (CgDynamGraphConfigHeadEntity cgDynamGraphConfigHead);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(CgDynamGraphConfigHeadEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(CgDynamGraphConfigHeadEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(CgDynamGraphConfigHeadEntity t);
}
