package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: IndexManageCalModel
 * Author:  Payne08
 * Date: 2021/6/7 17:19
 * Description: index_manage_cal_model
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 * @author Payne08
 */

@Data
@TableName("index_manage_cal_model")
public class IndexManageCalModel {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("index_code")
    private String indexCode;
    @TableField("fact_table")
    private String factTable;
    @TableField("dim_build_ids")
    private String dimBuildIds;
    @TableField("create_time")
    private Date createTime;
    @TableField("tenant_id")
    private String tenantId;
    @TableField("agg_method")
    private String aggMethod;
    @TableField("agg_field")
    private String aggField;

}
