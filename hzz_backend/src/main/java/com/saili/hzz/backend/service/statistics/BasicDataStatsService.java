package com.saili.hzz.backend.service.statistics;

import com.saili.hzz.core.vo.BaseBasicDataStatsVo;
import com.saili.hzz.core.vo.LocalBasicDataStatsVo;

import java.util.List;

/**
 * 基础数据统计 service
 * @author: whuab_mc
 * @date: 2019-10-07 13:24:13:24
 * @version: V1.0
 */
public interface BasicDataStatsService {
    /**
     * 获取 本级基础数据统计vo
     * @param divisionCode 行政区划编码
     * @return
     */
    LocalBasicDataStatsVo getLocalBasicDataStatsVo(String divisionCode);

    /**
     * 获取 市、县、乡、村基础数据统计vo 集
     * @param divisionCode 行政区划编码
     * @return
     */
    List<BaseBasicDataStatsVo> listBasicDataStatsVo(String divisionCode);
}
