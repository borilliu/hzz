package com.saili.hzz.backend.service.impl.event;

import com.saili.hzz.core.dao.mybatisdao.event.RiverPatrolProcessHandledDao;
import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.RiverPatrolProcessHandledEntity;
import com.saili.hzz.core.form.statistics.StatsForm;
import com.saili.hzz.backend.service.base.RiverLakeChiefService;
import com.saili.hzz.backend.service.event.RiverPatrolProcessHandledService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import com.saili.hzz.core.util.ResourceUtil;
import com.saili.hzz.core.util.UUIDGenerator;
import com.saili.hzz.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/***
 *
 * @author: whuab_mc
 * @date: 2019-09-17 14:59:14:59
 * @version: V1.0
 */
@Service
public class RiverPatrolProcessHandledServiceImpl implements RiverPatrolProcessHandledService {
    @Autowired
    private RiverPatrolProcessHandledDao dao;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RiverLakeChiefService riverLakeChiefService;

    @Override
    public List<RiverPatrolProcessHandledEntity> list(StatsForm form) {
        return dao.list(form);
    }

    /**
     * 保存
     *
     * @param handleDetail 处理详情
     */
    @Override
    public int save(String taskId, String handleDetail) {
        TSUser user = ResourceUtil.getSessionUser();
        RiverLakeChiefEntity riverLakeChief = riverLakeChiefService.getByUser(user);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        RiverPatrolProcessHandledEntity entity = new RiverPatrolProcessHandledEntity();
        entity.setId(UUIDGenerator.generate());
        entity.setTaskDefKey(task.getTaskDefinitionKey());
        entity.setProcInstId(task.getProcessInstanceId());
        entity.setRiverLakeChief(riverLakeChief);
        entity.setHandleDate(new Date());
        entity.setCreateBy(user.getId());
        entity.setCreateDate(entity.getHandleDate());
        entity.setHandleDetail(handleDetail);
        return dao.save(entity);
    }
}
