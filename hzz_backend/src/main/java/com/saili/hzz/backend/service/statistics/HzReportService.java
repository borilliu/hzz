package com.saili.hzz.backend.service.statistics;


import com.saili.hzz.core.vo.HzReportVo;

import java.util.List;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-06 14:27:14:27
 * @version: V1.0
 */
public interface HzReportService {
    /**
     * 根据 地区code 获取 HzReportVo
     * @param code
     * @return
     */
    List<HzReportVo> listByDivisionCode(String code);
}
