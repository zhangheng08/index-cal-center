package com.gc.stcc.indexcal.sz.distcongest.entiy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: OdsYthjcSysDistrictY
 * @Author: ZhangHeng
 * @Date: 2021/7/26 9:42
 * @Description:
 * @Version:
 */
@TableName("ods_ythjc_sys_district_y")
public class OdsYthjcSysDistrictY {

    @TableField("dist_id")
    private String distId;
    @TableField("dist_name")
    private String distName;
    @TableField("dist_type")
    private String distType;

}
