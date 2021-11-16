package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 指标管理 指标-计算表
 * 
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Data
@TableName("index_manage_index_for_cal")
public class IndexManageIndexForCalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 指标ID
	 */
	private Long indexId;
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
	 * 计算字段名称
	 */
	private String calColumnNames;
	/**
	 * 计算维度id列表
	 */
	private String calDimensionIds;

}
