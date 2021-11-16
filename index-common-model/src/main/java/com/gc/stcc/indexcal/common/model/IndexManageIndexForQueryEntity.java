package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 指标管理指标查询表
 * 
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Data
@TableName("index_manage_index_for_query")
public class IndexManageIndexForQueryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键ID
	 */
	@TableId(type = IdType.AUTO)
	private String id;
	/**
	 * 指标ID
	 */
	private String indexId;
	/**
	 * 指标结果表名称
	 */
	private String resultTableName;
	/**
	 * 列名称
	 */
	private String columnNames;
	/**
	 * 维度id列表
	 */
	private String dimensionIds;

}
