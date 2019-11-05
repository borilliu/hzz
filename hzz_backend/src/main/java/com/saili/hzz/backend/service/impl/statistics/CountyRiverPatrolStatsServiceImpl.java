package com.saili.hzz.backend.service.impl.statistics;

import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.form.statistics.StatsForm;
import com.saili.hzz.backend.service.base.DivisionService;
import com.saili.hzz.core.vo.HzProtalStatsVo;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 *
 * @author: whuab_mc
 * @date: 2019-09-24 11:32:11:32
 * @version: V1.0
 */
@Service(value = "countyRiverPatrolStatsService")
@Transactional(readOnly = true)
public class CountyRiverPatrolStatsServiceImpl extends RiverPatrolStatsServiceImpl {
    @Autowired
    private DivisionService divisionService;
    /**
     * 获取所有地区
     *
     * @param form
     * @return
     */
    @Override
    public List<TSLDivisionEntity> listAreaAll(StatsForm form) {
        return divisionService.listByParentCode(form.getCity().getCode());
    }

    @Override
    public Map<String, List<RiverLakeChiefEntity>> getStandMap(List<RiverLakeChiefEntity> hzList) {
        return hzList.stream().filter(entity -> null != entity.typeCode()).collect(Collectors.groupingBy(RiverLakeChiefEntity::typeCode));
    }

    @Override
    public void setStandName(HzProtalStatsVo vo, List<RiverLakeChiefEntity> hzList) {
        if (hzList != null && hzList.size() > 0 && StringUtils.isNotBlank(hzList.get(0).getTypeName())) {
            vo.setStandName(hzList.get(0).getTypeName());
        } else {
            vo.setStandName(null);
        }
    }
}
