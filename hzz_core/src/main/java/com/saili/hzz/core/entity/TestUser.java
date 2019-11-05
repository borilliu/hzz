package com.saili.hzz.core.entity;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 系统用户表
 *  @author  liuby
 */
public class TestUser extends TestBaseUser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String signatureFile;// 签名文件
	@Excel(name = "手机" ,width = 20)
	private String mobilePhone;// 手机
	@Excel(name = "办公电话",width = 20)
	private String officePhone;// 办公电话
	@Excel(name = "邮箱",width = 25)
	private String email;// 邮箱

	/*
	private TSLDivision TSLDivision;//行政区划

	@NotFound(action=NotFoundAction.IGNORE)
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "divisionid")
	public TSLDivision getTSLDivision() {
		return this.TSLDivision;
	}

	public void setTSLDivision(TSLDivision TSLDivision) {
		this.TSLDivision = TSLDivision;
	}*/

	/**头像*/
	private String portrait;
	/**开发权限标志*/
	private String devFlag;

	private String userType;//用户类型  1:系统用户 \2接口用户
	private String personType;//人员类型
	private String sex;//性别
	private String empNo;//工号
	private String citizenNo;//身份证号
	private String fax;//传真
	private String address;//联系地址
	private String post;//邮编
	private String memo;//备注

	@Excel(name = "排位顺序",width = 25)
	private String userOrderBy; //排位顺序

	public String getUserOrderBy() {
		return userOrderBy;
	}

	public void setUserOrderBy(String userOrderBy) {
		this.userOrderBy = userOrderBy;
	}

	private String userDutys; //用户职务

	public String getUserDutys() {
		return userDutys;
	}

	public void setUserDutys(String userDutys) {
		this.userDutys = userDutys;
	}

	/**创建时间*/
	private java.util.Date createDate;
	/**创建人ID*/
	private String createBy;
	/**创建人名称*/
	private String createName;
	/**修改时间*/
	private java.util.Date updateDate;
	/**修改人*/
	private String updateBy;
	/**修改人名称*/
	private String updateName;

	public String getDevFlag() {
		return devFlag;
	}

	public void setDevFlag(String devFlag) {
		this.devFlag = devFlag;
	}
	public String getSignatureFile() {
		return this.signatureFile;
	}

	public void setSignatureFile(String signatureFile) {
		this.signatureFile = signatureFile;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人ID
	 */
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人ID
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人ID
	 */
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人ID
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人名称
	 */
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人名称
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getCitizenNo() {
		return citizenNo;
	}

	public void setCitizenNo(String citizenNo) {
		this.citizenNo = citizenNo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TSUser [signatureFile=");
		builder.append(signatureFile);
		builder.append(", mobilePhone=");
		builder.append(mobilePhone);
		builder.append(", officePhone=");
		builder.append(officePhone);
		builder.append(", email=");
		builder.append(email);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", createName=");
		builder.append(createName);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", updateName=");
		builder.append(updateName);
		builder.append(", portrait=");
		builder.append(portrait);
		builder.append(", devFlag=");
		builder.append(devFlag);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", personType=");
		builder.append(personType);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", empNo=");
		builder.append(empNo);
		builder.append(", citizenNo=");
		builder.append(citizenNo);
		builder.append(", fax=");
		builder.append(fax);
		builder.append(", address=");
		builder.append(address);
		builder.append(", post=");
		builder.append(post);
		builder.append(", memo=");
		builder.append(memo);
		builder.append("]");
		return builder.toString();
	}
}