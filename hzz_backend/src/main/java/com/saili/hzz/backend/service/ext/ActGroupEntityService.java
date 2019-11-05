/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.saili.hzz.backend.service.ext;

import com.google.common.collect.Lists;
import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.backend.service.base.RiverLakeChiefService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import com.saili.hzz.core.util.ApplicationContextUtil;
import com.saili.hzz.web.system.pojo.base.TSRole;
import com.saili.hzz.web.system.pojo.base.TSRoleUser;
import com.saili.hzz.web.system.pojo.base.TSUser;
import com.saili.hzz.web.system.service.SystemService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Activiti Group Entity Service
 *
 * @author ThinkGem
 * @version 2013-12-05
 */
@Service
public class ActGroupEntityService extends GroupEntityManager {

    private SystemService systemService;
    private RiverLakeChiefService riverLakeChiefService;

    public SystemService getSystemService() {
        if (systemService == null) {
            systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        }
        return systemService;
    }

    public RiverLakeChiefService getRiverLakeChiefService() {
        if (riverLakeChiefService == null){
            riverLakeChiefService = ApplicationContextUtil.getContext().getBean(RiverLakeChiefService.class);
        }
        return riverLakeChiefService;
    }

    @Override
    public Group createNewGroup(String groupId) {
        return new GroupEntity(groupId);
    }

    @Override
    public void insertGroup(Group group) {
        throw new RuntimeException("not implement method.");
    }

    public void updateGroup(GroupEntity updatedGroup) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public void deleteGroup(String groupId) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public GroupQuery createNewGroupQuery() {
        throw new RuntimeException("not implement method.");
    }

    //	@SuppressWarnings("unchecked")
    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        List<Group> list = Lists.newArrayList();
        try {
                RiverLakeChiefEntity riverLakeChief = getRiverLakeChiefService().get(userId);
                TSUser user = getSystemService().findUniqueByProperty(TSUser.class, "id", riverLakeChief.getAccount().getId());
                if (null == user) {
                    return list;
                }

                List<TSRoleUser> rUsers = getSystemService().findByProperty(TSRoleUser.class, "TSUser.id", riverLakeChief.getAccount().getId());
                List<TSRole> roles = rUsers.stream().map(TSRoleUser::getTSRole).collect(Collectors.toList());
                if (roles != null && roles.size() > 0) {
                    for (TSRole role : roles) {
                        if (role == null) {
                            return null;
                        }
                        GroupEntity groupEntity = new GroupEntity();
                        groupEntity.setId(role.getRoleCode());
                        groupEntity.setName(role.getRoleName());
                        groupEntity.setType(role.getRoleType());
                        groupEntity.setRevision(1);
                        list.add(groupEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new RuntimeException("not implement method.");
    }

}