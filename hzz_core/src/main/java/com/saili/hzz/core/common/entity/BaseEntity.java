package com.saili.hzz.core.common.entity;

import com.saili.hzz.core.util.ResourceUtil;
import com.saili.hzz.web.system.pojo.base.TSUser;
import com.saili.hzz.core.extend.swftools.ConStant;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class BaseEntity extends IdEntity {
    /**问题描述*/
    @Excel(name="问题描述",width=15)
    public java.lang.String remarks;
    /**创建人id*/
    public java.lang.String createBy;
    /**创建日期*/
    public java.util.Date createDate;
    /**更新人id*/
    public java.lang.String updateBy;
    /**更新日期*/
    public java.util.Date updateDate;

    /**
     * 再添加之前需要执行的操作
     */
    public void beforeAdd() {
        Date currDate = new Date();
        setCreateDate(currDate);
        setUpdateDate(currDate);
        TSUser user = ResourceUtil.getSessionUser();
        if (user == null) {
            setCreateBy(ConStant.USER_ADMIN_ID);
            setUpdateBy(ConStant.USER_ADMIN_ID);
        } else {
            setCreateBy(user.getId());
            setUpdateBy(user.getId());
        }
    }

    /**
     * 再更新之前需要执行的操作
     */
    public void beforeUpdate() {
        Date currDate = new Date();
        setUpdateDate(currDate);
        TSUser user = ResourceUtil.getSessionUser();
        if (user == null) {
            setUpdateBy(ConStant.USER_ADMIN_ID);
        } else {
            setUpdateBy(user.getId());
        }
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  问题描述
     */

    @Column(name ="REMARKS",nullable=true,length=500)
    public java.lang.String getRemarks(){
        return this.remarks;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  问题描述
     */
    public void setRemarks(java.lang.String remarks){
        this.remarks = remarks;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  创建人id
     */

    @Column(name ="CREATE_BY",nullable=false,length=50)
    public java.lang.String getCreateBy(){
        return this.createBy;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  创建人id
     */
    public void setCreateBy(java.lang.String createBy){
        this.createBy = createBy;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  创建日期
     */

    @Column(name ="CREATE_DATE",nullable=false,length=20)
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
     *@return: java.lang.String  更新人id
     */

    @Column(name ="UPDATE_BY",nullable=false,length=50)
    public java.lang.String getUpdateBy(){
        return this.updateBy;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  更新人id
     */
    public void setUpdateBy(java.lang.String updateBy){
        this.updateBy = updateBy;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  更新日期
     */

    @Column(name ="UPDATE_DATE",nullable=false,length=20)
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
