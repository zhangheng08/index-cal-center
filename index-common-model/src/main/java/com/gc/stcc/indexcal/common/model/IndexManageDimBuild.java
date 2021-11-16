package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: IndexManageDimBuild
 * Author:  Payne08
 * Date: 2021/6/7 17:27
 * Description: index_manage_dim_build
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 * @author Payne08
 */
@Data
@TableName("index_manage_dim_build")
public class IndexManageDimBuild {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("dim_build_code")
    private String dimBuildCode;
    @TableField("dim_id")
    private Integer dimId;
    @TableField("dim_table")
    private String dimTable;
    @TableField("dim_level")
    private Integer dimLevel;
    @TableField("dim_parent_level")
    private Integer dimParentLevel;
    @TableField("dim_column")
    private String dimColumn;
    @TableField("dim_row_id")
    private Integer dimRowId;
    @TableField("dim_value")
    private String dimValue;
    @TableField("extend_columns")
    private String extendColumns;

}
