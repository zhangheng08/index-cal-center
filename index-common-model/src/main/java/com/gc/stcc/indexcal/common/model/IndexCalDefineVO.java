package com.gc.stcc.indexcal.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 指标计算定义类视图
 *
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Data
public class IndexCalDefineVO implements Serializable {

    /**
     * 自增主键
     */
    private String id;
    /**
     * 创建日期时间
     */
    private Date createDate;
    /**
     * 修改日期时间
     */
    private Date updateDate;
    /**
     * 操作用户id
     */
    private String ownerId;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 指标领域
     */
    private String tags;
    /**
     * 指标描述
     */
    private String description;
    /**
     * 序号
     */
    private Integer sort;
    /**
     * 源数据表名称
     */
    private String originTableName;
    /**
     * 指标结果表名称
     */
    private String resultTableName;
    /**
     * 计算表 ID
     */
    private String calId;
    /**
     * 计算字段名称
     */
    private String calColumnNames;
    /**
     * 计算维度id列表
     */
    private String calDimensionIds;

}
