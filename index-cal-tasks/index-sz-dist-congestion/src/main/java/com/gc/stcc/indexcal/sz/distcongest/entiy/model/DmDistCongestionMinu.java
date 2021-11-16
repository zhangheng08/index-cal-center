package com.gc.stcc.indexcal.sz.distcongest.entiy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DmDistCongestionMinu
 * @Author: ZhangHeng
 * @Date: 2021/6/16 16:20
 * @Description:
 * @Version:
 */
@Data
@TableName("dm_ythjc_dist_congestion_minu_index_cal_test")
public class DmDistCongestionMinu {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("dist_id")
    private String distId;
    @TableField("dist_name")
    private String distName;
    @TableField("CONGEST_INDEX")
    private Double congestIndex;
    @TableField("CONGEST_INDEX_CSY")
    private Double congestIndexCsy;
    @TableField("CONGEST_INDEX_CSM")
    private Double congestIndexCsm;
    @TableField("SPEED_AVG")
    private Double speedAvg;
    @TableField("SPEED_AVG_CSY")
    private Double speedAvgCsy;
    @TableField("SPEED_AVG_CSM")
    private Double speedAvgCsm;
    @TableField("CONGEST_DURATION")
    private Integer congestDuration;
    @TableField("CONGEST_MILEAGE")
    private Double congestMileage;
    @TableField("CREATE_TIME")
    private Date createTime;
    @TableField("UPDATE_TIME")
    private Date updateTime;
    @TableField("TIME_INTERVAL_ID")
    private Integer timeIntervalId;
    @TableField("END_TIME")
    private Date endTime;

}
