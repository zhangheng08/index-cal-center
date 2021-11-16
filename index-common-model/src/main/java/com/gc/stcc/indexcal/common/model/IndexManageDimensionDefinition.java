package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: IndexManageDimensionDefinition
 * @Author: ZhangHeng
 * @Date: 2021/6/30 10:27
 * @Description:
 * @Version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("index_manage_dimension_definition")
public class IndexManageDimensionDefinition {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 维度定义具体配置信息，以json表示
     */
    @TableField("definition_json")
    private String definitionJson;
    /**
     * insert time
     */
    @TableField("create_date")
    private Date createDate;

}
