package com.saili.hzz.core.dao.mybatisdao.base;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author whuab_mc
 */
@MyBatisDao
public interface RiverLakeChiefDao {

    /**
     * 根据部门id查询河湖长名录
     *
     * @param departId
     * @return
     */
    List<RiverLakeChiefEntity> listRiverLakeChiefs(@Param("departId") String departId);

    /**
     * 根据账户名称查询河湖长名录记录数
     *
     * @param accountName
     * @return
     */
    int countByAccountName(@Param("userName") String accountName);

    /**
     * 查询所有河长
     * @author whuab_mc
     * @date 2019-08-28 16:41:19
     * @return
     */
    List<RiverLakeChiefEntity> listAll();

    /**
     * 根据账号获取河长信息
     * @author whuab_mc
     * @date 2019-08-29 14:02:10
     * @param userId 账户
     * @return
     */
    RiverLakeChiefEntity getByUserId(@Param("userId") String userId);

    /**
     * 根据区划编码查询河长信息集
     * @author whuab_mc
     * @date 2019-08-29 14:37:42
     * @param divisionCode 区域编码
     * @return
     */
    List<RiverLakeChiefEntity> listByDivisionCode(@Param("divisionCode") String divisionCode);

    RiverLakeChiefEntity get(String id);

    /**
     * 根据角色编码查询河长集
     * @param roleCode 角色编码
     * @return
     */
    List<RiverLakeChiefEntity> listByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 根据行政区划查询河长集
     * @param division
     * @return
     */
    List<RiverLakeChiefEntity> listByDivision(@Param("division") TSLDivisionEntity division);

    /**
     * 查询包含指定父级的河长
     * @param parentDivision
     * @return
     */
    List<RiverLakeChiefEntity> listByContainsParentDivision(@Param("parentDivision") TSLDivisionEntity parentDivision);
}
