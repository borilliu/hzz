package com.saili.hzz.core.vo;

import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLBaseRiverLake;
import com.saili.hzz.core.util.StringUtil;
import org.jeecgframework.p3.core.utils.common.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-06 13:17:13:17
 * @version: V1.0
 */
public class HzReportVo {
    private String id;
    private RiverLakeChiefEntity riverChief;
    private List<TSLBaseRiverLake> riverLakeList;

    public HzReportVo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaName() {
        if (null == riverChief || null == riverChief.getDivision()) {
            return null;
        }
        return riverChief.getDivision().getName();
    }

//    public void setAreaName(String areaName) {
//        this.areaName = areaName;
//    }

    public String getRiverChiefName() {
        if (null == riverChief) {
            return null;
        }
        return riverChief.getName();
    }

//    public void setRiverChiefName(String riverChiefName) {
//        this.riverChiefName = riverChiefName;
//    }

    public String getDutyName() {
        if (null == riverChief) {
            return null;
        }
        return riverChief.getDuty();
    }

//    public void setDutyName(String dutyName) {
//        this.dutyName = dutyName;
//    }

//    public String getRiverLakeName() {
//        return riverLakeName;
//    }

    public String getRiverLakeNames() {
        if (null == riverLakeList) {
            return null;
        }
        List<String> riverLakeNameList = riverLakeList.stream()
                .filter(item -> StringUtils.isNotBlank(item.getName()))
                .map(TSLBaseRiverLake::getName)
                .collect(Collectors.toList());
        return StringUtil.joinString(riverLakeNameList, ",");
    }

//    public void setRiverLakeName(String riverLakeName) {
//        this.riverLakeName = riverLakeName;
//    }

//    public String getParentRiverLakeName() {
//        return parentRiverLakeName;
//    }

    public String getParentRiverLakeNames() {
        if (null == riverLakeList) {
            return null;
        }
        List<String> parentRiverLakeNameList = riverLakeList.stream()
                .filter(item -> null != item.getParent())
                .map(TSLBaseRiverLake::getParent)
                .map(TSLBaseRiverLake::getName)
                .collect(Collectors.toList());
        return StringUtil.joinString(parentRiverLakeNameList, ",");
    }

//    public void setParentRiverLakeName(String parentRiverLakeName) {
//        this.parentRiverLakeName = parentRiverLakeName;
//    }

    public String getPhone() {
        if (null == riverChief) {
            return null;
        }
        return riverChief.getPhone();
    }

//    public void setPhone(String phone) {
//        this.phone = phone;
//    }

    public RiverLakeChiefEntity getRiverChief() {
        return riverChief;
    }

    public void setRiverChief(RiverLakeChiefEntity riverChief) {
        this.riverChief = riverChief;
    }

    public List<TSLBaseRiverLake> getRiverLakeList() {
        return riverLakeList;
    }

    public void setRiverLakeList(List<TSLBaseRiverLake> riverLakeList) {
        this.riverLakeList = riverLakeList;
    }
}
