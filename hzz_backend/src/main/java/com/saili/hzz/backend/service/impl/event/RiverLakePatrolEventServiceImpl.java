package com.saili.hzz.backend.service.impl.event;

import com.google.common.collect.Lists;
import com.saili.hzz.core.dao.mybatisdao.event.RiverLakePatrolEventDao;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.form.BaseForm;
import com.saili.hzz.core.form.statistics.ProblemHandleStatsForm;
import com.saili.hzz.core.form.statistics.StatsForm;
import com.saili.hzz.backend.service.event.RiverLakePatrolEventService;
import com.saili.hzz.core.vo.AreaRiverPatrolStatsGridVo;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-08-06 13:14:13:14
 * @version: V1.0
 */
@Service
@Transactional(readOnly = true)
public class RiverLakePatrolEventServiceImpl implements RiverLakePatrolEventService {
    @Autowired
    private RiverLakePatrolEventDao dao;

    /**
     * 按地区统计应巡数量
     *
     * @param form
     * @return
     */
    @Override
    public List<AreaRiverPatrolStatsGridVo> listTarget(BaseForm form) {
        return dao.listTarget(form);
    }

    /**
     * 跟新处理状态
     *
     * @param procInsId 流程id
     * @param statusCode 状态码
     */
    @Transactional(readOnly = false)
    @Override
    public Integer updateStatus(String procInsId, String statusCode) {
        return dao.updateStatus(procInsId, statusCode);
    }

    @Override
    public Integer updateProcInsIdById(String id, String procInsId) {
        return dao.updateProcInsIdById(id, procInsId);
    }

    /**
     * 状态为已完成（-1）的数量
     *
     * @param form
     * @return
     */
    @Override
    public List<AreaRiverPatrolStatsGridVo> listValue(BaseForm form) {
        return dao.listFinish(form);
    }

    @Override
    public List<TSLBaseRiverLakePatrolEvent> list(StatsForm form) {
        return dao.list(form);
    }

    @Override
    public List<TSLBaseRiverLakePatrolEvent> listByParentDivision(TSLDivisionEntity parentDivision) {
        if (null == parentDivision || StringUtils.isBlank(parentDivision.getCode())) {
            return Lists.newArrayList();
        }
        return dao.listByParentDivision(parentDivision);
    }

    @Override
    public List<TSLBaseRiverLakePatrolEvent> listByProblemHandleStatsForm(ProblemHandleStatsForm form) {
        if (null == form) {
            return Lists.newArrayList();
        }
        return dao.listByProblemHandleStatsForm(form);
    }

    @Override
    public List<TSLBaseRiverLakePatrolEvent> listLastYearByProblemHandleStatsForm(ProblemHandleStatsForm form) {
        if (null == form) {
            return Lists.newArrayList();
        }
        return dao.listLastYearByProblemHandleStatsForm(form);
    }
}
