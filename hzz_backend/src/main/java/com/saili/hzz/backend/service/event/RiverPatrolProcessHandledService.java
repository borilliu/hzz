package com.saili.hzz.backend.service.event;

import com.saili.hzz.core.entity.RiverPatrolProcessHandledEntity;
import com.saili.hzz.core.form.statistics.StatsForm;

import java.util.List;

/**
 * @author: whuab_mc
 * @date: 2019-09-17 14:58:14:58
 * @version: V1.0
 */
public interface RiverPatrolProcessHandledService {
    /**
     * 查询已经处理的巡河流程节点记录
     * @param form
     * @return
     */
    List<RiverPatrolProcessHandledEntity> list(StatsForm form);

    /**
     * 保存
     * @param handleDetail 处理详情
     */
    int save(String taskId, String handleDetail);
}
