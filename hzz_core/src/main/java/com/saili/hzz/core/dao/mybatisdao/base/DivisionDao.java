package com.saili.hzz.core.dao.mybatisdao.base;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-08-27 16:06:16:06
 * @version: V1.0
 */
@MyBatisDao
public interface DivisionDao {
    List<TSLDivisionEntity> listTreeById(@Param("id") String id);

    List<TSLDivisionEntity> listAll();

    /**
     * 根据 父级 编码查询
     * @return
     */
    List<TSLDivisionEntity> listByParentCode(String parentCode);

    /**
     * 包含指定的地区编码的地区集
     * @param code
     * @return
     */
    List<TSLDivisionEntity> listContainParentCode(String code);

    /**
     * 根据编号获取行政区划信息
     * @param code
     * @return
     */
    TSLDivisionEntity getByCode(@Param("code") String code);
}
