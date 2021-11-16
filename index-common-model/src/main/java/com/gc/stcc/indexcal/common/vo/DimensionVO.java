package com.gc.stcc.indexcal.common.vo;

import com.gc.stcc.indexcal.common.model.DimensionValue;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 指标管理 维度
 * 
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Data
public class DimensionVO implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 维度领域
	 */
	private String tags;
	/**
	 * 维度值类型 dictionary-来自字典，table-来自数据库，enmu-来自枚举
	 */
	private String type;
	/**
	 * 数据库列名
	 */
	private String columnName;
	/**
	 * 序号
	 */
	private Integer sort;
	/**
	 * 是否为默认维度
	 */
	private Integer isDefault;

	private List<DimensionValue> dimensionValues;
	/**
	 * 维度ID
	 */
	private String dimensionId;
	/**
	 * 创建表id
	 */
	private String buildId;
	/**
	 * 列名
	 */
	private String columnField;
	/**
	 * 列数据类型
	 */
	private String columnType;
	/**
	 * 列注解
	 */
	private String columnComment;
	/**
	 * 列是否为NULL、NOT NULL
	 */
	private String columnNull;
	/**
	 * enum表id
	 */
	private String enumId;
	/**
	 * enmu全类名
	 */
	private String className;
	/**
	 * redis key名称
	 */
	private String redisKey;
	/**
	 * table表id
	 */
	private String tableId;
	/**
	 * 数据库表名
	 */
	private String tableName;
	/**
	 * 数据库表对应维度名称的列名
	 */
	private String columnDimensionName;
	/**
	 * 数据库表对应维度值的列名
	 */
	private String columnDimensionValue;

}
