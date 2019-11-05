package com.saili.hzz.core.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 公告管理信息表
 * @author onlineGenerator
 * @date 2019-10-30 14:56:05
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sl_notice", schema = "")
@SuppressWarnings("serial")
public class TSLNoticeEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**公告标题*/
	@Excel(name="公告标题",width=15)
	private String title;
	/**公告文号*/
	@Excel(name="公告文号",width=15)
	private String docNo;
	/**分类名称标识*/
	@Excel(name="分类名称标识",width=15,dicCode="notic_class_name")
	private Integer groupCode;
	/**公开形式标识*/
	@Excel(name="公开形式标识",width=15,dicCode="notic_open_way")
	private Integer openWay;
	/**公开范围标识*/
	@Excel(name="公开范围标识",width=15,dicCode="notic_open_scope")
	private Integer openScope;
	/**公开时限标识*/
	@Excel(name="公开时限标识",width=15,dicCode="notice_time_limit")
	private Integer openTimeLimit;
	/**公开程序*/
	@Excel(name="公开程序",width=15)
	private String openProcedure;
	/**发布机构编码*/
	@Excel(name="发布机构编码",width=15)
	private String publishOrgCode;
	/**发布机构名称*/
	@Excel(name="发布机构名称",width=15)
	private String publishOrgName;
	/**发布日期*/
	@Excel(name="发布日期",width=15,format = "yyyy-MM-dd")
	private Date publishDate;
	/**内容描述*/
	@Excel(name="内容描述",width=15)
	private String contentDescribe;
	/**公开内容*/
	@Excel(name="公开内容",width=15)
	private String publishContent;
	/**备注*/
	@Excel(name="备注",width=15)
	private String remarks;
	/**创建人*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人*/
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
	 *@return: java.lang.String  公告标题
	 */

	@Column(name ="TITLE",nullable=true,length=100)
	public String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公告标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公告文号
	 */

	@Column(name ="DOC_NO",nullable=true,length=32)
	public String getDocNo(){
		return this.docNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公告文号
	 */
	public void setDocNo(String docNo){
		this.docNo = docNo;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分类名称标识
	 */

	@Column(name ="GROUP_CODE",nullable=true)
	public Integer getGroupCode(){
		return this.groupCode;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分类名称标识
	 */
	public void setGroupCode(Integer groupCode){
		this.groupCode = groupCode;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  公开形式标识
	 */

	@Column(name ="OPEN_WAY",nullable=false)
	public Integer getOpenWay(){
		return this.openWay;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  公开形式标识
	 */
	public void setOpenWay(Integer openWay){
		this.openWay = openWay;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  公开范围标识
	 */

	@Column(name ="OPEN_SCOPE",nullable=false)
	public Integer getOpenScope(){
		return this.openScope;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  公开范围标识
	 */
	public void setOpenScope(Integer openScope){
		this.openScope = openScope;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  公开时限标识
	 */

	@Column(name ="OPEN_TIME_LIMIT",nullable=false)
	public Integer getOpenTimeLimit(){
		return this.openTimeLimit;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  公开时限标识
	 */
	public void setOpenTimeLimit(Integer openTimeLimit){
		this.openTimeLimit = openTimeLimit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公开程序
	 */

	@Column(name ="OPEN_PROCEDURE",nullable=true,length=100)
	public String getOpenProcedure(){
		return this.openProcedure;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公开程序
	 */
	public void setOpenProcedure(String openProcedure){
		this.openProcedure = openProcedure;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发布机构编码
	 */

	@Column(name ="PUBLISH_ORG_CODE",nullable=true,length=32)
	public String getPublishOrgCode(){
		return this.publishOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发布机构编码
	 */
	public void setPublishOrgCode(String publishOrgCode){
		this.publishOrgCode = publishOrgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发布机构名称
	 */

	@Column(name ="PUBLISH_ORG_NAME",nullable=true,length=50)
	public String getPublishOrgName(){
		return this.publishOrgName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发布机构名称
	 */
	public void setPublishOrgName(String publishOrgName){
		this.publishOrgName = publishOrgName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发布日期
	 */

	@Column(name ="PUBLISH_DATE",nullable=true,length=32)
	public Date getPublishDate(){
		return this.publishDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发布日期
	 */
	public void setPublishDate(Date publishDate){
		this.publishDate = publishDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容描述
	 */

	@Column(name ="CONTENT_DESCRIBE",nullable=true,length=200)
	public String getContentDescribe(){
		return this.contentDescribe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容描述
	 */
	public void setContentDescribe(String contentDescribe){
		this.contentDescribe = contentDescribe;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公开内容
	 */

	@Column(name ="PUBLISH_CONTENT",nullable=true,length=1500)
	public String getPublishContent(){
		return this.publishContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公开内容
	 */
	public void setPublishContent(String publishContent){
		this.publishContent = publishContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */

	@Column(name ="REMARKS",nullable=true,length=255)
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
	 *@return: java.lang.String  创建人
	 */

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
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
	 *@return: java.lang.String  更新人
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人
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
