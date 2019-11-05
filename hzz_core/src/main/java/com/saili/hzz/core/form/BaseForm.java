package com.saili.hzz.core.form;

import com.saili.hzz.core.entity.TSLDivisionEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***
 * 查询条件 基类
 * @author: whuab_mc
 * @date: 2019-09-11 11:35:11:35
 * @version: V1.0
 */
public class BaseForm {
    /**
     * 省
     */
    private TSLDivisionEntity province;
    /**
     * 市
     */
    private TSLDivisionEntity city;
    /**
     * 县
     */
    private TSLDivisionEntity county;

    /**
     * 开始日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDay;
    /**
     * 结束日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDay;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @DateTimeFormat(pattern = "yyyy-MM")
    private Date month;

    /**
     * 往前推一年
     */
    public Date getLastYearStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 年份减
        calendar.add(Calendar.YEAR, -1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 当年的开始时间
     */
    public Date getCurrentYearStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 年份减
        calendar.set(Calendar.MONTH, calendar.getActualMinimum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        return calendar.getTime();
    }

    /**
     * 当月的开始时间
     */
    public Date getCurrentMonthStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 年份减
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        return calendar.getTime();
    }

    public TSLDivisionEntity getProvince() {
        return province;
    }

    public void setProvince(TSLDivisionEntity province) {
        this.province = province;
    }

    public TSLDivisionEntity getCity() {
        return city;
    }

    public void setCity(TSLDivisionEntity city) {
        this.city = city;
    }

    public TSLDivisionEntity getCounty() {
        return county;
    }

    public void setCounty(TSLDivisionEntity county) {
        this.county = county;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public Date getStartTime() {
        if (null != startTime) {
            return startTime;
        }

        if (null != month) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(month);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
            return calendar.getTime();
        }

        if (null != startDay) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDay);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
            return calendar.getTime();
        }
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        if (null != endTime) {
            return endTime;
        }

        if (null != month) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(month);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
            return calendar.getTime();
        }

        if (null != endDay) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDay);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
            return calendar.getTime();
        }
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
