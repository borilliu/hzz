package com.saili.hzz.backend.service.impl.statistics;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.saili.hzz.core.domain.HzProtalStatsDo;
import com.saili.hzz.core.domain.IProtalDo;
import com.saili.hzz.core.domain.UnProtalDo;
import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.RiverPatrolProcessHandledEntity;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.form.statistics.StatsForm;
import com.saili.hzz.backend.service.base.RiverLakeChiefService;
import com.saili.hzz.backend.service.event.RiverPatrolProcessHandledService;
import com.saili.hzz.backend.service.statistics.RiverPatrolStatsService;
import com.saili.hzz.core.vo.AreaRiverPatrolStatsEChartsVo;
import com.saili.hzz.core.vo.HzProtalStatsVo;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import com.saili.hzz.core.util.DateUtils;
import com.saili.hzz.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/***
 *
 * @author: whuab_mc
 * @date: 2019-09-11 11:51:11:51
 * @version: V1.0
 */
public abstract class RiverPatrolStatsServiceImpl implements RiverPatrolStatsService {
    @Autowired
    protected TaskService taskService;
    @Autowired
    private RiverLakeChiefService riverLakeChiefService;
    @Autowired
    private RiverPatrolProcessHandledService riverPatrolProcessHandledService;

    /**
     * 获取所有地区
     *
     * @param form
     * @return
     */
    public abstract List<TSLDivisionEntity> listAreaAll(StatsForm form);

    /**
     * 获取河长集按指标进行分组后的map
     *
     * @param hzList
     * @return
     */
    public abstract Map<String, List<RiverLakeChiefEntity>> getStandMap(List<RiverLakeChiefEntity> hzList);

    /**
     * 设置vo的指标名称
     *
     * @param vo
     * @param hzList
     */
    public abstract void setStandName(HzProtalStatsVo vo, List<RiverLakeChiefEntity> hzList);

    /**
     * 【地区/河长】巡河统计数据集
     *
     * @param form
     * @return
     */
    @Override
    public List<HzProtalStatsVo> listGrid(StatsForm form) {
        List<HzProtalStatsVo> results = Lists.newArrayList();

        List<TSLDivisionEntity> areaAllList = listAreaAll(form);

        // 1、已巡源数据
        List<HzProtalStatsDo> valueList = listProtal(form, areaAllList);

        // 2、未巡源数据
        List<HzProtalStatsDo> unValueList = listUnPratol(form, areaAllList);

        // 已巡源数据按【地区+月份】分组
        Map<String, List<HzProtalStatsDo>> valueAreaMonthMap = valueList.stream()
                .filter(item -> null != item.keyAreaMonth())
                .collect(Collectors.groupingBy(HzProtalStatsDo::keyAreaMonth));
        // 未巡源数据按【地区+月份】分组
        Map<String, List<HzProtalStatsDo>> unValueAreaMonthMap = unValueList.stream()
                .filter(item -> null != item.keyAreaMonth())
                .collect(Collectors.groupingBy(HzProtalStatsDo::keyAreaMonth));

        for (Map.Entry<String, List<HzProtalStatsDo>> valueEntry : valueAreaMonthMap.entrySet()) {
            // 当前【地区+月份】下的所有已巡河长集（河长不去重。相同河长巡查几次，那么就按几次巡查人数计算）
            List<HzProtalStatsDo> valueDomainList = valueEntry.getValue();
            List<RiverLakeChiefEntity> valueHzList = valueDomainList.stream()
                    .flatMap(item -> item.getHzList().stream())
                    .collect(Collectors.toList());

            // 当前【地区+月份】下的所有未巡河长集（河长不去重。相同河长巡查几次，那么就按几次巡查人数计算）
            List<HzProtalStatsDo> unValueDomainList = unValueAreaMonthMap.get(valueEntry.getKey());
            List<RiverLakeChiefEntity> unValueHzList = unValueDomainList.stream()
                    .flatMap(item -> item.getHzList().stream())
                    .collect(Collectors.toList());

            // 定义基准信息
            HzProtalStatsDo baseConf = valueDomainList.get(0);
            // TODO:
            if (StatsForm.AREA_ANTYPE.equals(form.getAntype())) {
                results.add(setValueVo(valueHzList, unValueHzList, baseConf));
            } else if (StatsForm.RIVER_ANTYPE.equals(form.getAntype())) {
                results.addAll(doHzStatistics(valueHzList, unValueHzList, baseConf));
            }

            // 移除已经处理的未巡数据
            unValueAreaMonthMap.remove(valueEntry.getKey());
        }

        // 处理剩下的未巡数据
        for (Map.Entry<String, List<HzProtalStatsDo>> domainEntry : unValueAreaMonthMap.entrySet()) {
            // 当前【地区+月份】下的所有未巡河长集（河长去重。相同河长巡查了多次，那么只作为一个巡查人数计算）
            List<HzProtalStatsDo> unValueDomainList = domainEntry.getValue();
            List<RiverLakeChiefEntity> unValueHzList = unValueDomainList.stream()
                    .flatMap(item -> item.getHzList().stream())
                    .collect(Collectors.toList());

            // 定义基准信息
            HzProtalStatsDo baseConf = unValueDomainList.get(0);
            // TODO:
            if (StatsForm.AREA_ANTYPE.equals(form.getAntype())) {
                results.add(setUnValueVo(unValueHzList, baseConf));
            } else if (StatsForm.RIVER_ANTYPE.equals(form.getAntype())) {
                results.addAll(doUnValueHzStatistics(unValueHzList, baseConf));
            }

        }
        return results;
    }

