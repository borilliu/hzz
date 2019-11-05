package com.saili.hzz.act.service.problem;

import com.saili.hzz.core.domain.act.Act;
import com.saili.hzz.core.domain.act.params.ProcessStartParam;
import com.saili.hzz.core.domain.act.problem.ProblemAct;
import com.saili.hzz.core.vo.ProblemTaskVo;

import java.util.List;

/***
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-09-05 15:17:15:17
 * @version: V1.0
 */
public interface ProblemWorkflowService {

    /**
     * 申请流程
     * @author whuab_mc
     * @date 2019-08-06 13:12:53
     * @param param
     * @return
     */
    void applyProcess(ProcessStartParam param);

    /**
     * 根据流程处理节点，获取页面名称
     * @author whuab_mc
     * @date 2019-08-21 11:07:30
     * @param act
     * @return
     */
    String getViewName(Act act);

    /**
     *  查询待办任务
     * @author whuab_mc
     * @date 2019-09-05 15:21:23
     * @param userName
     * @return
     */
    List<ProblemAct> findTodoTasks(String userName);

    /**
     *  查询待办任务
     * @author whuab_mc
     * @date 2019-09-05 15:21:23
     * @param userName
     * @return
     */
    List<ProblemTaskVo> listTodoProblem(String userName);
}
