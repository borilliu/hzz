package com.saili.hzz.core.common.dao;


import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.saili.hzz.tag.vo.easyui.ComboTreeModel;
import com.saili.hzz.tag.vo.easyui.TreeGridModel;
import com.saili.hzz.web.system.pojo.base.TSDepart;
import com.saili.hzz.web.system.pojo.base.TSRole;
import com.saili.hzz.web.system.pojo.base.TSUser;
import com.saili.hzz.core.common.model.common.UploadFile;
import com.saili.hzz.core.common.model.json.ComboTree;
import com.saili.hzz.core.common.model.json.ImportFile;
import com.saili.hzz.core.common.model.json.TreeGrid;
import com.saili.hzz.core.extend.template.Template;

public interface ICommonDao extends IGenericBaseCommonDao{
	
	
	/**
	 * admin账户密码初始化
	 * @param user
	 */
	public void pwdInit(TSUser user, String newPwd);
	/**
	 * 检查用户是否存在
	 * */
	public TSUser getUserByUserIdAndUserNameExits(TSUser user);
	public TSUser findUserByAccountAndPassword(String username,String password);
	public String getUserRole(TSUser user);
	/**
	 * 获取用户的角色列表
	 * @author whuab_mc
	 * @date 2019-08-19 10:11:19
	 * @param user
	 * @return
	 */
	public List<TSRole> listRole(TSUser user);
	/**
	 * 文件上传
	 * @param request
	 */
	public <T> T  uploadFile(UploadFile uploadFile);
	/**
	 * 文件上传或预览
	 * @param uploadFile
	 * @return
	 */
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);

	public Map<Object,Object> getDataSourceMap(Template template);
	/**
	 * 生成XML文件
	 * @param fileName XML全路径
	 */
	public HttpServletResponse createXml(ImportFile importFile);
	/**
	 * 解析XML文件
	 * @param fileName XML全路径
	 */
	public void parserXml(String fileName);
	public List<ComboTree> comTree(List<TSDepart> all, ComboTree comboTree);

	/**
     * 根据模型生成ComboTree JSON
     *
     * @param all 全部对象
     * @param comboTreeModel 模型
     * @param in 已拥有的对象
     * @param recursive 是否递归加载所有子节点
     * @return List<ComboTree>
     */
	public  List<ComboTree> ComboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive);

	public  List<TreeGrid> treegrid(List<?> all, TreeGridModel treeGridModel);
}