    /**
     * 【地区/河长】巡河统计数据图表集
     *
     * @param form
     * @return
     */
    @Override
    public AreaRiverPatrolStatsEChartsVo listECharts(StatsForm form) {
        AreaRiverPatrolStatsEChartsVo result = null;

        List<HzProtalStatsVo> sourceList = listGrid(form);
        List<Object> areaNameList = sourceList.stream()
                .map(HzProtalStatsVo::getAreaName)
                .collect(Collectors.toList());
        String[] areaNameArrays = new String[areaNameList.size()];
        areaNameList.toArray(areaNameArrays);

        Map<String, List<HzProtalStatsVo>> map = Maps.newHashMap();
        if (StatsForm.AREA_ANTYPE.equals(form.getAntype())) {
            // 按【月份】分组
            map = sourceList.stream()
                    .filter(obj -> null != obj.getMonth())
                    .collect(Collectors.groupingBy(HzProtalStatsVo::getMonth));
        } else if (StatsForm.RIVER_ANTYPE.equals(form.getAntype())) {
            // 按【月份+指标】分组
            map = sourceList.stream()
                    .filter(obj -> null != obj.monthStandNameKey())
                    .collect(Collectors.groupingBy(HzProtalStatsVo::monthStandNameKey));
        }

        result = doECharts(areaNameArrays, map);

        return result;
    }

    /**
     * 巡河统计数据图表
     *
     * @param areaNameArrays
     * @param map
     * @return
     */
    private AreaRiverPatrolStatsEChartsVo doECharts(String[] areaNameArrays, Map<String, List<HzProtalStatsVo>> map) {
        AreaRiverPatrolStatsEChartsVo result = new AreaRiverPatrolStatsEChartsVo();
        List<String> legendDataList = Lists.newArrayList();
        for (Map.Entry<String, List<HzProtalStatsVo>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<HzProtalStatsVo> list = entry.getValue();

            String targetBarName = key + "_" + AreaRiverPatrolStatsEChartsVo.TARGET_NAME;
            String valueBarName = key + "_" + AreaRiverPatrolStatsEChartsVo.VALUE_NAME;
            double[] target = list.stream().mapToDouble(HzProtalStatsVo::getTarget).toArray();
            double[] value = list.stream().mapToDouble(HzProtalStatsVo::getValue).toArray();
            AreaRiverPatrolStatsEChartsVo.Serie targetSerie = new AreaRiverPatrolStatsEChartsVo.Serie();
            targetSerie.setName(targetBarName);
            targetSerie.setType(AreaRiverPatrolStatsEChartsVo.Serie.BAR_TYPE);
            targetSerie.setBarGap(0);
            targetSerie.setData(target);

            AreaRiverPatrolStatsEChartsVo.Serie valueSerie = new AreaRiverPatrolStatsEChartsVo.Serie();
            valueSerie.setName(valueBarName);
            valueSerie.setType(AreaRiverPatrolStatsEChartsVo.Serie.BAR_TYPE);
            targetSerie.setBarGap(0);
            valueSerie.setData(value);

            legendDataList.add(targetBarName);
            legendDataList.add(valueBarName);

            result.getSeries().add(targetSerie);
            result.getSeries().add(valueSerie);
        }

        result.setxAxis(areaNameArrays);
        String[] legendData = new String[legendDataList.size()];
        legendDataList.toArray(legendData);
        result.setLegend(legendData);

        return result;
    }

