package com.saili.hzz.backend.service.impl.statistics;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.saili.hzz.core.domain.problem.statistics.ProblemExecuteStatsDo;
import com.saili.hzz.core.domain.problem.statistics.ProblemIndexStatsDo;
import com.saili.hzz.core.domain.problem.statistics.ProblemRatioStatsDo;
import com.saili.hzz.core.domain.problem.statistics.YearProblemHandledStatsDo;
import com.saili.hzz.core.entity.IStatsType;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.form.statistics.ProblemHandleStatsForm;
import com.saili.hzz.backend.service.base.DivisionService;
import com.saili.hzz.backend.service.event.RiverLakePatrolEventService;
import com.saili.hzz.backend.service.statistics.ProblemHandleStatsService;
import com.saili.hzz.core.vo.ProblemHandleStatsVo;
import com.saili.hzz.core.constant.Constants;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import com.saili.hzz.core.util.DateUtils;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import com.saili.hzz.web.system.pojo.base.TSType;
import com.saili.hzz.web.system.pojo.base.TSTypegroup;
import com.saili.hzz.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.stream.Collectors;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-11 13:51:13:51
 * @version: V1.0
 */
@Service
public class ProblemHandleStatsServiceImpl implements ProblemHandleStatsService {
    Logger logger = LoggerFactory.getLogger(ProblemHandleStatsServiceImpl.class);

    @Autowired
    private DivisionService divisionService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RiverLakePatrolEventService riverLakePatrolEventService;

    /**
     * 统计问题处理业务
     *
     * @param form
     * @return
     */
    @Override
    public ProblemHandleStatsVo statsProblemHandle(ProblemHandleStatsForm form) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("问题处理统计业务处理");
        /**
         * 1：获取资源数据
         */
        TSLDivisionEntity division = divisionService.getByCode(form.getArea().getCode());
        List<IStatsType> statsTypeList = Lists.newArrayList();
        if (ProblemHandleStatsForm.PROBLEM_TYPE_STATS_TYPE.equals(form.getStatsType())) {
            TSTypegroup typegroup = systemService.getTypeGroupByCode("questType");
            List<TSType> problemTypeList = typegroup.getTSTypes();
            Collections.sort(problemTypeList, new Comparator<TSType>() {
                @Override
                public int compare(TSType o1, TSType o2) {
                    return o1.getOrderNum() - o2.getOrderNum();
                }
            });
            statsTypeList.addAll(problemTypeList);
        } else if (ProblemHandleStatsForm.AREA_STATS_TYPE.equals(form.getStatsType())) {
            List<TSLDivisionEntity> divisionList = divisionService.listByParentCode(form.getArea().getCode());
            statsTypeList.addAll(divisionList);
        }

         List<TSLBaseRiverLakePatrolEvent> problemAllList = riverLakePatrolEventService.listByProblemHandleStatsForm(form);
        if (problemAllList.size() <= 0) {
            ProblemHandleStatsVo vo = new ProblemHandleStatsVo();
            // 4、近一年巡河问题处理完成次数变化趋势图
            YearProblemHandledStatsDo problemHandledStatsDo = statsYearProblemHandled(form);
            vo.setYearProblemHandledStatsDo(problemHandledStatsDo);
            return vo;
        }
        List<TSLBaseRiverLakePatrolEvent> problemList = problemAllList.stream().filter(problem -> null != problem && StringUtils.isNotBlank(problem.getProcInsId())).distinct().collect(Collectors.toList());

        List<String> procInstIdList = problemAllList.stream().filter(problem -> null != problem && StringUtils.isNotBlank(problem.getProcInsId())).map(TSLBaseRiverLakePatrolEvent::getProcInsId).distinct().collect(Collectors.toList());
        // 查询待处理的流程
        List<ProcessInstance> runtimeProcessInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(new HashSet(procInstIdList)).list();
        List<String> pendingProcInstIdList = runtimeProcessInstanceList.stream().filter(panding -> null != panding && StringUtils.isNotBlank(panding.getId())).map(ProcessInstance::getId).distinct().collect(Collectors.toList());
        // 查询已经处理过（结束）的流程
        List<HistoricProcessInstance> historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery().processInstanceIds(new HashSet(procInstIdList)).finished().list();
        List<String> processedProcInstIdList = historicProcessInstanceList.stream().filter(processed -> null != processed && StringUtils.isNotBlank(processed.getId())).map(HistoricProcessInstance::getId).distinct().collect(Collectors.toList());

