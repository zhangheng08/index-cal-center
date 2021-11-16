package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 指标管理 维度 数据库类型配置
 * 
 * @author hsd
 * @date 2020-07-21 10:53:17
 */
@Data
@TableName("index_manage_dimension_build_config")
public class IndexManageDimensionBuildConfigEntity implements Serializable {

	public static final String COLUMN_NULL = "NULL";
	public static final String COLUMN_NOT_NULL = "NOT_NULL";

	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId(type = IdType.AUTO)
	private String id;
	/**
	 * 维度ID
	 */
	private String dimensionId;
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

}
