package com.gc.stcc.indexcal.sz.distcongest.entiy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: OdsInternetDistCongestY
 * @Author: ZhangHeng
 * @Date: 2021/6/16 12:08
 * @Description:
 * @Version:
 */
@Data
@TableName("ODS_YTHJC_INTERNET_DIST_CONGEST_Y")
public class OdsInternetDistCongestY {

    @TableField("create_time")
    private Date createTime;
    @TableField("dist_id")
    private String distId;
    @TableField("road_type")
    private String roadType;
    @TableField("internal_id")
    private String internalId;
    @TableField("congest_index")
    private Double  congestIndex;
    @TableField("congest_length1")
    private Double congestLength1;
    @TableField("congest_length2")
    private Double congestLength2;
    @TableField("congest_length3")
    private Double congestLength3;
    @TableField("congest_length4")
    private Double congestLength4;
    @TableField("road_speed")
    private Double roadSpeed;
    @TableField("city_index_with")
    private Double cityIndexWith;
    @TableField("city_index_without")
    private Double cityIndexWithout;
    @TableField("city_index_diff")
    private Double cityIndexDiff;
    @TableField("insert_time")
    private Date insertTime;


}