        List<TSLBaseRiverLakePatrolEvent> pendingProblemList = Lists.newArrayList();
        List<TSLBaseRiverLakePatrolEvent> processedProblemList = Lists.newArrayList();
        problemAllList.stream().filter(problem -> StringUtils.isNotBlank(problem.getProcInsId())).distinct()
                .forEach(problem -> {
                    String procInsId = problem.getProcInsId();
                    if (pendingProcInstIdList.contains(procInsId)) {
                        pendingProblemList.add(problem);
                    }

                    if (processedProcInstIdList.contains(procInsId)) {
                        processedProblemList.add(problem);
                    }
                });


        Map<String, Long> problemListGroupByStatsType = Maps.newHashMap();
        Map<String, Long> pendingProblemListGroupByStatsType = Maps.newHashMap();
        Map<String, Long> processedProblemListGroupByStatsType = Maps.newHashMap();
        if (ProblemHandleStatsForm.PROBLEM_TYPE_STATS_TYPE.equals(form.getStatsType())) {
            problemListGroupByStatsType = problemList.stream().filter(problem -> null != problem && StringUtils.isNotBlank(problem.getQuestType())).collect(Collectors.groupingBy(TSLBaseRiverLakePatrolEvent::getQuestType, Collectors.counting()));
            pendingProblemListGroupByStatsType = pendingProblemList.stream().filter(problem -> null != problem && StringUtils.isNotBlank(problem.getQuestType())).collect(Collectors.groupingBy(TSLBaseRiverLakePatrolEvent::getQuestType, Collectors.counting()));
            processedProblemListGroupByStatsType = processedProblemList.stream().filter(problem -> null != problem && StringUtils.isNotBlank(problem.getQuestType())).collect(Collectors.groupingBy(TSLBaseRiverLakePatrolEvent::getQuestType, Collectors.counting()));
        } else if (ProblemHandleStatsForm.AREA_STATS_TYPE.equals(form.getStatsType())) {
            for (IStatsType statsType : statsTypeList) {
                Long value = problemList.stream().filter(problem -> null != problem && problem.getAddParentCode().contains(statsType.getCode())).collect(Collectors.counting());
                Long pendingValue = pendingProblemList.stream().filter(problem -> null != problem && problem.getAddParentCode().contains(statsType.getCode())).collect(Collectors.counting());
                Long processedValue = processedProblemList.stream().filter(problem -> null != problem && problem.getAddParentCode().contains(statsType.getCode())).collect(Collectors.counting());
                problemListGroupByStatsType.put(statsType.getCode(), value);
                pendingProblemListGroupByStatsType.put(statsType.getCode(), pendingValue);
                processedProblemListGroupByStatsType.put(statsType.getCode(), processedValue);
            }
        }

        /**
         * 2：具体业务处理
         */
        // 1、各指标数量统计
        ProblemIndexStatsDo problemIndexStatsDo = statsProblemIndex(statsTypeList, pendingProblemList, processedProblemList);
        // 2、巡河问题占比统计
        ProblemRatioStatsDo problemRatioStatsDo = statsProblemRatio(statsTypeList, problemListGroupByStatsType);
        // 3、巡河处理完成情况统计
        ProblemExecuteStatsDo problemExecuteStatsDo = statsProblemExecute(statsTypeList, pendingProblemListGroupByStatsType, processedProblemListGroupByStatsType);
        // 4、近一年巡河问题处理完成次数变化趋势图
        YearProblemHandledStatsDo yearProblemHandledStatsDo = statsYearProblemHandled(form);

