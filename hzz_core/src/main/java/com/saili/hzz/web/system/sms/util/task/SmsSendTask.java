package com.saili.hzz.web.system.sms.util.task;

import com.saili.hzz.core.util.LogUtil;
import com.saili.hzz.web.system.sms.service.TSSmsServiceI;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 
 * @ClassName:SmsSendTask 
 * @Description: 消息推送定时任务
 * @date 2014-11-13 下午5:06:34
 * 
 */
@Service("smsSendTask")
public class SmsSendTask implements Job{
	
	@Autowired
	private TSSmsServiceI tSSmsService; 
	
	public void run() {
		long start = System.currentTimeMillis();
		LogUtil.info("===================推送消息定时任务开始===================");
		try {			
			tSSmsService.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogUtil.info("===================推送消息定时任务结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		LogUtil.info("===================推送消息定时任务总耗时"+times+"毫秒===================");
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		run();
	}
}
