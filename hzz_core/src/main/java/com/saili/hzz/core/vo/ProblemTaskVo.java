package com.saili.hzz.core.vo;

import com.saili.hzz.core.domain.act.problem.ProblemAct;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;

/***
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-09-06 14:05:14:05
 * @version: V1.0
 */
public class ProblemTaskVo {
    /***
     * 问题工作流
     */
    private ProblemAct problemAct;
    /***
     * 问题详情
     */
    private TSLBaseRiverLakePatrolEvent problem;

    public ProblemTaskVo() {
    }

    public ProblemTaskVo(ProblemAct problemAct, TSLBaseRiverLakePatrolEvent problem) {
        this.problemAct = problemAct;
        this.problem = problem;
    }

    public ProblemAct getProblemAct() {
        return problemAct;
    }

    public void setProblemAct(ProblemAct problemAct) {
        this.problemAct = problemAct;
    }

    public TSLBaseRiverLakePatrolEvent getProblem() {
        return problem;
    }

    public void setProblem(TSLBaseRiverLakePatrolEvent problem) {
        this.problem = problem;
    }
}
