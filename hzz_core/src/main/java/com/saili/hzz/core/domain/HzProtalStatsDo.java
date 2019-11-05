package com.saili.hzz.core.domain;

import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.util.StringUtil;

import java.util.List;

/***
 * 河长巡河统计 do
 * @author: whuab_mc
 * @date: 2019-09-17 11:25:11:25
 * @version: V1.0
 */
public class HzProtalStatsDo {
    /**
     * 流程id
     */
    private String procInsId;
    /**
     * 地区编码
     */
    private String divisionCode;
    /**
     * 地区
     */
    private TSLDivisionEntity division;
    /**
     * 月份
     */
    private String month;
    /**
     * 河长集
     */
    private List<RiverLakeChiefEntity> hzList;

    public HzProtalStatsDo() {
    }

    public String keyAreaMonth() {
        if (StringUtil.isEmpty(divisionCode) || StringUtil.isEmpty(month)) {
            return null;
        }
        return divisionCode + "_" + month;
    }

    public String keyProcInsIdAreaMonth() {
        if (StringUtil.isEmpty(procInsId) || StringUtil.isEmpty(divisionCode) || StringUtil.isEmpty(month)) {
            return null;
        }
        return procInsId + "_" + divisionCode + "_" + month;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public TSLDivisionEntity getDivision() {
        return division;
    }

    public void setDivision(TSLDivisionEntity division) {
        this.division = division;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<RiverLakeChiefEntity> getHzList() {
        return hzList;
    }

    public void setHzList(List<RiverLakeChiefEntity> hzList) {
        this.hzList = hzList;
    }
}
