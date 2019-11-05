package com.saili.hzz.core.domain;

import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import org.apache.tools.ant.taskdefs.Get;

import java.util.Date;
import java.util.List;

/**
 * @author: whuab_mc
 * @date: 2019-09-26 11:50:11:50
 * @version: V1.0
 */
public interface IProtalDo {
    /**
     * 获取 流程id
     * @return
     */
    String getProcInstId();

    /**
     * 获取 执行时间
     * @return
     */
    Date getExecuteDate();

    /**
     * 获取 河长对象
     * @return
     */
    RiverLakeChiefEntity getRiverLakeChief();

    /**
     * 获取 巡河事件对象
     * @return
     */
    TSLBaseRiverLakePatrolEvent getRiverLakePatrolEvent();
}
