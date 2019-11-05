package com.saili.hzz.web.system.service;
import java.io.Serializable;

import com.saili.hzz.web.system.pojo.base.TSDictTableConfigEntity;
import com.saili.hzz.core.common.service.CommonService;

/**
 * 字典表授权配置
 * @author zhoujf
 *
 */
public interface TSDictTableConfigServiceI extends CommonService{
	
 	public void delete(TSDictTableConfigEntity entity) throws Exception;
 	
 	public Serializable save(TSDictTableConfigEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TSDictTableConfigEntity entity) throws Exception;
 	
 	/**
 	 * 校验自定义字典是否授权
 	 * @param dictionary
 	 * @param dictCondition
 	 * @return
 	 */
 	public boolean checkDictAuth(String dictionary,String dictCondition);
 	
 	/**
 	 * 自定义字典转换value为text文本值
 	 * @param dictionary
 	 * @param dictCondition
 	 * @param value  编码值
 	 * @return
 	 */
 	public Object getDictText(String dictionary,String dictCondition,String value);
 	
}
