package com.saili.hzz.backend.service.impl.statistics;

import com.google.common.collect.Lists;
import com.saili.hzz.core.domain.BasicDataStatsDo;
import com.saili.hzz.core.domain.DivisionLevelAreaCountDo;
import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLBaseRiverLake;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.enums.DivisionLevelEnum;
import com.saili.hzz.backend.service.RiverLakeService;
import com.saili.hzz.backend.service.base.DivisionService;
import com.saili.hzz.backend.service.base.RiverLakeChiefService;
import com.saili.hzz.backend.service.statistics.BasicDataStatsService;
import com.saili.hzz.core.vo.BaseBasicDataStatsVo;
import com.saili.hzz.core.vo.LocalBasicDataStatsVo;
import com.saili.hzz.core.constant.Constants;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-07 13:37:13:37
 * @version: V1.0
 */
@Service
public class BasicDataStatsServiceImpl implements BasicDataStatsService {
    Logger logger = LoggerFactory.getLogger(BasicDataStatsServiceImpl.class);

    @Autowired
    private RiverLakeChiefService riverLakeChiefService;
    @Autowired
    private RiverLakeService riverLakeService;
    @Autowired
    private DivisionService divisionService;

    /**
     * 获取 本级基础数据统计vo 集
     *
     * @param divisionCode 行政区划编码
     * @return
     */
    @Override
    public LocalBasicDataStatsVo getLocalBasicDataStatsVo(String divisionCode) {
        LocalBasicDataStatsVo result = new LocalBasicDataStatsVo();

        TSLDivisionEntity division = new TSLDivisionEntity(divisionCode);
        List<RiverLakeChiefEntity> hzList = riverLakeChiefService.listByDivision(division);
        List<TSLBaseRiverLake> riverLakeList = riverLakeService.listByDivision(division);

        // 获取基础数据统计domain
        BasicDataStatsDo domain = getBasicDataStatsDo(hzList, riverLakeList);
        BeanUtils.copyProperties(domain, result);
        return result;
    }

    /**
     * 获取 市、县、乡、村基础数据统计vo 集
     *
     * @param divisionCode 行政区划编码
     * @return
     */
    @Override
    public List<BaseBasicDataStatsVo> listBasicDataStatsVo(String divisionCode) {
        List<BaseBasicDataStatsVo> results = Lists.newArrayList();
        TSLDivisionEntity division = new TSLDivisionEntity(divisionCode);
        List<RiverLakeChiefEntity> hzList = riverLakeChiefService.listByContainsParentDivision(division);
        List<TSLBaseRiverLake> riverLakeList = riverLakeService.listByContainsParentDivision(division);
        // 每个行政区划级别下的行政区划数量
        Map<String, DivisionLevelAreaCountDo> areaDivisionLevelStatsMap = divisionService.divisionLevelStats(divisionCode);
        // 所有市级或县级行政区划
        List<TSLDivisionEntity> countyDivisionList = divisionService.listByParentCode(divisionCode);
        // 所有的行政区划级别
        List<DivisionLevelEnum> divisionLevelEnumList = DivisionLevelEnum.listDivisionLevel();

        for (TSLDivisionEntity area : countyDivisionList) {
            BaseBasicDataStatsVo vo = new BaseBasicDataStatsVo();
            vo.setDivision(area);

            String areaCode = area.getCode();
            DivisionLevelAreaCountDo divisionLevelAreaCount = areaDivisionLevelStatsMap.get(areaCode);
            List<RiverLakeChiefEntity> sameDivisionCodeHzList = hzList.stream().filter(hz -> areaCode.equals(hz.getDivision().getCode()) || hz.getDivision().getParentCodes().contains(areaCode)).collect(Collectors.toList());
            List<TSLBaseRiverLake> sameDivisionCodeRiverLakeList = riverLakeList.stream().filter(riverLake -> areaCode.equals(riverLake.getTSLDivision().getCode()) || riverLake.getTSLDivision().getParentCodes().contains(areaCode)).collect(Collectors.toList());

            // 根据行政区划级别分组
            Map<String, List<RiverLakeChiefEntity>> hzListGroupByDivisionLevel = sameDivisionCodeHzList.stream().filter(hz -> StringUtils.isNotBlank(hz.getDivisionLevelCode())).collect(Collectors.groupingBy(RiverLakeChiefEntity::getDivisionLevelCode));
            Map<String, List<TSLBaseRiverLake>> riverLakeListGroupByDivisionLevel = sameDivisionCodeRiverLakeList.stream().filter(riverLake -> StringUtils.isNotBlank(riverLake.getDivisionLevelCode())).collect(Collectors.groupingBy(TSLBaseRiverLake::getDivisionLevelCode));
            for (DivisionLevelEnum divisionLevelEnum : divisionLevelEnumList) {
                String divisionLevelCode = divisionLevelEnum.getCode();
                List<RiverLakeChiefEntity> sameDivisionLevelHzList =  !hzListGroupByDivisionLevel.containsKey(divisionLevelCode) ? Lists.newArrayList() : hzListGroupByDivisionLevel.get(divisionLevelCode);
                List<TSLBaseRiverLake> sameDivisionLevelRiverLakeList = !riverLakeListGroupByDivisionLevel.containsKey(divisionLevelCode) ? Lists.newArrayList() : riverLakeListGroupByDivisionLevel.get(divisionLevelCode);
                // 获取基础数据统计domain
                BasicDataStatsDo domain = getBasicDataStatsDo(sameDivisionLevelHzList, sameDivisionLevelRiverLakeList);

                switch (divisionLevelCode) {
                    case Constants.DivisionLevel.CITY_LEVEL:
                        domain.setAreaCount(divisionLevelAreaCount.getCityCount());
                        vo.setCityLevel(domain);
                        continue;
                    case Constants.DivisionLevel.COUNTY_LEVEL:
                        domain.setAreaCount(divisionLevelAreaCount.getCountyCount());
                        vo.setCountyLevel(domain);
                        continue;
                    case Constants.DivisionLevel.TOWN_LEVEL:
                        domain.setAreaCount(divisionLevelAreaCount.getTownCount());
                        vo.setTownLevel(domain);
                        continue;
                    case Constants.DivisionLevel.VILLAGE_LEVEL:
                        domain.setAreaCount(divisionLevelAreaCount.getVillageCout());
                        vo.setVillageLevel(domain);
                        continue;
                }
            }
            results.add(vo);
        }
        return results;
    }

