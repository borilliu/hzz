package com.saili.hzz.web.system.dao;

import org.jeecgframework.minidao.annotation.Param;
import com.saili.hzz.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    /**
     * 根据地区编码查询用户信息
     * @param divisionCode
     * @return
     */
    List<TSUser> selectByDivisionCode(@Param("divisionCode") String divisionCode);
}