    /**
     * 获取【已巡】源数据
     *
     * @param form 查询条件
     * @return
     */
    private List<HzProtalStatsDo> listProtal(StatsForm form, List<TSLDivisionEntity> areaAllList) {
        List<RiverPatrolProcessHandledEntity> riverPatrolProcessHandledList = riverPatrolProcessHandledService.list(form);
        List<HzProtalStatsDo> results = createListHzProtalStatsDo(areaAllList, riverPatrolProcessHandledList);
        return results;
    }

    /**
     * 获取【未巡】源数据
     *
     * @param form 查询条件
     * @return
     */
    private List<HzProtalStatsDo> listUnPratol(StatsForm form, List<TSLDivisionEntity> areaAllList) {
        List<UnProtalDo> unProtalDoList = Lists.newArrayList();
        // 获取正在进行的任务
        List<Task> taskList = taskService.createTaskQuery().list();
        for (Task task : taskList) {
            // 取出还未巡查的用户组
            List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(task.getId());
            identityLinkList = identityLinkList.stream()
                    .filter(identityLink -> null == identityLink.getUserId())
                    .collect(Collectors.toList());
            // TODO: 此处应该是按一个人计算，还是把当前组中所有河长拿出来计算
            // 取出每个还未巡查的用户中的河长集
            for (IdentityLink identityLink : identityLinkList) {
                // 取出河长集信息
                List<RiverLakeChiefEntity> list = riverLakeChiefService.listByRoleCode(identityLink.getGroupId());
                for (RiverLakeChiefEntity hz : list) {
                    UnProtalDo unProtalDo = new UnProtalDo();
                    unProtalDo.setTask(task);
                    unProtalDo.setRiverLakeChief(hz);
                    unProtalDoList.add(unProtalDo);
                }
            }
        }

        List<HzProtalStatsDo> results = createListHzProtalStatsDo(areaAllList, unProtalDoList);
        return results;
    }


    public List<HzProtalStatsDo> createListHzProtalStatsDo(List<TSLDivisionEntity> areaAllList, List<? extends IProtalDo> list) {
        List<HzProtalStatsDo> results = Lists.newArrayList();
        List<HzProtalStatsDo> domainList = Lists.newArrayList();
        // 把需要统计的地区 按 【地区】 分组
        Map<String, TSLDivisionEntity> divisonMap = areaAllList.stream().collect(Collectors.toMap(TSLDivisionEntity::getCode, Function.identity(), (k1, k2) -> k2));
        for (IProtalDo entity : list) {
            if (null == entity.getRiverLakeChief()) {
                continue;
            }

            HzProtalStatsDo domain = new HzProtalStatsDo();
            domain.setProcInsId(entity.getProcInstId());
            domain.setMonth(DateUtils.formatDate(entity.getExecuteDate(), DateUtils.yyyyMM));
            List<RiverLakeChiefEntity> hzList = Lists.newArrayList();
            hzList.add(entity.getRiverLakeChief());
            domain.setHzList(hzList);

            // 指定的统计区域
            TSLDivisionEntity division = null;
            // 当前河长所属的区域code
            String divisionCode = StringUtil.isEmpty(entity.getRiverLakeChief().getDivisionCode()) ? null : entity.getRiverLakeChief().getDivisionCode();
            if (divisonMap.containsKey(divisionCode)) {
                division = divisonMap.get(divisionCode);

                domain.setDivisionCode(division.getCode());
                domain.setDivision(division);
                domainList.add(domain);
                continue;
            }

            if (null == entity.getRiverLakeChief().getDivision() || StringUtil.isEmpty(entity.getRiverLakeChief().getDivision().getParentCodes())) {
                continue;
            }
            // 当前河长所属区域的所有父级code字符串
            String[] parentCodeArray = entity.getRiverLakeChief().getDivision().getParentCodes().split(",");
            for (String parentCode : parentCodeArray) {
                if (divisonMap.containsKey(parentCode)) {
                    division = divisonMap.get(parentCode);

                    domain.setDivisionCode(division.getCode());
                    domain.setDivision(division);
                    domainList.add(domain);
                    break;
                }
            }
        }
        // 按【流程 + 地区 + 月份】 分组 生成domain
        Map<String, List<HzProtalStatsDo>> domainMap = domainList.stream().collect(Collectors.groupingBy(HzProtalStatsDo::keyProcInsIdAreaMonth));
        for (Map.Entry<String, List<HzProtalStatsDo>> domainEntry : domainMap.entrySet()) {
            HzProtalStatsDo baseConfig = domainEntry.getValue().get(0);

            HzProtalStatsDo domain = new HzProtalStatsDo();
            domain.setProcInsId(baseConfig.getProcInsId());
            domain.setDivisionCode(baseConfig.getDivisionCode());
            domain.setDivision(baseConfig.getDivision());
            domain.setMonth(baseConfig.getMonth());
            List<RiverLakeChiefEntity> hzList = domainEntry.getValue().stream()
                    .flatMap(item -> item.getHzList().stream())
                    .collect(Collectors.toList());
            domain.setHzList(hzList);
            results.add(domain);
        }
        return results;
    }

