package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * 指标管理 数据库维度
 * 
 * @author hsd
 * @date 2020-07-20 14:55:43
 */
@Data
@TableName("index_manage_dimension_table")
public class IndexManageDimensionTableEntity implements Serializable {
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
	 * 名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
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