    /**
     * 获取基础数据统计domain
     *
     * @param hzList
     * @param riverLakeList
     * @return
     */
    private BasicDataStatsDo getBasicDataStatsDo(List<RiverLakeChiefEntity> hzList, List<TSLBaseRiverLake> riverLakeList) {
        // 1、河长数量统计
        BasicDataStatsDo result = new BasicDataStatsDo();
        Map<String, Long> hzTypeMap = hzList.stream().filter(hz -> StringUtils.isNotBlank(hz.getType())).collect(Collectors.groupingBy(RiverLakeChiefEntity::getType, Collectors.counting()));
        for (Map.Entry<String, Long> entry : hzTypeMap.entrySet()) {
            String code = entry.getKey();
            int count = entry.getValue().intValue();

            switch (code) {
                case Constants.HzType.FIST_PRESIDENT_HZ_CODE:
                    result.setFirstPresidentHzCount(count);
                    continue;
                case Constants.HzType.PRESIDENT_HZ_CODE:
                    result.setPresidentHzCount(count);
                    continue;
                case Constants.HzType.VICE_PRESIDENT_HZ_CODE:
                    result.setVicePresidentHzCount(count);
                    continue;
                case Constants.HzType.HZ_CODE:
                    result.setHzCount(count);
                    continue;
                case Constants.HzType.VICE_HZ_CODE:
                    result.setViceHzCount(count);
                    continue;
            }
        }

        // 2、河湖数量统计
        Map<String, Long> riverLakeTypeMap = riverLakeList.stream().filter(riverLake -> StringUtils.isNotBlank(riverLake.getRiverLakeType())).collect(Collectors.groupingBy(TSLBaseRiverLake::getRiverLakeType, Collectors.counting()));
        for (Map.Entry<String, Long> entry : riverLakeTypeMap.entrySet()) {
            String code = entry.getKey();
            int count = entry.getValue().intValue();

            switch (code) {
                case Constants.RiverLakeType.RIVER_CODE:
                    result.setRiverCount(count);
                    continue;
                case Constants.RiverLakeType.LAKE_CODE:
                    result.setLakeCount(count);
                    continue;
                case Constants.RiverLakeType.REACH_CODE:
                    result.setReachCount(count);
                    continue;
                case Constants.RiverLakeType.RESERVOIR_CODE:
                    result.setReservoirCount(count);
                    continue;
            }
        }
        return result;
    }
}