    /**
     * 设置未巡 vo
     *
     * @param unValueHzList
     * @param baseConf
     * @return
     */
    private HzProtalStatsVo setUnValueVo(List<RiverLakeChiefEntity> unValueHzList, HzProtalStatsDo baseConf) {
        String month = baseConf.getMonth();
        String areaName = baseConf.getDivision().getName();

        HzProtalStatsVo vo = new HzProtalStatsVo();
        vo.setAreaName(areaName);
        vo.setMonth(month);
        vo.setValue(0);
        vo.setUnValue(null != unValueHzList && unValueHzList.size() > 0 ? unValueHzList.size() : 0);
        vo.setTarget(vo.getValue() + vo.getUnValue());
        return vo;
    }

    /**
     * 设置已巡 vo
     *
     * @param valueHzList
     * @param unValueHzList
     * @param baseConf
     * @return
     */
    private HzProtalStatsVo setValueVo(List<RiverLakeChiefEntity> valueHzList, List<RiverLakeChiefEntity> unValueHzList, HzProtalStatsDo baseConf) {
        String month = baseConf.getMonth();
        String areaName = baseConf.getDivision().getName();

        HzProtalStatsVo vo = new HzProtalStatsVo();
        vo.setAreaName(areaName);
        vo.setMonth(month);
        vo.setValue(null != valueHzList && valueHzList.size() > 0 ? valueHzList.size() : 0);
        vo.setUnValue(null != unValueHzList && unValueHzList.size() > 0 ? unValueHzList.size() : 0);
        vo.setTarget(vo.getValue() + vo.getUnValue());
        return vo;
    }

    /**
     * 按【地区河长】统计
     *
     * @param valueList   已巡源数据
     * @param unValueList 未巡源数据
     * @return
     */
    private List<HzProtalStatsVo> doHzStatistics(List<RiverLakeChiefEntity> valueList, List<RiverLakeChiefEntity> unValueList, HzProtalStatsDo baseConf) {
        List<HzProtalStatsVo> results = Lists.newArrayList();
        // 已巡河长集按【河长职务】分组
        Map<String, List<RiverLakeChiefEntity>> valueHzTypeMap = getStandMap(valueList);
        // 未巡河长集按【河长职务】分组
        Map<String, List<RiverLakeChiefEntity>> unValueHzTypeMap = getStandMap(unValueList);
        for (Map.Entry<String, List<RiverLakeChiefEntity>> entry : valueHzTypeMap.entrySet()) {
            // 已巡河长集
            List<RiverLakeChiefEntity> vlueHzList = entry.getValue();
            // 未巡河长集
            List<RiverLakeChiefEntity> unValueHzList = unValueHzTypeMap.get(entry.getKey());

            // 设置已巡 vo
            HzProtalStatsVo vo = setValueVo(vlueHzList, unValueHzList, baseConf);
            setStandName(vo, vlueHzList);
            results.add(vo);
            unValueHzTypeMap.remove(entry.getKey());
        }

        // 处理剩下的未巡数据
        for (Map.Entry<String, List<RiverLakeChiefEntity>> entry : unValueHzTypeMap.entrySet()) {
            // 已巡河长集
            List<RiverLakeChiefEntity> unValueHzList = entry.getValue();

            // 设置已巡 vo
            HzProtalStatsVo vo = setUnValueVo(unValueHzList, baseConf);
            setStandName(vo, unValueHzList);
            results.add(vo);
        }
        return results;
    }

    /***
     * 处理 未巡查 的河长统计
     * @param unValueRiverList
     * @param baseConf
     * @return
     */
    public List<HzProtalStatsVo> doUnValueHzStatistics(List<RiverLakeChiefEntity> unValueRiverList, HzProtalStatsDo baseConf) {
        List<HzProtalStatsVo> results = Lists.newArrayList();
        Map<String, List<RiverLakeChiefEntity>> unValueHzTypeMap = getStandMap(unValueRiverList);
        for (Map.Entry<String, List<RiverLakeChiefEntity>> entry : unValueHzTypeMap.entrySet()) {
            List<RiverLakeChiefEntity> unValueHzList = entry.getValue();

            // 设置未巡 vo
            HzProtalStatsVo vo = setUnValueVo(unValueHzList, baseConf);
            setStandName(vo, unValueHzList);
            results.add(vo);
        }
        return results;
    }
}
