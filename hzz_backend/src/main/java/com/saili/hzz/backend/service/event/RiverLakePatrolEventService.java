package com.saili.hzz.backend.service.event;

import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.form.BaseForm;
import com.saili.hzz.core.form.statistics.ProblemHandleStatsForm;
import com.saili.hzz.core.form.statistics.StatsForm;
import com.saili.hzz.core.vo.AreaRiverPatrolStatsGridVo;

import java.util.List;

/**
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-08-06 13:10:13:10
 * @version: V1.0
 */
public interface RiverLakePatrolEventService {
    /**
     * 按地区统计应巡数量
     * @param form
     * @return
     */
    List<AreaRiverPatrolStatsGridVo> listTarget(BaseForm form);

    /**
     * 跟新处理状态
     *
     * @param procInsId 流程id
     * @param statusCode 状态码
     */
    Integer updateStatus(String procInsId, String statusCode);

    /**
     * 根据id 更新流程id
     * @param id
     * @param procInsId
     * @return
     */
    Integer updateProcInsIdById(String id, String procInsId);

    /**
     * 状态为已完成（-1）的数量
     * @param form
     * @return
     */
    List<AreaRiverPatrolStatsGridVo> listValue(BaseForm form);

    /**
     * 获取巡河事件集
     * @param form
     * @return
     */
    List<TSLBaseRiverLakePatrolEvent> list(StatsForm form);

    /**
     * 根据行政区划查询所有子行政区划集
     * @param area
     * @return
     */
    List<TSLBaseRiverLakePatrolEvent> listByParentDivision(TSLDivisionEntity area);

    /**
     * 根据问题处理统计查询条件查询
     * @param form
     * @return
     */
    List<TSLBaseRiverLakePatrolEvent> listByProblemHandleStatsForm(ProblemHandleStatsForm form);

    /**
     * 根据问题处理统计查询条件查询近一年的问题信息
     * @param form
     * @return
     */
    List<TSLBaseRiverLakePatrolEvent> listLastYearByProblemHandleStatsForm(ProblemHandleStatsForm form);
}
