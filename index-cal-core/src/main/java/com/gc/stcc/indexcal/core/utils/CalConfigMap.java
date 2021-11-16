package com.gc.stcc.indexcal.core.utils;

import com.gc.stcc.indexcal.common.enums.EIntervalType;
import com.gc.stcc.indexcal.common.vo.DimensionDefinitionVo;
import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.Months;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hsd
 * @date 2020/8/7 17:43
 * ------------------------------------
 * @Copyright (C) 2021, 华录高诚
 * @FileName: CalConfigMap
 * @Author: ZhangHeng
 * @Date: 2021/6/30 18:15
 * @Description:
 * @Version: 2.0
 */
@Data
public class CalConfigMap implements Serializable {

    private Date startDate;
    private Date endDate;
    private String intervalId;
    private String intervalName;
    private Date resultDate;
    private boolean isFirstCalculate;
    private DimensionDefinitionVo.DimensionLayerInfoBean dimensionLayerInfoBean;

    @Deprecated
    public CalConfigMap(Date startDate, String intervalId) {
        this(startDate, getNextDateByIntervalId(startDate, intervalId), intervalId);
    }

    public CalConfigMap(Date startDate, Date endDate, String intervalId) {

        this.startDate = startDate;
        this.intervalId = intervalId;
        this.endDate = endDate;
        this.resultDate = this.endDate;

        //修正补值计算截止时间

        EIntervalType intervalType = EIntervalType.getEIntervalTypeEnumByCode(Integer.valueOf(intervalId));

        Calendar from  =  Calendar.getInstance();
        from.setTime(this.startDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(this.endDate);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromWeek = from.get(Calendar.WEEK_OF_YEAR);
        int fromDay = from.get(Calendar.DAY_OF_YEAR);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toWeek = to.get(Calendar.WEEK_OF_YEAR);
        int toDay = to.get(Calendar.DAY_OF_YEAR);

        int floor = 0;

        double fixSupplyInterval = 0.0;

        switch (intervalType) {
            case 五分钟:
                fixSupplyInterval = (this.endDate.getTime() - this.startDate.getTime()) / (5 * 60000.0);
                floor = (int) Math.floor(fixSupplyInterval);
                this.endDate = DateUtil.addMinute(startDate, 5 * floor);
                break;
            case 十五分钟:
                fixSupplyInterval = (this.endDate.getTime() - this.startDate.getTime())/ (15 * 60000.0);
                floor = (int) Math.floor(fixSupplyInterval);
                this.endDate = DateUtil.addMinute(startDate, 15 * floor);
                break;
            case 一小时:
                fixSupplyInterval = (this.endDate.getTime() - this.startDate.getTime())/ (60 * 60000.0);
                floor = (int) Math.floor(fixSupplyInterval);
                this.endDate = DateUtil.addHour(startDate, floor);
                break;
            case 日:
                this.endDate = DateUtil.addDays(startDate, toDay - fromDay);
                break;
            case 周:
                this.endDate = DateUtil.addDays(startDate, 7 * (toWeek - fromWeek));
                break;
            case 月:
            case 季度: //todo
            case 年: //todo
                this.endDate = DateUtil.addMonths(startDate, toMonth - fromMonth);
                break;
            default:
                break;
        }

    }

    @Deprecated
    public static Date getNextDateByIntervalId(Date startDate, String intervalId) {
        Date end = DateUtil.getNow();
        EIntervalType intervalType = EIntervalType.getEIntervalTypeEnumByCode(Integer.valueOf(intervalId));
        switch (intervalType) {
            case 五分钟:
                end = DateUtil.addMinute(startDate, 5);
                break;
            case 十五分钟:
                end = DateUtil.addMinute(startDate, 15);
                break;
            case 一小时:
                end = DateUtil.addHour(startDate, 1);
                break;
            case 日:
                end = DateUtil.addDays(startDate, 1);
                break;
            case 周:
                end = DateUtil.addDays(startDate, 7);
                break;
            case 月:
                end = DateUtil.addMonths(startDate, 1);
                break;
            case 季度:
                end = DateUtil.addMonths(startDate, 3);
                break;
            case 年:
                end = DateUtil.addYear(startDate, 1);
                break;
            default:
                break;
        }
        return end;
    }

    @Override
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "startDate: "+sdf.format(startDate)+"endDate: "+sdf.format(endDate);
    }

}
