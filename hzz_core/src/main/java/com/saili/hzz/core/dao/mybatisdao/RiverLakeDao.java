package com.saili.hzz.core.dao.mybatisdao;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.entity.TSLBaseRiverLake;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author whuab_mc
 */
@MyBatisDao
public interface RiverLakeDao {

    TSLBaseRiverLake getByCode(@Param("code") String code);

    /**
     * 根据 行政区划 查询
     * @param division
     * @return
     */
    List<TSLBaseRiverLake> listByDivision(@Param("division") TSLDivisionEntity division);

    /**
     * 查询包含指定父级的行政区划的河湖集
     * @param parentDivision
     * @return
     */
    List<TSLBaseRiverLake> listByContainsParentDivision(@Param("parentDivision") TSLDivisionEntity parentDivision);

    /**
     * 根据标识经纬度获取河湖信息
     * @param publicSignsLng
     * @param publicSignsLat
     * @return
     */
    TSLBaseRiverLake getByMarkerPoint(@Param("publicSignsLng") Double publicSignsLng, @Param("publicSignsLat") Double publicSignsLat);
}
