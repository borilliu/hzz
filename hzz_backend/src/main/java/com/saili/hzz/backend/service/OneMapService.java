package com.saili.hzz.backend.service;

import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.vo.onemap.DrawRiverLakeDataVo;
import com.saili.hzz.core.vo.onemap.RiverInfoVo;

import java.util.List;

/**
 * @author: whuab_mc
 * @date: 2019-10-18 08:55:8:55
 * @version: V1.0
 */
public interface OneMapService {
    /**
     * 获取河流信息
     * @param publicSignsLng
     * @param publicSignsLat
     * @return
     */
    RiverInfoVo getRiverInfo(Double publicSignsLng, Double publicSignsLat);

    /**
     * 一张图中绘制河流的信息
     * @param division
     * @return
     */
    List<DrawRiverLakeDataVo> listDrawRiverLakeData(TSLDivisionEntity division);
}
