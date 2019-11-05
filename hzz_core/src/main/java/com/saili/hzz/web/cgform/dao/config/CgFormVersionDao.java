package com.saili.hzz.web.cgform.dao.config;

import com.saili.hzz.web.cgform.entity.config.CgFormHeadEntity;

import org.jeecgframework.minidao.annotation.Arguments;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Title:CgFormFieldDao
 * @description:
 * @author liuby
 * @date Aug 24, 2013 11:33:33 AM
 * @version V1.0
 */
@Repository("cgFormVersionDao")
public interface CgFormVersionDao {
	@Arguments("tableName")
	public String  getCgFormVersionByTableName(String tableName);
	@Arguments("id")
	public String  getCgFormVersionById(String id);
	@Arguments({"newVersion","formId"})
	public void  updateVersion(String newVersion,String formId);
	
	@Arguments({"id"})
	public CgFormHeadEntity  getCgFormById(String id);
}
