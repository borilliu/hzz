package com.saili.hzz.backend.service.impl.base;
import com.saili.hzz.core.dao.mybatisdao.base.RiverLakeChiefDao;
import com.saili.hzz.core.dao.mybatisdao.sys.UserDao;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.backend.service.base.DivisionService;
import com.saili.hzz.backend.service.base.RiverLakeChiefService;
import com.saili.hzz.core.common.model.json.ComboTree;
import com.saili.hzz.core.common.model.json.ValidForm;
import com.saili.hzz.core.common.service.impl.CommonServiceImpl;
import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.util.StringUtil;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import com.saili.hzz.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Service("riverLakeChiefService")
@Transactional(readOnly = true)
public class RiverLakeChiefServiceImpl extends CommonServiceImpl implements RiverLakeChiefService {

	@Autowired
	private RiverLakeChiefDao riverLakeChiefDao;
	@Autowired
	private UserDao userMybatisDao;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private DivisionService divisionService;

	@Override
	public List<RiverLakeChiefEntity> listRiverLakeChiefs(String departId) {
		return riverLakeChiefDao.listRiverLakeChiefs(departId);
	}

	@Override
	@Transactional(readOnly = false)
 	public void delete(RiverLakeChiefEntity entity) throws Exception{
 		super.delete(entity);
 	}

	@Override
	@Transactional(readOnly = false)
 	public Serializable save(RiverLakeChiefEntity entity) throws Exception{
 		// 查询关联的账户
//		entity.setId("8a8ab0b246dc81120146dc8181950052");
		entity.getAccount().setDevFlag("1");
 		Serializable t = super.save(entity);
 		return t;
 	}

	@Override
	@Transactional(readOnly = false)
 	public void saveOrUpdate(RiverLakeChiefEntity entity) throws Exception{
		entity.getAccount().setDevFlag("1");
 		super.saveOrUpdate(entity);
 	}

	@Override
	public RiverLakeChiefEntity getByPhone() {
		return null;
	}

    @Override
    public void checkAccount(String accountName, ValidForm validForm) {
		int count = riverLakeChiefDao.countByAccountName(accountName);
		if(count > 0)
		{
			validForm.setInfo("此用户已被绑定！");
			validForm.setStatus("n");
			return;
		}

		int userCount = userMybatisDao.countByUserName(accountName);
		if (userCount <= 0) {
			validForm.setInfo("系统不存在此用户，请先添加用户！");
			validForm.setStatus("n");
		}
	}

	@Override
	public RiverLakeChiefEntity getByUser(TSUser user) {
		RiverLakeChiefEntity result = null;
		if (null != user && StringUtil.isNotEmpty(user.getId())) {
			result = riverLakeChiefDao.getByUserId(user.getId());
		}
		return result;
	}

	@Override
	public List<ComboTree> listSameLevelRiverTree(String divisionCode) {
		List<ComboTree> results = new ArrayList<ComboTree>();
		List<RiverLakeChiefEntity> riverLakeChiefList = riverLakeChiefDao.listByDivisionCode(divisionCode);
		for (RiverLakeChiefEntity riverLakeChief : riverLakeChiefList) {
			ComboTree tree = new ComboTree();
			tree.setId(riverLakeChief.getId());
			tree.setText(riverLakeChief.getName());
			results.add(tree);
		}
		return results;
	}

	@Override
	public List<ComboTree> listLowerLevelRiverTree(String parentCode) {
		List<ComboTree> trees = new ArrayList<ComboTree>();
		List<RiverLakeChiefEntity> riverLakeChiefList = riverLakeChiefDao.listAll();
        List<TSLDivisionEntity> divisionTree = divisionService.parseDivisionTree(parentCode);
		Map<String, List<RiverLakeChiefEntity>> riverLakeChiefMap = riverLakeChiefList.stream()
				.collect(Collectors.groupingBy(RiverLakeChiefEntity::getDivisionCode));
		for (TSLDivisionEntity division : divisionTree) {
			trees.add(generateRiverDivisionTree(division, riverLakeChiefMap));
		}
		return trees;
	}

    @Override
    public RiverLakeChiefEntity get(String id) {
        return riverLakeChiefDao.get(id);
    }

    @Override
    public List<RiverLakeChiefEntity> listByRoleCode(String roleCode) {
        return riverLakeChiefDao.listByRoleCode(roleCode);
    }

    @Override
    public List<RiverLakeChiefEntity> listByDivision(TSLDivisionEntity division) {
		if (null == division || StringUtils.isBlank(division.getCode())) {
			return null;
		}
        return riverLakeChiefDao.listByDivision(division);
    }

    @Override
    public List<RiverLakeChiefEntity> listByContainsParentDivision(TSLDivisionEntity parentDivision) {
		if (null == parentDivision || StringUtils.isBlank(parentDivision.getCode())) {
			return null;
		}
        return riverLakeChiefDao.listByContainsParentDivision(parentDivision);
    }

    private ComboTree generateRiverDivisionTree(TSLDivisionEntity division, Map<String, List<RiverLakeChiefEntity>> riverLakeChiefMap) {
		ComboTree parent = new ComboTree();
		parent.setId(division.getId());
		parent.setText(division.getName());

		List<RiverLakeChiefEntity> riverLakeChiefList = riverLakeChiefMap.get(division.getCode());
		if (null != riverLakeChiefList && riverLakeChiefList.size() > 0) {
			List<ComboTree> results = new ArrayList<ComboTree>();
			for (RiverLakeChiefEntity riverLakeChief : riverLakeChiefList) {
				parent.setState("closed");
				ComboTree tree = new ComboTree();
				tree.setId(riverLakeChief.getId());
				tree.setText(riverLakeChief.getName());
				results.add(tree);
			}
			parent.setChildren(results);
		}

		if (null != division && null != division.getChildrenList() && division.getChildrenList().size() > 0) {
			List<ComboTree> children = new ArrayList<ComboTree>();
			for (TSLDivisionEntity childernDivision : division.getChildrenList()) {
				ComboTree childern = generateRiverDivisionTree(childernDivision, riverLakeChiefMap);
				children.add(childern);
			}
			parent.setChildren(children);
		}

		return parent;
	}
}