package com.saili.hzz.core.dao.mybatisdao.sys;

import com.saili.hzz.core.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * @author whuab_mc
 */
@MyBatisDao("userMybatisDao")
public interface UserDao {

    /**
     * 指定的用户名是否有数据
     * @param userName
     * @return
     */
    int countByUserName(@Param("userName") String userName);
}
