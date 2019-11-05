package com.saili.hzz.backend.service.statistics;

import com.saili.hzz.core.form.statistics.ProblemHandleStatsForm;
import com.saili.hzz.core.vo.ProblemHandleStatsVo;

/**
 * 问题处理统计 service
 * @author: whuab_mc
 * @date: 2019-10-11 13:49:13:49
 * @version: V1.0
 */
public interface ProblemHandleStatsService {
    /**
     * 统计问题处理业务
     * @param form
     * @return
     */
    ProblemHandleStatsVo statsProblemHandle(ProblemHandleStatsForm form);
}
