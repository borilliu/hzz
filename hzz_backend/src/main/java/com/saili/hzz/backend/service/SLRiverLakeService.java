package com.saili.hzz.backend.service;

import com.saili.hzz.core.entity.TSLBaseRiverLake;

import java.util.List;

public interface SLRiverLakeService {

    List<TSLBaseRiverLake> testMinidao();

    List<TSLBaseRiverLake> testSql();

    /**
     * 根据河流编码查询
     * @param code
     * @return
     */
    TSLBaseRiverLake getByCode(String code) throws Exception;
}
