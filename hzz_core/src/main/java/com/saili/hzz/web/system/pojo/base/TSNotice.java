package com.saili.hzz.web.system.pojo.base;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.saili.hzz.core.common.entity.IdEntity;

/**
 * 通知公告表
 *  @author  alax
 */
@Entity
@Table(name = "t_s_notice")
@SuppressWarnings("serial")
public class TSNotice extends IdEntity implements java.io.Serializable {
	//private static final long serialVersionUID = 1L;
	private String noticeCode;// 通告编号
	private String noticeTitle;// 通告标题
	private String noticeContent;// 通告内容
	private String noticeType;// 通知公告类型 字典类noticeType
	private String noticeLevel;// 通告授权级别（1:全员，2：角色，3：用户）
	private Date noticeTerm; //阅读期限 现在先不用了
	private String createUser; //创建者
	private Date createTime;  //创建时间
	private String isRead; //是否已读(0:未读，1：已读) 计算用
	
	private String noticeSource;//发布来源
	private String noticeUnit;//发布单位
	private String noticeMemo;//发布备注
	private Date releaseTime;  //发布时间
	
	
	private int lid;
	
	private String appVisible; //是否app首面显示 字典类appVisible
	
	/** 附件 */
	private java.lang.String attachmentDoc;
	/** 标题图片 */
	private java.lang.String titlePicture;
	
	
	@Column(name ="lid",nullable=true)
	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	@Column(name ="notice_code",nullable=true)
	public String getNoticeCode() {
		return noticeCode;
	}

	public void setNoticeCode(String noticeCode) {
		this.noticeCode = noticeCode;
	}

	@Column(name = "notice_source", nullable = true, length = 200)
	public String getNoticeSource() {
		return noticeSource;
	}

	public void setNoticeSource(String noticeSource) {
		this.noticeSource = noticeSource;
	}

	@Column(name = "notice_unit", nullable = true, length = 200)
	public String getNoticeUnit() {
		return noticeUnit;
	}

	public void setNoticeUnit(String noticeUnit) {
		this.noticeUnit = noticeUnit;
	}
	
	@Column(name = "notice_memo", nullable = true, length = 200)
	public String getNoticeMemo() {
		return noticeMemo;
	}

	public void setNoticeMemo(String noticeMemo) {
		this.noticeMemo = noticeMemo;
	}

	@Column(name ="release_time",nullable=true)
	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	@Column(name ="attachment_doc_file",nullable=true)
	public java.lang.String getAttachmentDoc() {
		return attachmentDoc;
	}

	public void setAttachmentDoc(java.lang.String attachmentDoc) {
		this.attachmentDoc = attachmentDoc;
	}

	@Column(name ="title_picture_file",nullable=true)
	public java.lang.String getTitlePicture() {
		return titlePicture;
	}

	public void setTitlePicture(java.lang.String titlePicture) {
		this.titlePicture = titlePicture;
	}

	@Column(name ="notice_title",nullable=true)
	public String getNoticeTitle() {
		return noticeTitle;
	}
	
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	
	@Column(name ="notice_content",nullable=true)
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	
	@Column(name ="notice_type",nullable=true)
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	
	@Column(name ="notice_level",nullable=true)
	public String getNoticeLevel() {
		return noticeLevel;
	}
	public void setNoticeLevel(String noticeLevel) {
		this.noticeLevel = noticeLevel;
	}
	
	@Column(name ="notice_term",nullable=true)
	public Date getNoticeTerm() {
		return noticeTerm;
	}
	public void setNoticeTerm(Date noticeTerm) {
		this.noticeTerm = noticeTerm;
	}
	
	@Column(name ="create_user",nullable=true)
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Column(name ="create_time",nullable=true)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	@Column(name ="app_visible",nullable=true)
	public String getAppVisible() {
		return appVisible;
	}

	public void setAppVisible(String appVisible) {
		this.appVisible = appVisible;
	}
	
	@Transient
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
	
}