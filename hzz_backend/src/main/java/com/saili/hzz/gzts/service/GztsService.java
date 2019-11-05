package com.saili.hzz.gzts.service;

import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-09-03 14:44:14:44
 * @version: V1.0
 */
public interface GztsService {

    /**
     * 上传文件
     * @author whuab_mc
     * @date 2019-09-03 14:44:53
     * @return
     */
    boolean uploadFile(List<MultipartFile> files, String rootContextPath, TSLBaseRiverLakePatrolEvent event);
}
