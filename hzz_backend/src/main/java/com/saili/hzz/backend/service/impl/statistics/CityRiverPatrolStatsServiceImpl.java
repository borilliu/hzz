package com.saili.hzz.backend.service.impl.statistics;

import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.form.statistics.StatsForm;
import com.saili.hzz.backend.service.base.DivisionService;
import com.saili.hzz.core.vo.HzProtalStatsVo;
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
@Service(value = "cityRiverPatrolStatsService")
@Transactional(readOnly = true)
public class CityRiverPatrolStatsServiceImpl extends RiverPatrolStatsServiceImpl {
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
        return divisionService.listByParentCode(form.getProvince().getCode());
    }

    @Override
    public Map<String, List<RiverLakeChiefEntity>> getStandMap(List<RiverLakeChiefEntity> hzList) {
        return hzList.stream().filter(entity -> null != entity.getId()).collect(Collectors.groupingBy(RiverLakeChiefEntity::getId));
    }

    @Override
    public void setStandName(HzProtalStatsVo vo, List<RiverLakeChiefEntity> hzList) {
        String anme = null != hzList && hzList.size() > 0 ? hzList.get(0).getName() : null;
        vo.setStandName(anme);
    }
}
