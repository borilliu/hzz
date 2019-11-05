package com.saili.hzz.core.dao.mybatisdao.event;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.form.BaseForm;
import com.saili.hzz.core.form.statistics.ProblemHandleStatsForm;
import com.saili.hzz.core.form.statistics.StatsForm;
import com.saili.hzz.core.vo.AreaRiverPatrolStatsGridVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-08-16 11:27:11:27
 * @version: V1.0
 */
@MyBatisDao
public interface RiverLakePatrolEventDao {
    /**
     * 应巡查总数量
     * @param form
     * @return
     */
    List<AreaRiverPatrolStatsGridVo> listTarget(@Param("form") BaseForm form);

    /**
     * 更新状态
     *
     * @param procInsId 流程id
     * @param statusCode
     * @return
     */
    Integer updateStatus(@Param("procInsId") String procInsId, @Param("statusCode")String statusCode);

    /**
     * 根据id 更新流程id
     *
     * @param id
     * @param procInsId
     * @return
     */
    Integer updateProcInsIdById(@Param("id") String id, @Param("procInsId") String procInsId);

    /**
     * 已巡查数量
     * @return
     */
    List<AreaRiverPatrolStatsGridVo> listFinish(@Param("form") BaseForm form);

    /**
     * 获取巡河事件集
     * @param form
     * @return
     */
    List<TSLBaseRiverLakePatrolEvent> list(@Param("form") StatsForm form);

    /**
     * 根据行政区划查询所有子行政区划集
     * @param parentDivision
     * @return
     */
    List<TSLBaseRiverLakePatrolEvent> listByParentDivision(@Param("parentDivision") TSLDivisionEntity parentDivision);

    /**
     * 根据问题处理统计查询条件插叙
     * @param form
     * @return
     */
    List<TSLBaseRiverLakePatrolEvent> listByProblemHandleStatsForm(@Param("form") ProblemHandleStatsForm form);

    /**
     * 根据问题处理统计查询条件查询近一年的问题信息
     * @param form
     * @return
     */
    List<TSLBaseRiverLakePatrolEvent> listLastYearByProblemHandleStatsForm(@Param("form") ProblemHandleStatsForm form);
}
