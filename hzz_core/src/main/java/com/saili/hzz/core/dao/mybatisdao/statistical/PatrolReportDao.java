package com.saili.hzz.core.dao.mybatisdao.statistical;

import com.saili.hzz.core.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * 巡河表报 dao
 * @author whuab_mc
 */
@MyBatisDao
public interface PatrolReportDao {

    /**
     * 根据记录唯一性条件查询
     * @param divisionCode
     * @param yearMonth
     * @return
     */
    int countByRecordUniqueness(@Param("divisionCode") String divisionCode, @Param("yearMonth") String yearMonth);
}
