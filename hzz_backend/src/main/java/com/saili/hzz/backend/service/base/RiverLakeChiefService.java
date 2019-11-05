package com.saili.hzz.backend.service.base;
import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.common.model.json.ComboTree;
import com.saili.hzz.core.common.model.json.ValidForm;
import com.saili.hzz.core.common.service.CommonService;
import com.saili.hzz.web.system.pojo.base.TSUser;

import java.io.Serializable;
import java.util.List;

public interface RiverLakeChiefService extends CommonService{

	List<RiverLakeChiefEntity> listRiverLakeChiefs(String departId);

 	void delete(RiverLakeChiefEntity entity) throws Exception;
 	
 	Serializable save(RiverLakeChiefEntity entity) throws Exception;
 	
 	void saveOrUpdate(RiverLakeChiefEntity entity) throws Exception;

 	RiverLakeChiefEntity getByPhone();

	/**
	 * 检查账户信息
	 * @param accountName
	 * @param validForm
	 */
    void checkAccount(String accountName, ValidForm validForm);

	/**
	 * 根据账号获取河长信息
	 * @author whuab_mc
	 * @date 2019-08-29 14:01:14
	 * @return
	 */
	RiverLakeChiefEntity getByUser(TSUser user);

	/**
	 * 获取同级河长树
	 * @author whuab_mc
	 * @date 2019-08-29 14:20:02
	 * @param divisionCode
	 * @return
	 */
	List<ComboTree> listSameLevelRiverTree(String divisionCode);

    /**
     * 获取下级河长树
     * @author whuab_mc
     * @date 2019-08-29 14:19:24
     * @param parentCode
     * @return
     */
	List<ComboTree> listLowerLevelRiverTree(String parentCode);

	/**
	 * 根据id获取河长信息
	 * @param id
	 * @return
	 */
	RiverLakeChiefEntity get(String id);

	/**
	 * 根据角色编码查询河长集
	 * @param roleCode 角色编码
	 * @return
	 */
	List<RiverLakeChiefEntity> listByRoleCode(String roleCode);

	/**
	 * 根据行政区划查询河长集
	 * @param division
	 * @return
	 */
    List<RiverLakeChiefEntity> listByDivision(TSLDivisionEntity division);

	/**
	 * 查询父级包含指定行政区划河长集
	 * @param division
	 * @return
	 */
	List<RiverLakeChiefEntity> listByContainsParentDivision(TSLDivisionEntity division);
}
