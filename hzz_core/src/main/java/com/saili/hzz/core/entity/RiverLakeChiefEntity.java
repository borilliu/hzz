package com.saili.hzz.core.entity;

import java.lang.String;
import javax.persistence.*;

import com.saili.hzz.core.enums.DivisionLevelEnum;
import com.saili.hzz.core.enums.HzTypeEnum;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.saili.hzz.web.system.pojo.base.TSDepart;
import com.saili.hzz.web.system.pojo.base.TSUser;

/**   
 * @Title: Entity
 * @Description: 河湖长名录
 * @author onlineGenerator
 * @date 2019-06-26 10:05:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sl_rl_chief", schema = "")
@SuppressWarnings("serial")
public class RiverLakeChiefEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**河长账户*/
	@Excel(name="河长账户",width=15)
	@Column(name = "USER_ID")
	private TSUser account;
	/**河长姓名*/
	@Excel(name="河长姓名",width=15)
	private java.lang.String name;
	/**河长级别*/
	@Excel(name="河长级别",width=15)
	private TSLUserTypeEntity level;
	/**河长类型*/
	@Excel(name="河长类型",width=15)
	private String type;
	/**行政区划*/
	@Excel(name="行政区划",width=15)
	private TSLDivisionEntity division;
	/**河长部门*/
	@Excel(name="河长部门",width=15)
	private TSDepart depart;
	/**河长职位*/
	@Excel(name="河长职位",width=15)
	private java.lang.String duty;
	/**手机号码*/
	@Excel(name="手机号码",width=15)
	private java.lang.String phone;
	/**邮箱*/
	@Excel(name="邮箱",width=15)
	private java.lang.String email;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;

	@Transient
	public String getDivisionLevelCode() {
		if (null == division || StringUtils.isBlank(division.getParentCodes())) {
			return null;
		}
		int parentCout = division.getParentCodes().split(",").length;
		return DivisionLevelEnum.getCodeByParentCount(parentCout);
	}

	@Transient
	public String getDivisionCode() {
		if (null == division) {
			return null;
		}
		return division.getCode();
	}

	@Transient
	public String typeCode() {
		if (null == type) {
			return null;
		}
		return type;
	}

	@Transient
	public String getTypeName(){
		if (StringUtils.isNotBlank(type)) {
			HzTypeEnum hzTypeEnum = HzTypeEnum.getByCode(type);
			if (null == hzTypeEnum) {
				return null;
			}
			return hzTypeEnum.getName();
		}
		return null;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  河长账户
	 */

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID",referencedColumnName = "id")
	public TSUser getAccount(){
		return this.account;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  河长账户
	 */
	public void setAccount(TSUser account){
		this.account = account;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  河长姓名
	 */

	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  河长姓名
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  河长级别
	 */

//	@Column(name ="LEVEL",nullable=true,length=32)
	@ManyToOne
	@JoinColumn(name = "LEVEL", referencedColumnName = "code")
	public TSLUserTypeEntity getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  河长级别
	 */
	public void setLevel(TSLUserTypeEntity level){
		this.level = level;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  河长类型
	 */

	@Column(name ="TYPE",nullable=true,length=32)
	public String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  河长类型
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  行政区划
	 */

	@OneToOne
	@JoinColumn(name = "DIVISION_CODE", referencedColumnName = "code")
	public TSLDivisionEntity getDivision(){
		return this.division;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  行政区划
	 */
	public void setDivision(TSLDivisionEntity division){
		this.division = division;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  河长部门
	 */

	@OneToOne
	@JoinColumn(name ="DEPART_ID", referencedColumnName = "id")
	public TSDepart getDepart(){
		return this.depart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  河长部门
	 */
	public void setDepart(TSDepart depart){
		this.depart = depart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  河长职位
	 */

	@Column(name ="DUTY",nullable=true,length=32)
	public java.lang.String getDuty(){
		return this.duty;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  河长职位
	 */
	public void setDuty(java.lang.String duty){
		this.duty = duty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号码
	 */

	@Column(name ="PHONE",nullable=true,length=32)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号码
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮箱
	 */

	@Column(name ="EMAIL",nullable=true,length=32)
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮箱
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name="CREATE_BY",nullable=true,length=50)
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
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name="UPDATE_BY",nullable=true,length=50)
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
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
}
