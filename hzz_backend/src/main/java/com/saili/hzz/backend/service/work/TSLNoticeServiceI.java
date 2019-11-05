package com.saili.hzz.backend.service.work;
import com.saili.hzz.core.entity.TSLNoticeEntity;
import com.saili.hzz.core.common.service.CommonService;

import java.io.Serializable;

public interface TSLNoticeServiceI extends CommonService{
	
 	public void delete(TSLNoticeEntity entity) throws Exception;
 	
 	public Serializable save(TSLNoticeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TSLNoticeEntity entity) throws Exception;
 	
}
