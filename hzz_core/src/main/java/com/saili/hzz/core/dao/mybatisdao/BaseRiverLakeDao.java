package com.saili.hzz.core.dao.mybatisdao;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.entity.TSLBaseRiverLake;
import org.apache.ibatis.annotations.Param;

/**
 * 河湖业务 基类
 * @author whuab_mc
 */
@MyBatisDao
public interface BaseRiverLakeDao<T extends TSLBaseRiverLake> {

    /**
     * 根据河湖编号查询河湖数量
     * @param code
     * @return
     */
    int countByCode(@Param("code") String code);

    /**
     * 根据id获取河湖信息
     * @param id
     * @return
     */
    T get(@Param("id") String id);
}
