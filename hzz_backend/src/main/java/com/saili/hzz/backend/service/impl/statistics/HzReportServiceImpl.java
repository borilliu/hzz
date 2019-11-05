package com.saili.hzz.backend.service.impl.statistics;

import com.saili.hzz.core.dao.mybatisdao.statistical.HzReportDao;
import com.saili.hzz.backend.service.statistics.HzReportService;
import com.saili.hzz.core.vo.HzReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-06 14:27:14:27
 * @version: V1.0
 */
@Service
public class HzReportServiceImpl implements HzReportService {
    @Autowired
    private HzReportDao dao;
    /**
     * 根据 地区code 获取 HzReportVo
     *
     * @param code
     * @return
     */
    @Override
    public List<HzReportVo> listByDivisionCode(String code) {
        return dao.listByDivisionCode(code);
    }
}
