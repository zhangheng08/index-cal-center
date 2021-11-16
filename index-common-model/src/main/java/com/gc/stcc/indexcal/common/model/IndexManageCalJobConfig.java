package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Payne08
 */
@Data
@TableName("index_manage_index_for_cal")
public class IndexManageCalJobConfig {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("index_code")
    private String indexCode;
    @TableField("fact_table_name")
    private String factTableName;
    @TableField("tenant_id")
    private String tenantId;
    @TableField("dim_config")
    private String dimConfig;

}
