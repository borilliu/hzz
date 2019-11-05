package com.saili.hzz.backend.service.impl.statistical;

import com.saili.hzz.core.dao.mybatisdao.statistical.PatrolReportDao;
import com.saili.hzz.backend.service.statistical.PatrolReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 巡河报表 service impl
 * @author whuab_mc
 */
@Service
@Transactional(readOnly = true)
public class PatrolReportServiceImpl implements PatrolReportService {

    @Autowired
    private PatrolReportDao patrolReportDao;

    @Override
    public int countByRecordUniqueness(String divisionCode, String yearMonth) {
        return patrolReportDao.countByRecordUniqueness(divisionCode, yearMonth);
    }
}
