package com.saili.hzz.web.system.service;
import java.io.Serializable;

import com.saili.hzz.web.system.enums.InterfaceEnum;
import com.saili.hzz.web.system.pojo.base.InterfaceRuleDto;
import com.saili.hzz.web.system.pojo.base.TSInterfaceEntity;
import com.saili.hzz.core.common.service.CommonService;

public interface TSInterfaceServiceI extends CommonService{
	
 	public void delete(TSInterfaceEntity entity) throws Exception;
 	
 	public Serializable save(TSInterfaceEntity entity ) throws Exception;
 	
 	public void saveOrUpdate( TSInterfaceEntity entity) throws Exception;
 	
 	public InterfaceRuleDto getInterfaceRuleByUserNameAndCode(String userName, InterfaceEnum interfaceEnum);
 	
}
