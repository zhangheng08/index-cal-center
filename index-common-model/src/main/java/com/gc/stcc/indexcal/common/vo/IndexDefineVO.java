package com.gc.stcc.indexcal.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 指标定义类视图
 *
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Data
public class IndexDefineVO implements Serializable {

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
     * 查询表 Id
     */
    private String queryId;
    /**
     * 指标ID
     */
    private String indexId;
    /**
     * 源数据表名称
     */
    private String originTableName;
    /**
     * 指标结果表名称
     */
    private String resultTableName;
    /**
     * 字段名称
     */
    private String columnNames;
    /**
     * 维度id列表
     */
    private String dimensionIds;
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
    /**
     * 创建表 ID
     */
    private String buildId;
    /**
     * 额外补充的sql
     */
    private String extraSql;

}
