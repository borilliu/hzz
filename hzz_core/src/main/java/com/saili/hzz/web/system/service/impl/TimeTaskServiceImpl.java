package com.saili.hzz.web.system.service.impl;

import com.saili.hzz.web.system.service.TimeTaskServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saili.hzz.core.common.service.impl.CommonServiceImpl;

@Service("timeTaskService")
@Transactional
public class TimeTaskServiceImpl extends CommonServiceImpl implements TimeTaskServiceI {
	
}