package com.saili.hzz.backend.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.saili.hzz.core.dao.mybatisdao.base.DivisionDao;
import com.saili.hzz.core.domain.DivisionLevelAreaCountDo;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.backend.service.base.DivisionService;
import com.saili.hzz.core.common.hibernate.qbc.CriteriaQuery;
import com.saili.hzz.core.util.StringUtil;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import com.saili.hzz.tag.vo.datatable.SortDirection;
import com.saili.hzz.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-08-28 13:17:13:17
 * @version: V1.0
 */
@Service
public class DivisionServiceImpl implements DivisionService {
    @Autowired
    private DivisionDao divisionDao;
    @Autowired
    private SystemService systemService;

    @Override
    public List<TSLDivisionEntity> listById(String id) {
        return divisionDao.listTreeById(id);
    }

    @Override
    public List<TSLDivisionEntity> listBycriteriaWay(String id, String pid) {
        CriteriaQuery cq = new CriteriaQuery(TSLDivisionEntity.class);
        if(null != id){
            cq.notEq("id", id);
        }
        if (pid != null) {
            cq.eq("parent.id", pid);
        }
        if (pid == null) {
            cq.isNull("parent");
        }
        cq.addOrder("sort", SortDirection.asc);
        cq.add();
        return systemService.getListByCriteriaQuery(cq, false);
    }

    @Override
    public List<TSLDivisionEntity> listAll() {
        return divisionDao.listAll();
    }

    /**
     * @方法名: parseMenuTree<br>
     * @描述: 组装菜单<br>
     * @return
     */
    @Override
    public List<TSLDivisionEntity> parseDivisionTree(String parentCode){
        List<TSLDivisionEntity> list = listAll();
        List<TSLDivisionEntity> result = new ArrayList<TSLDivisionEntity>();

        // 1、获取第一级节点
        for (TSLDivisionEntity division : list) {
            if(StringUtil.equals(parentCode, division.getCode())) {
                result.add(division);
            }
        }

        // 2、递归获取子节点
        for (TSLDivisionEntity parent : result) {
            parent = recursiveTree(parent, list);
        }

        return result;
    }

    /**
     * 解析行政区划树结构
     * @param rootCode
     * @param divisionList
     * @return
     */
    @Override
    public TSLDivisionEntity parseDivisionTree(String rootCode, List<TSLDivisionEntity> divisionList){
        TSLDivisionEntity root = new TSLDivisionEntity();
        // 1、获取指定的根级节点
        for (TSLDivisionEntity division : divisionList) {
            if(StringUtil.equals(rootCode, division.getCode())) {
                root = division;
                break;
            }
        }
        return recursiveTree(root, divisionList);
    }

    private TSLDivisionEntity recursiveTree(TSLDivisionEntity parent, List<TSLDivisionEntity> list) {
        for (TSLDivisionEntity division : list) {
            if(StringUtil.equals(parent.getCode(), division.getParentCode())) {
                division = recursiveTree(division, list);
                parent.getChildrenList().add(division);
            }
        }

        return parent;
    }

    @Override
    public List<TSLDivisionEntity> listByParentCode(String parentCode) {
        if (StringUtils.isBlank(parentCode)) {
            return Lists.newArrayList();
        }
        return divisionDao.listByParentCode(parentCode);
    }

    /**
     * 包含指定的地区编码的地区集
     *
     * @param code
     * @return
     */
    @Override
    public List<TSLDivisionEntity> listContainParentCode(String code) {
        if (StringUtils.isBlank(code)) {
            return Lists.newArrayList();
        }
        return divisionDao.listContainParentCode(code);
    }

    @Override
    public Map<String, DivisionLevelAreaCountDo> divisionLevelStats(String parentCode) {
        Map<String, DivisionLevelAreaCountDo> results = Maps.newHashMap();
        List<TSLDivisionEntity> childernDivisionList = listByParentCode(parentCode);
        List<TSLDivisionEntity> allChildernDivisionList = listContainParentCode(parentCode);
        for (TSLDivisionEntity childernDivision : childernDivisionList) {
            String childernDivisionCode = childernDivision.getCode();
            Map<String, Long> divisionLevelMap = allChildernDivisionList.stream().filter(division -> division.getCode().equals(childernDivisionCode) || division.getParentCodes().contains(childernDivisionCode)).filter(division -> StringUtils.isNotBlank(division.getDivisionLevelCode())).collect(Collectors.groupingBy(TSLDivisionEntity::getDivisionLevelCode, Collectors.counting()));
            DivisionLevelAreaCountDo domain = new DivisionLevelAreaCountDo(divisionLevelMap);
            results.put(childernDivisionCode, domain);
        }
        return results;
    }

    @Override
    public TSLDivisionEntity getByCode(String code) {
        return divisionDao.getByCode(code);
    }
}
