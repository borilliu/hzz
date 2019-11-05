package com.saili.hzz.backend.service.base;

import com.saili.hzz.core.domain.DivisionLevelAreaCountDo;
import com.saili.hzz.core.entity.TSLDivisionEntity;

import java.util.List;
import java.util.Map;

/**
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-08-28 13:16:13:16
 * @version: V1.0
 */
public interface DivisionService {
    List<TSLDivisionEntity> listById(String id);

    List<TSLDivisionEntity> listBycriteriaWay(String id, String pid);

    List<TSLDivisionEntity> listAll();

    List<TSLDivisionEntity> parseDivisionTree(String parentCode);

    TSLDivisionEntity parseDivisionTree(String parentCode, List<TSLDivisionEntity> divisionList);

    List<TSLDivisionEntity> listByParentCode(String parentCode);

    /**
     * 包含指定的地区编码的地区集
     * @param code
     * @return
     */
    List<TSLDivisionEntity> listContainParentCode(String code);

    /**
     * 每个行政区划级别下的行政区划数量统计
     * @param parentCode
     * @return key: 行政区划编码 value: 行政区划每个级别的地区数量
     */
    Map<String, DivisionLevelAreaCountDo> divisionLevelStats(String parentCode);

    /**
     * 根据编码获取行政区划信息
     * @param code
     * @return
     */
    TSLDivisionEntity getByCode(String code);
}
