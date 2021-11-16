package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 指标管理 指标-维度关系表
 * 
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Data
@TableName("index_manage_index_dimension")
public class IndexManageIndexDimensionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId(type = IdType.AUTO)
	private String id;
	/**
	 * 自增主键
	 */
	private String indexId;
	/**
	 * 自增主键
	 */
	private String dimensionId;

}
