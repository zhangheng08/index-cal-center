package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @author Payne08
 */
@Data
@TableName("index_manage_dimension")
public class IndexManageDimension extends Model<IndexManageDimension> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("create_date")
    private Date createDate;

    @TableField("update_date")
    private Date updateDate;

    @TableField("name")
    private String name;

    @TableField("column_name")
    private String columnName;

    @TableField("is_default")
    private short isDefault;

    @TableField("ref_id")
    private String refId;

    @TableField("table_name")
    private String tableName;

    @TableField("column_type")
    private String columnType;

    @TableField("ref_dim_ids")
    private String refDimIds;

}
