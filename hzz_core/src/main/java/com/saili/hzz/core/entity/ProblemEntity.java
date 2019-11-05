package com.saili.hzz.core.entity;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.saili.hzz.core.common.entity.BaseEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.saili.hzz.web.system.pojo.base.TSType;
import com.saili.hzz.web.system.pojo.base.TSUser;

/**   
 * @Title: Entity
 * @Description: 问题信息表
 * @author onlineGenerator
 * @date 2019-06-17 16:10:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sl_problem", schema = "")
@SuppressWarnings("serial")
public class ProblemEntity extends BaseEntity {
	/**主键*/
	private java.lang.String id;
	/**问题编号*/
	@Excel(name="问题编号",width=15)
	private java.lang.String code;
	/**问题类型*/
	@Excel(name="问题类型",width=15)
	private java.lang.String type;
	/**地区编号*/
	@Excel(name="地区编号",width=15)
	private java.lang.String divisionCode;
	/**相关河长*/
	@Excel(name="相关河长",width=15)
	private java.lang.String riverChief;
	/**相关河段*/
	@Excel(name="相关河段",width=15)
	private java.lang.String reach;
	/**地址*/
	@Excel(name="地址",width=15)
	private java.lang.String address;
	/**上报时间*/
	@Excel(name="上报时间",width=15,format = "yyyy-MM-dd")
	private java.util.Date reportTime;
	/**上报事件*/
	@Excel(name="上报事件",width=15)
	private java.lang.String event;
	/**问题上报图片*/
	@Excel(name="问题上报图片",width=15)
	private java.lang.String photo;

	private TSUser user;
	private TSType problemDict;
//	private TSType eventDict;
	private TSLDivisionEntity division;

	@NotFound(action= NotFoundAction.IGNORE)
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "river_chief",referencedColumnName = "id")
	public TSUser getUser() {
		return user;
	}

	public void setUser(TSUser user) {
		this.user = user;
	}

	@NotFound(action= NotFoundAction.IGNORE)
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type",referencedColumnName = "typecode")
	public TSType getProblemDict() {
		return problemDict;
	}

	public void setProblemDict(TSType problemDict) {
		this.problemDict = problemDict;
	}

	/*@NotFound(action= NotFoundAction.IGNORE)
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event",referencedColumnName = "typecode")
	public TSType getEventDict() {
		return eventDict;
	}

	public void setEventDict(TSType eventDict) {
		this.eventDict = eventDict;
	}*/

	@NotFound(action= NotFoundAction.IGNORE)
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "division_code",referencedColumnName = "code")
	public TSLDivisionEntity getDivision() {
		return division;
	}

	public void setDivision(TSLDivisionEntity division) {
		this.division = division;
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
	 *@return: java.lang.String  问题编号
	 */

	@Column(name ="CODE",nullable=false,length=100)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  问题编号
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  问题类型
	 */

	@Column(name ="TYPE",nullable=false,length=100)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  问题类型
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地区编号
	 */

	@Column(name ="DIVISION_CODE",nullable=true,length=50)
	public java.lang.String getDivisionCode(){
		return this.divisionCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地区编号
	 */
	public void setDivisionCode(java.lang.String divisionCode){
		this.divisionCode = divisionCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  相关河长
	 */

	@Column(name ="RIVER_CHIEF",nullable=true,length=50)
	public java.lang.String getRiverChief(){
		return this.riverChief;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  相关河长
	 */
	public void setRiverChief(java.lang.String riverChief){
		this.riverChief = riverChief;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  相关河段
	 */

	@Column(name ="REACH",nullable=true,length=100)
	public java.lang.String getReach(){
		return this.reach;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  相关河段
	 */
	public void setReach(java.lang.String reach){
		this.reach = reach;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址
	 */

	@Column(name ="ADDRESS",nullable=true,length=100)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  上报时间
	 */

	@Column(name ="REPORT_TIME",nullable=false,length=32)
	public java.util.Date getReportTime(){
		return this.reportTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  上报时间
	 */
	public void setReportTime(java.util.Date reportTime){
		this.reportTime = reportTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上报事件
	 */

	@Column(name ="EVENT",nullable=true,length=100)
	public java.lang.String getEvent(){
		return this.event;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上报事件
	 */
	public void setEvent(java.lang.String event){
		this.event = event;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  问题上报图片
	 */

	@Column(name ="PHOTO",nullable=true,length=500)
	public java.lang.String getPhoto(){
		return this.photo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  问题上报图片
	 */
	public void setPhoto(java.lang.String photo){
		this.photo = photo;
	}
}
