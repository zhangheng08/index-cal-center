package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 指标管理 指标
 * 
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Data
@TableName("index_manage_index")
public class IndexManageIndexEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
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

}
