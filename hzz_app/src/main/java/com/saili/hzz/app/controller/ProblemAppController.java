package com.saili.hzz.app.controller;

import com.saili.hzz.core.domain.act.params.ProcessStartParam;
import com.saili.hzz.act.service.problem.ProblemWorkflowService;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import com.saili.hzz.core.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * @Title: 问题交办 controller
 * @Description: app接口 处理问题交办
 * @author: whuab_mc
 * @date: 2019-08-16 10:47:10:47
 * @version: V1.0
 */
@Controller
@RequestMapping("/app/problem/")
public class ProblemAppController {
    @Autowired
    private ProblemWorkflowService problemWorkflowService;

    /**
     *  提交问题
     * @author whuab_mc
     * @date 2019-08-22 15:07:40
     * @param problem
     * @return
     */
    @RequestMapping("doSubmitProblem")
    @ResponseBody
    public void doSubmitProblem(TSLBaseRiverLakePatrolEvent problem) {
        ProcessStartParam param = new ProcessStartParam();
        param.setProcDefKey(Constants.Activiti.PD_PROBLEM[0]);
        param.setBusinessId(Constants.Activiti.PD_PROBLEM[1]);
        param.setBusinessId(problem.getId());
        param.setTitle(problem.getName());
        param.setStarter(problem.getTsSubmitUser().getId());
        param.setBusiness(problem);
        problemWorkflowService.applyProcess(param);
    }
}
