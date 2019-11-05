package com.saili.hzz.backend.service;

import com.saili.hzz.core.entity.TSLBaseRiverLake;

/**
 * 河湖业务 基类
 * @author whuab_mc
 */
public interface BaseRiverLakeService<T extends TSLBaseRiverLake> {

    /**
     * 根据河湖编号查询河湖数量
     * @param code
     * @return
     */
    int countByCode(String code);

    /**
     * 根据id查询河湖信息
     * @param id
     * @return
     */
    T get(String id);
}
