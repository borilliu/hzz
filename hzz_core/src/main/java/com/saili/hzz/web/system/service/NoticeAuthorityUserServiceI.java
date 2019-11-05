package com.saili.hzz.web.system.service;
import java.io.Serializable;

import com.saili.hzz.web.system.pojo.base.TSNoticeAuthorityUser;
import com.saili.hzz.core.common.service.CommonService;

public interface NoticeAuthorityUserServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSNoticeAuthorityUser t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSNoticeAuthorityUser t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSNoticeAuthorityUser t);
 	
 	public boolean checkAuthorityUser(String noticeId, String userid);
 	
 	public void saveNoticeAuthorityUser(TSNoticeAuthorityUser noticeAuthorityUser);
 	
 	public void doDelNoticeAuthorityUser(TSNoticeAuthorityUser noticeAuthorityUser);
}
