package com.saili.hzz.web.cgform.service.fillrule;
import com.saili.hzz.web.cgform.entity.fillrule.TSFillRuleEntity;
import com.saili.hzz.core.common.service.CommonService;

import java.io.Serializable;

public interface TSFillRuleServiceI extends CommonService{
	
 	public void delete(TSFillRuleEntity entity) throws Exception;
 	
 	public Serializable save(TSFillRuleEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TSFillRuleEntity entity) throws Exception;
 	
}
