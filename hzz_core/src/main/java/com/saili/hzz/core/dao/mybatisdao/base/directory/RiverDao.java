package com.saili.hzz.core.dao.mybatisdao.base.directory;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.dao.mybatisdao.BaseRiverLakeDao;
import com.saili.hzz.core.entity.TSLBaseRiverLake;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.entity.TSLRLRiverEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author whuab_mc
 */
@MyBatisDao
public interface RiverDao  extends BaseRiverLakeDao<TSLRLRiverEntity> {

    /**
     * 根据 行政地区 查询
     * @param division
     * @return
     */
    List<TSLBaseRiverLake> listByDivision(@Param("division") TSLDivisionEntity division);
}
