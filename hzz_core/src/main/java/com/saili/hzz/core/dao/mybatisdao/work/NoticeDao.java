package com.saili.hzz.core.dao.mybatisdao.work;

import com.saili.hzz.core.annotation.MyBatisDao;

/**
 * 通知
 *
 * @author whuab_mc
 */
@MyBatisDao
public interface NoticeDao {

    int countByNoticeCode(String noticeCode);
}
