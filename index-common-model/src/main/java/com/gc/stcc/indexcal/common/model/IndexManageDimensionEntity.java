package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 指标管理 维度
 * 
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Data
@TableName("index_manage_dimension")
public class IndexManageDimensionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId(type = IdType.AUTO)
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

}
