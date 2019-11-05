package com.saili.hzz.web.system.service;

import java.util.List;

import com.saili.hzz.web.system.pojo.base.DynamicDataSourceEntity;

public interface DynamicDataSourceServiceI{

	public List<DynamicDataSourceEntity> initDynamicDataSource();

	public void refleshCache();


	public DynamicDataSourceEntity getDynamicDataSourceEntityForDbKey(String dbKey);


}
