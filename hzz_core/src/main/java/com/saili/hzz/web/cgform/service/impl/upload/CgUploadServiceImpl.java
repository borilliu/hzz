package com.saili.hzz.web.cgform.service.impl.upload;

import java.util.Map;

import com.saili.hzz.web.cgform.dao.upload.CgFormUploadDao;
import com.saili.hzz.web.cgform.entity.upload.CgUploadEntity;
import com.saili.hzz.core.common.service.impl.CommonServiceImpl;
import com.saili.hzz.core.util.ContextHolderUtils;
import com.saili.hzz.core.util.FileUtils;
import com.saili.hzz.core.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.saili.hzz.web.cgform.service.upload.CgUploadServiceI;
import com.saili.hzz.web.system.pojo.base.TSAttachment;
@Service("cgUploadService")
@Transactional
public class CgUploadServiceImpl extends CommonServiceImpl implements CgUploadServiceI {
	@Autowired
	private CgFormUploadDao cgFormUploadDao;
	
	public void deleteFile(CgUploadEntity file) {
		//step.1 删除附件
		String sql = "select * from t_s_attachment where id = ?";
		Map<String, Object> attachmentMap = commonDao.findOneForJdbc(sql, file.getId());
		//附件相对路径
		String realpath = (String) attachmentMap.get("realpath");
		String fileName = FileUtils.getFilePrefix2(realpath);
		
		//获取部署项目绝对地址
		String realPath = ContextHolderUtils.getSession().getServletContext().getRealPath("/");
		FileUtils.delete(realPath+realpath);
		FileUtils.delete(realPath+fileName+".pdf");
		FileUtils.delete(realPath+fileName+".swf");
		//step.2 删除数据
		commonDao.delete(file);
	}

	
	public void writeBack(String cgFormId,String cgFormName,String cgFormField,String fileId,String fileUrl) {
		try{
			if ("t_sl_base_river_lake_patrol_event_photo".equals(cgFormName)||"t_sl_base_river_lake_patrol_event_photo1".equals(cgFormName)){
				
			}else{
				cgFormUploadDao.updateBackFileInfo(cgFormId, cgFormName, cgFormField, fileId, fileUrl);	
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件上传失败："+e.getMessage());
		}
	}

	@Override
	public void deleteAttachment(TSAttachment attachment) {
		String realpath = attachment.getRealpath();//附件相对路径
		String pathNosuffix = FileUtils.getFilePrefix2(realpath);
		//获取部署项目绝对地址
		String projectPath = ContextHolderUtils.getSession().getServletContext().getRealPath("/");
		//step.1 删除附件
		FileUtils.delete(projectPath+realpath);
		if(!FileUtils.isPicture(attachment.getExtend())){
			//文件类型删除预览生成的文件
			FileUtils.delete(projectPath+pathNosuffix+".pdf");
			FileUtils.delete(projectPath+pathNosuffix+".swf");
		}
		//step.2 删除数据
		commonDao.delete(attachment);
	}

	@Override
	public void updateCgFormFile(String cgFormId, String tableName, String attachments) throws Exception{
		JSONArray array = JSONArray.parseArray(attachments);
		String addSql = "INSERT INTO cgform_uploadfiles (id,CGFORM_FIELD,CGFORM_ID,CGFORM_NAME) VALUES(?,?,?,?)";
		String delSql = "delete from cgform_uploadfiles where id = ?";
		for (Object object : array) {
			JSONObject json = (JSONObject)object;
			String cgFormField = json.getString("cgFormField");//获取表字段
			String attachmentId = json.getString("attachment");//获取附件ID(逗号隔开的字符串)
			if(oConvertUtils.isNotEmpty(attachmentId)){
				String attachmentArray[] = attachmentId.split(",");
				for (String a : attachmentArray) {
					if(oConvertUtils.isNotEmpty(a)){
						if(a.endsWith("_D")){
							//以_D结尾 代表你是被删除掉了哦
							String metaId = a.substring(0,a.length()-2);
							//删除cgform_uploadfiles
							commonDao.executeSql(delSql,metaId);
							//删除附件表同时删除文件
							TSAttachment attachment = this.getEntity(TSAttachment.class,metaId);
							deleteAttachment(attachment);
						}else if(a.endsWith("_O")){
							//String metaId = a.substring(0,a.length()-2);//获取原始ID
							//以_O结尾 代表你历史文件 那么需要走更新操作,但是没有需要更新的内容
						}else{
							commonDao.executeSql(addSql, a,cgFormField,cgFormId,tableName);
						}
					}
				}
			}
		}
	}

}
