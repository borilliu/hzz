/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.saili.hzz.core.dao.mybatisdao;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.entity.TestUser;
import org.jeecgframework.minidao.annotation.Param;


/**
 * 单表生成DAO接口
 * @author ThinkGem
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestDataDao extends CrudDao<TestUser> {
    public TestUser get(@Param("id") String id);
}