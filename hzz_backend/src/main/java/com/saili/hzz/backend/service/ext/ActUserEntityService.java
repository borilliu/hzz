/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.saili.hzz.backend.service.ext;

import com.google.common.collect.Lists;
import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.backend.service.base.RiverLakeChiefService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
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
 * Activiti User Entity Service
 * @author whuab_mc
 * @version 2019-07-17
 */
@Service
public class ActUserEntityService extends UserEntityManager {

	private SystemService systemService;
	private RiverLakeChiefService riverLakeChiefService;

	public SystemService getSystemService() {
		if (systemService == null){
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
    public User createNewUser(String userId) {
		return new UserEntity(userId);
	}

	@Override
    public void insertUser(User user) {
		throw new RuntimeException("not implement method.");
	}

	public void updateUser(UserEntity updatedUser) {
		throw new RuntimeException("not implement method.");
	}

	@Override
    public UserEntity findUserById(String userId) {
		UserEntity result = null;
		RiverLakeChiefEntity riverLakeChief = getRiverLakeChiefService().get(userId);
        try {
			TSUser user = getSystemService().findUniqueByProperty(TSUser.class, "id", riverLakeChief.getAccount().getId());
			if (user == null){
				return null;
			}

			result = new UserEntity();
			result.setId(user.getUserName());
			result.setFirstName(user.getRealName());
			result.setLastName("");
			result.setPassword(user.getPassword());
			result.setEmail(user.getEmail());
			result.setRevision(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return result;
	}

	@Override
    public void deleteUser(String userId) {
		User user = findUserById(userId);
		if (user != null) {
            TSUser user1 = new TSUser();
            user1.setId(user.getId());
			getSystemService().delete(user1);
		}
	}

	@Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
		throw new RuntimeException("not implement method.");
	}

	@Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
		throw new RuntimeException("not implement method.");
	}

	@Override
    public List<Group> findGroupsByUser(String userId) {
		List<Group> list = Lists.newArrayList();
		RiverLakeChiefEntity riverLakeChief = getRiverLakeChiefService().get(userId);
        List<TSRoleUser> rUsers = getSystemService().findByProperty(TSRoleUser.class, "TSUser.id", riverLakeChief.getAccount().getId());
        List<TSRole> roles = rUsers.stream().map(TSRoleUser::getTSRole).collect(Collectors.toList());
		for (TSRole role : roles){
            if (role == null){
                return null;
            }
            GroupEntity groupEntity = new GroupEntity();
            groupEntity.setId(role.getRoleName());
            groupEntity.setName(role.getRoleName());
            groupEntity.setType(role.getRoleType());
            groupEntity.setRevision(1);
			list.add(groupEntity);
		}
		return list;
	}

	@Override
    public UserQuery createNewUserQuery() {
		throw new RuntimeException("not implement method.");
	}

	@Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
		throw new RuntimeException("not implement method.");
	}

	@Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
		throw new RuntimeException("not implement method.");
	}

	@Override
    public Boolean checkPassword(String userId, String password) {
		throw new RuntimeException("not implement method.");
	}

	@Override
    public List<User> findPotentialStarterUsers(String proceDefId) {
		throw new RuntimeException("not implement method.");

	}

	@Override
    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
		throw new RuntimeException("not implement method.");
	}

	@Override
    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
		throw new RuntimeException("not implement method.");
	}
	
}
