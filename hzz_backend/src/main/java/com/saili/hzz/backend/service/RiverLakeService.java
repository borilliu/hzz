package com.saili.hzz.backend.service;

import com.saili.hzz.core.entity.TSLBaseRiverLake;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.common.model.json.ValidForm;

import java.util.List;

/**
 * 河湖名录 service
 * @author whuab_mc
 */
public interface RiverLakeService {

    /**
     * 验证河湖名录编码是否已经存在
     * @param code 河湖编码
     * @return
     */
    ValidForm checkRiverLakeCode(String type, String code);

    /**
     * 根据 行政地区 查询河湖信息
     * @param division
     * @return
     */
    List<TSLBaseRiverLake> listByDivision(TSLDivisionEntity division);

    /**
     * 查询包含指定父级行政区划的河湖集
     * @param division
     * @return
     */
    List<TSLBaseRiverLake> listByContainsParentDivision(TSLDivisionEntity division);

    /**
     * 根据标识点坐标获取河湖信息
     * @param publicSignsLng
     * @param publicSignsLat
     * @return
     */
    TSLBaseRiverLake getByMarkerPoint(Double publicSignsLng, Double publicSignsLat);
}
