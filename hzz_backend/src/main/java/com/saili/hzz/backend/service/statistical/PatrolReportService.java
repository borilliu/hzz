package com.saili.hzz.backend.service.statistical;

/**
 * 巡查报表 service
 * @author whuab_mc
 */
public interface PatrolReportService {

    /**
     * 根据记录唯一性条件查询
     * @param divisionCode
     * @param yearMonth
     * @return
     */
    int countByRecordUniqueness(String divisionCode, String yearMonth);

}
