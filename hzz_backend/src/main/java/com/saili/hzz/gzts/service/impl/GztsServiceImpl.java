package com.saili.hzz.gzts.service.impl;

import com.saili.hzz.core.domain.act.params.ProcessStartParam;
import com.saili.hzz.act.service.problem.ProblemWorkflowService;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEventPhoto;
import com.saili.hzz.gzts.service.GztsService;
import com.saili.hzz.core.constant.Constants;
import com.saili.hzz.core.util.DateUtils;
import com.saili.hzz.core.util.ResourceUtil;
import com.saili.hzz.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/***
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-09-03 14:45:14:45
 * @version: V1.0
 */
@Service
@Transactional(readOnly = true)
public class GztsServiceImpl implements GztsService {

    @Autowired
    private SystemService systemService;
    @Autowired
    private ProblemWorkflowService problemWorkflowService;

    /**
     * 上传文件
     *
     * @return
     * @author whuab_mc
     * @date 2019-09-03 14:44:53
     */
    @Transactional(readOnly = false)
    @Override
    public boolean uploadFile(List<MultipartFile> files, String rootContextPath, TSLBaseRiverLakePatrolEvent event) {
        ProcessStartParam param = new ProcessStartParam();
        param.setProcDefKey(Constants.Activiti.PD_PROBLEM[0]);
        param.setBusinessId(Constants.Activiti.PD_PROBLEM[1]);
        param.setBusinessId(event.getId());
        param.setTitle(event.getName());
        param.setStarter(event.getComplainantPhone());
        param.setBusiness(event);
        try {
//            // 启动处理流程
            problemWorkflowService.applyProcess(param);

            String uploadbasepath = ResourceUtil.getConfigByName("uploadpath");
            // 文件数据库保存路径
            // 文件保存在硬盘的相对路径
            String path = uploadbasepath + File.separatorChar;
            // 文件的硬盘真实路径
            String realPath = rootContextPath + path + DateUtils.getDataString(DateUtils.yyyyMMdd) + File.separatorChar;
            path += DateUtils.getDataString(DateUtils.yyyyMMdd) + File.separatorChar;
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();// 创建文件时间子目录
            }

            for (MultipartFile mf : files) {
                // 拼接上传文件位置
                String newfilePath = realPath + mf.getOriginalFilename();
                String relativePath = path + mf.getOriginalFilename();

                // 创建文件实例
                File uploadFile = new File(newfilePath);

                // 判断文件已经存在，则删除该文件
                if (uploadFile.exists()) {
                    uploadFile.delete();
                }

                // 利于spring中的FileCopyUtils.copy()将文件复制
                FileCopyUtils.copy(mf.getBytes(), uploadFile);

                TSLBaseRiverLakePatrolEventPhoto photo = new TSLBaseRiverLakePatrolEventPhoto();
                photo.setPhoto(relativePath);
                photo.setPhotoDate(new Date());
                photo.setTslBaseRiverLakePatrolEvent(event);
                systemService.save(photo);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
