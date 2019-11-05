package com.saili.hzz.backend.controller.statistical;

import com.saili.hzz.backend.service.statistical.PatrolReportService;
import org.apache.commons.lang.StringUtils;
import com.saili.hzz.core.common.model.json.ValidForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/patrolReportController")
public class PatrolReportController {
    @Autowired
    private PatrolReportService patrolReportService;

    /**
     * 检查巡河报表记录唯一性
     * @param divisionCode 行政区划编码
     * @param yearMonth 年月
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkRecordUniqueness")
    @ResponseBody
    public ValidForm checkRecordUniqueness(String divisionCode, String yearMonth, HttpServletRequest request) {
        ValidForm v = new ValidForm();
        v.setInfo("验证通过！");
        v.setStatus("y");

        if (StringUtils.isEmpty(divisionCode)) {
            v.setInfo("请选择行政区划");
            v.setStatus("n");
            return v;
        }

        if (StringUtils.isEmpty(yearMonth)) {
            v.setInfo("请选择年月");
            v.setStatus("n");
            return v;
        }

        int count = patrolReportService.countByRecordUniqueness(divisionCode, yearMonth);

        if(count > 0)
        {
            v.setInfo("巡河报表记录已存在");
            v.setStatus("n");
            return v;
        }

        return v;
    }
}
