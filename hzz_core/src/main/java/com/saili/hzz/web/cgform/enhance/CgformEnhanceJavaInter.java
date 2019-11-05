package com.saili.hzz.web.cgform.enhance;

import java.util.Map;

import com.saili.hzz.core.common.exception.BusinessException;

/**
 * JAVA增强
 * @author luobaoli
 *
 */
public interface CgformEnhanceJavaInter {
	/**
	 * @param tableName 数据库表名
	 * @param map 表单数据
	 */

	public void execute(String tableName,Map map) throws BusinessException;

}