        String startDay = DateUtils.formatDate(form.getStartDay(), Constants.DateFormatKeys.DATE_10_FORMAT);
        String endDay = DateUtils.formatDate(form.getEndDay(), Constants.DateFormatKeys.DATE_10_FORMAT);
        String searchDate = startDay + "-" + endDay;
        /**
         * 3：设置返回结果对象
         */
        ProblemHandleStatsVo vo = new ProblemHandleStatsVo();
        vo.setArea(division);
        vo.setSearchDate(searchDate);
        vo.setProblemIndexStatsDo(problemIndexStatsDo);
        vo.setProblemRatioStatsDo(problemRatioStatsDo);
        vo.setProblemExecuteStatsDo(problemExecuteStatsDo);
        vo.setYearProblemHandledStatsDo(yearProblemHandledStatsDo);
        stopWatch.stop();
        logger.debug("{}", stopWatch.prettyPrint());
        return vo;
    }

    /**
     * 近一年巡河问题处理完成次数变化趋势图
     *
     * @return
     */
    private YearProblemHandledStatsDo statsYearProblemHandled(ProblemHandleStatsForm form) {
        ProblemHandleStatsForm searchform = new ProblemHandleStatsForm();
        BeanUtils.copyProperties(form, searchform);
        searchform.setStartTime(form.getLastYearStartTime());
        searchform.setEndTime(new Date());

        // 最近一年内的所有月份集合
        List<String> monthList = DateUtils.getMonthBetween(searchform.getStartTime(), searchform.getEndTime());

        List<IStatsType> statsTypeList = Lists.newArrayList();
        if (ProblemHandleStatsForm.PROBLEM_TYPE_STATS_TYPE.equals(form.getStatsType())) {
            TSTypegroup typegroup = systemService.getTypeGroupByCode("questType");
            List<TSType> problemTypeList = typegroup.getTSTypes();
            Collections.sort(problemTypeList, new Comparator<TSType>() {
                @Override
                public int compare(TSType o1, TSType o2) {
                    return o1.getOrderNum() - o2.getOrderNum();
                }
            });
            statsTypeList.addAll(problemTypeList);
        } else if (ProblemHandleStatsForm.AREA_STATS_TYPE.equals(form.getStatsType())) {
            List<TSLDivisionEntity> divisionList = divisionService.listByParentCode(searchform.getArea().getCode());
            statsTypeList.addAll(divisionList);
        }

        List<TSLBaseRiverLakePatrolEvent> problemAllList = riverLakePatrolEventService.listByProblemHandleStatsForm(searchform);
        if (problemAllList.size() <= 0) {
            return null;
        }
        List<TSLBaseRiverLakePatrolEvent> problemList = problemAllList.stream().filter(problem -> null != problem && StringUtils.isNotBlank(problem.getProcInsId())).distinct().collect(Collectors.toList());

        // 查询已经处理过（结束）的流程
        List<String> procInstIdList = problemAllList.stream().filter(problem -> null != problem && StringUtils.isNotBlank(problem.getProcInsId())).map(TSLBaseRiverLakePatrolEvent::getProcInsId).distinct().collect(Collectors.toList());
        List<HistoricProcessInstance> historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery().processInstanceIds(new HashSet(procInstIdList)).finished().list();


        Map<String, List<TSLBaseRiverLakePatrolEvent>> problemListGroupByStatsType = Maps.newHashMap();
        if (ProblemHandleStatsForm.PROBLEM_TYPE_STATS_TYPE.equals(form.getStatsType())) {
            problemListGroupByStatsType = problemList.stream().filter(problem -> null != problem && StringUtils.isNotBlank(problem.getQuestType())).collect(Collectors.groupingBy(TSLBaseRiverLakePatrolEvent::getQuestType));
        } else if (ProblemHandleStatsForm.AREA_STATS_TYPE.equals(form.getStatsType())) {
            problemListGroupByStatsType = problemList.stream().filter(problem -> null != problem && StringUtils.isNotBlank(problem.getDivisionCode())).collect(Collectors.groupingBy(TSLBaseRiverLakePatrolEvent::getQuestType));
            for (IStatsType statsType : statsTypeList) {
                List<TSLBaseRiverLakePatrolEvent> value = problemList.stream().filter(problem -> null != problem && problem.getAddParentCode().contains(statsType.getCode())).collect(Collectors.toList());
                problemListGroupByStatsType.put(statsType.getCode(), value);
            }
        }

        List<Map<String, Object>> series = Lists.newArrayList();
        for (IStatsType statsType : statsTypeList) {
            String code = statsType.getCode();
            List<TSLBaseRiverLakePatrolEvent> problemListStatsType = null == problemListGroupByStatsType.get(code) ? Lists.newArrayList() : problemListGroupByStatsType.get(code);
             List<String> procInsIdList = problemListStatsType.stream().map(TSLBaseRiverLakePatrolEvent::getProcInsId).collect(Collectors.toList());
            List<HistoricProcessInstance> hisListSameStatsType = historicProcessInstanceList.stream().filter(processInstance -> procInsIdList.contains(processInstance.getId())).collect(Collectors.toList());
            Map<String, Long> hisGroupByDate = hisListSameStatsType.stream().collect(Collectors.groupingBy(his -> DateUtils.formatDate(his.getEndTime(), DateUtils.yyyyMM), Collectors.counting()));

            List<Integer> datas = Lists.newArrayList();
            for (String date : monthList) {
                Long count = null == hisGroupByDate.get(date) ? 0L : hisGroupByDate.get(date);
                datas.add(count.intValue());
            }

            Map<String, Object> serie = Maps.newHashMap();
            serie.put("name", statsType.getName());
            serie.put("data", datas);
            series.add(serie);
        }



        YearProblemHandledStatsDo domain = new YearProblemHandledStatsDo();
        String[] legendData = new String[statsTypeList.size()];
        statsTypeList.stream().map(IStatsType::getName).collect(Collectors.toList()).toArray(legendData);
        domain.setLegendData(legendData);

        String[] xAxisData = new String[monthList.size()];
        monthList.toArray(xAxisData);
        domain.setxAxisData(xAxisData);

        domain.setSeries(series);

        return domain;
    }

    /**
     * 巡河处理完成情况统计
     *
     * @param statsIndexList    统计指标集
     * @param pendingCountMap   待处理数量map（key:统计指标）
     * @param processedCountMap 已处理（结束）数量map（key:统计指标）
     * @return
     */
    private ProblemExecuteStatsDo statsProblemExecute(List<IStatsType> statsIndexList, Map<String, Long> pendingCountMap, Map<String, Long> processedCountMap) {
        Long pendingSum = pendingCountMap.values().stream().reduce(Long::sum).orElse(0L);
        Long processedSum = processedCountMap.values().stream().reduce(Long::sum).orElse(0L);
        Long total = pendingSum + processedSum;

        ProblemExecuteStatsDo domain = new ProblemExecuteStatsDo();
        for (IStatsType statsType : statsIndexList) {
            domain.getLegendData().add(statsType.getName());

            Long pendingCount = !pendingCountMap.containsKey(statsType.getCode()) ? 0L : pendingCountMap.get(statsType.getCode());
            domain.getPendingData().add(pendingCount.intValue());

            Long processedCount = !processedCountMap.containsKey(statsType.getCode()) ? 0L : processedCountMap.get(statsType.getCode());
            domain.getProcessedData().add(processedCount.intValue());

            domain.getCompleteRateData().add((processedCount.doubleValue() / total.doubleValue()) * 100);
        }

        return domain;
    }

    /**
     * 巡河问题占比统计
     *
     * @param statsIndexList  统计指标集
     * @param problemCountMap 问题数量map（key:统计指标）
     * @return
     */
    private ProblemRatioStatsDo statsProblemRatio(List<IStatsType> statsIndexList, Map<String, Long> problemCountMap) {
        ProblemRatioStatsDo domain = new ProblemRatioStatsDo();
        for (IStatsType statsType : statsIndexList) {
            domain.getLegendData().add(statsType.getName());

            Long value = null == problemCountMap.get(statsType.getCode()) ? 0L : problemCountMap.get(statsType.getCode());
            Map<String, Object> data = Maps.newHashMap();
            data.put("name", statsType.getName());
            data.put("value", value.intValue());
            domain.getSeriesData().add(data);
        }
        return domain;
    }

    /**
     * 各指标数量统计
     *
     * @param statsTypeList 统计类型集合（1：按事件类型、2：按行政区划）
     * @param pendingList   待处理的问题集
     * @param processedList 已处理的问题集
     * @return
     */
    private ProblemIndexStatsDo statsProblemIndex(List<IStatsType> statsTypeList, List<TSLBaseRiverLakePatrolEvent> pendingList, List<TSLBaseRiverLakePatrolEvent> processedList) {
        ProblemIndexStatsDo domain = new ProblemIndexStatsDo();
        domain.setStatsTypeTotal(statsTypeList.size());
        domain.setPendingTotal(pendingList.size());
        domain.setProcessedTotal(processedList.size());
        domain.setEventTotal(domain.getPendingTotal() + domain.getProcessedTotal());
        return domain;
    }
}
