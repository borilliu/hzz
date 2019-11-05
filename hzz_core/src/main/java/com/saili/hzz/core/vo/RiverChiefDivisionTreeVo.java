package com.saili.hzz.core.vo;

import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.common.entity.IdEntity;

import java.util.List;

/***
 * @Title: 地区河长树 vo
 * @Description:
 * @author: whuab_mc
 * @date: 2019-08-28 09:56:9:56
 * @version: V1.0
 */
public class RiverChiefDivisionTreeVo extends IdEntity {
    /***
     * 区划编码
     */
    private String divisionCode;
    /***
     * 区划名称
     */
    private String divisionName;
    /***
     * 上级区划编码
     */
    private String parentDivisionCode;
    /***
     * 行政区划
     */
    private TSLDivisionEntity division;
    /***
     * 下级地区河长树
     */
    private List<RiverChiefDivisionTreeVo> childernList;
    /***
     * 当前地区河长集
     */
    private List<RiverLakeChiefEntity> riverLakeChiefList;

    public RiverChiefDivisionTreeVo() {
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getParentDivisionCode() {
        return parentDivisionCode;
    }

    public void setParentDivisionCode(String parentDivisionCode) {
        this.parentDivisionCode = parentDivisionCode;
    }

    public TSLDivisionEntity getDivision() {
        return division;
    }

    public void setDivision(TSLDivisionEntity division) {
        this.division = division;
    }

    public List<RiverChiefDivisionTreeVo> getChildernList() {
        return childernList;
    }

    public void setChildernList(List<RiverChiefDivisionTreeVo> childernList) {
        this.childernList = childernList;
    }

    public List<RiverLakeChiefEntity> getRiverLakeChiefList() {
        return riverLakeChiefList;
    }

    public void setRiverLakeChiefList(List<RiverLakeChiefEntity> riverLakeChiefList) {
        this.riverLakeChiefList = riverLakeChiefList;
    }
}
