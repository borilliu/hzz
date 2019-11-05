package com.saili.hzz.web.black.service;
import com.saili.hzz.web.black.entity.TsBlackListEntity;
import com.saili.hzz.core.common.service.CommonService;

import java.io.Serializable;

public interface TsBlackListServiceI extends CommonService{
	
 	public void delete(TsBlackListEntity entity) throws Exception;
 	
 	public Serializable save(TsBlackListEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TsBlackListEntity entity) throws Exception;
 	
}
