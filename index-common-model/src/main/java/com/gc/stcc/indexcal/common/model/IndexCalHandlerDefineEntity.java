package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("index_cal_handler_define")
@Builder(toBuilder=true)
public class IndexCalHandlerDefineEntity implements Serializable {

	/**
	 * 自增主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 创建日期时间
	 */
	@TableField("create_date")
	private Date createDate;
	/**
	 * 修改日期时间
	 */
	@TableField("update_date")
	private Date updateDate;
	/**
	 * 指标ID
	 */
	@TableField("index_id")
	private Long indexId;
	/**
	 * 名称
	 */
	@TableField("index_name")
	private String indexName;
	/**
	 * 编码
	 */
	@TableField("index_code")
	private String indexCode;
	/**
	 * 执行器名称
	 */
	@TableField("handler_name")
	private String handlerName;
	/**
	 * 首次计算日期时间
	 */
	@TableField("first_cal_date")
	private Date firstCalDate;
	/**
	 维度定义信息
	 */
	@TableField("dimension_definition_id")
	private Long dimensionDefinitionId;
	@TableField("origin_table_name")
	private String originTableName;


}
