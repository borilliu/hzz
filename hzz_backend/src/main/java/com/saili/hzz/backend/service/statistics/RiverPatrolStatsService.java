package com.saili.hzz.backend.service.statistics;

import com.saili.hzz.core.form.statistics.StatsForm;
import com.saili.hzz.core.vo.AreaRiverPatrolStatsEChartsVo;
import com.saili.hzz.core.vo.HzProtalStatsVo;

import java.util.List;

/**
 * @author: whuab_mc
 * @date: 2019-09-11 11:50:11:50
 * @version: V1.0
 */
public interface RiverPatrolStatsService {
    /**
     * 地区巡河统计数据列表集
     * @param form
     * @return
     */
    List<HzProtalStatsVo> listGrid(StatsForm form);

    /**
     * 巡河统计数据图表
     * @param form
     * @return
     */
    AreaRiverPatrolStatsEChartsVo listECharts(StatsForm form);
}
