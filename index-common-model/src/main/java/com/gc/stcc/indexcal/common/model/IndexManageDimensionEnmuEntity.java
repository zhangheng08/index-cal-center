package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 指标管理 枚举维度
 * 
 * @author hsd
 * @email ${email}
 * @date 2020-07-20 11:36:22
 */
@Data
@TableName("index_manage_dimension_enmu")
public class IndexManageDimensionEnmuEntity implements Serializable {
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
	 * enmu全类名
	 */
	private String className;
	/**
	 * redis key名称
	 */
	private String redisKey;

}
