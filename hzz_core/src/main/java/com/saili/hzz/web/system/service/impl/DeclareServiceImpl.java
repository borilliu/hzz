package com.saili.hzz.web.system.service.impl;

import java.util.List;

import com.saili.hzz.web.system.pojo.base.TSAttachment;
import com.saili.hzz.web.system.service.DeclareService;

import com.saili.hzz.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("declareService")
@Transactional
public class DeclareServiceImpl extends CommonServiceImpl implements DeclareService {

	public List<TSAttachment> getAttachmentsByCode(String businessKey, String description)
	{

		String hql="from TSAttachment t where t.businessKey= ? and t.description = ?";
		return commonDao.findHql(hql,businessKey,description);

	}
	
}
