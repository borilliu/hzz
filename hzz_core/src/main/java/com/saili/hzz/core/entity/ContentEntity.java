package com.saili.hzz.core.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 公文管理信息报
 * @author onlineGenerator
 * @date 2019-10-31 10:26:28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sl_content", schema = "")
@SuppressWarnings("serial")
public class ContentEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**内容标题*/
	@Excel(name="内容标题",width=15)
	private String title;
	/**分类名称*/
	@Excel(name="分类名称",width=15,dicCode="content_class_name")
	private Integer groupCode;
	/**发布来源*/
	@Excel(name="发布来源",width=15)
	private String publishSources;
	/**发布时间*/
	@Excel(name="发布时间",width=15,format = "yyyy-MM-dd")
	private Date publishDate;
	/**发布单位编码*/
	@Excel(name="发布单位编码",width=15)
	private String publishOrgCode;
	/**发布单位名称*/
	@Excel(name="发布单位名称",width=15)
	private String publishOrgName;
	/**附件*/
	@Excel(name="附件",width=15)
	private String accessory;
	/**标题图片*/
	@Excel(name="标题图片",width=15)
	private String titleImage;
	/**发布内容*/
	@Excel(name="发布内容",width=15)
	private String publishContent;
	/**备注*/
	@Excel(name="备注",width=15)
	private String remarks;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容标题
	 */

	@Column(name ="TITLE",nullable=false,length=100)
	public String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分类名称
	 */

	@Column(name ="GROUP_CODE",nullable=false)
	public Integer getGroupCode(){
		return this.groupCode;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分类名称
	 */
	public void setGroupCode(Integer groupCode){
		this.groupCode = groupCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发布来源
	 */

	@Column(name ="PUBLISH_SOURCES",nullable=true,length=100)
	public String getPublishSources(){
		return this.publishSources;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发布来源
	 */
	public void setPublishSources(String publishSources){
		this.publishSources = publishSources;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发布时间
	 */

	@Column(name ="PUBLISH_DATE",nullable=false,length=32)
	public Date getPublishDate(){
		return this.publishDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发布时间
	 */
	public void setPublishDate(Date publishDate){
		this.publishDate = publishDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发布单位编码
	 */

	@Column(name ="PUBLISH_ORG_CODE",nullable=true,length=20)
	public String getPublishOrgCode(){
		return this.publishOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发布单位编码
	 */
	public void setPublishOrgCode(String publishOrgCode){
		this.publishOrgCode = publishOrgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发布单位名称
	 */

	@Column(name ="PUBLISH_ORG_NAME",nullable=true,length=50)
	public String getPublishOrgName(){
		return this.publishOrgName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发布单位名称
	 */
	public void setPublishOrgName(String publishOrgName){
		this.publishOrgName = publishOrgName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件
	 */

	@Column(name ="ACCESSORY",nullable=true,length=100)
	public String getAccessory(){
		return this.accessory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件
	 */
	public void setAccessory(String accessory){
		this.accessory = accessory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题图片
	 */

	@Column(name ="TITLE_IMAGE",nullable=true,length=100)
	public String getTitleImage(){
		return this.titleImage;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题图片
	 */
	public void setTitleImage(String titleImage){
		this.titleImage = titleImage;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发布内容
	 */

	@Column(name ="PUBLISH_CONTENT",nullable=true,length=1500)
	public String getPublishContent(){
		return this.publishContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发布内容
	 */
	public void setPublishContent(String publishContent){
		this.publishContent = publishContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */

	@Column(name ="REMARKS",nullable=true,length=200)
	public String getRemarks(){
		return this.remarks;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */

	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
}
