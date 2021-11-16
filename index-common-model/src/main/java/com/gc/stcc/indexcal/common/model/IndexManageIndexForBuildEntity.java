package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 指标管理指标创建表
 * 
 * @author hsd
 * @date 2020-07-21 10:53:17
 */
@Data
@TableName("index_manage_index_for_build")
public class IndexManageIndexForBuildEntity implements Serializable {
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
	 * 维度id列表
	 */
	private String dimensionIds;
	/**
	 * 额外补充的sql
	 */
	private String extraSql;

}
