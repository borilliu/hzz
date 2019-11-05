/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.saili.hzz.act.dao;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.domain.act.Act;

/**
 * 审批DAO接口
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao {

	public int updateProcInsIdByBusinessId(Act act);
	
}
