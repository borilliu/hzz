package com.saili.hzz.core.dao.mybatisdao.event;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.entity.RiverPatrolProcessHandledEntity;
import com.saili.hzz.core.form.statistics.StatsForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 *
 * @author: whuab_mc
 * @date: 2019-09-18 14:24:14:24
 * @version: V1.0
 */
@MyBatisDao
public interface RiverPatrolProcessHandledDao {

    List<RiverPatrolProcessHandledEntity> list(@Param("form") StatsForm form);

    /**
     * 保存
     * @param entity
     * @return
     */
    int save(RiverPatrolProcessHandledEntity entity);
}
