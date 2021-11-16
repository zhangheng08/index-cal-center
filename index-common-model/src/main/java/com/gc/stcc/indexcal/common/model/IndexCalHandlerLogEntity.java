package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 指标计算 计算处理记录表
 * 
 * @author hsd
 * @email ${email}
 * @date 2020-08-07 15:02:16
 */
@Data
@TableName("index_cal_handler_log")
@Builder(toBuilder=true)
public class IndexCalHandlerLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public IndexCalHandlerLogEntity() {
	}

	public IndexCalHandlerLogEntity(String id, Date createDate, String indexCode, String handlerName, String intervalId, Date calStartDate, Date calEndDate, String calColumnNames, String calResult, String writeNum, String calCostTime, String calType) {
		this.id = id;
		this.createDate = createDate;
		this.indexCode = indexCode;
		this.handlerName = handlerName;
		this.intervalId = intervalId;
		this.calStartDate = calStartDate;
		this.calEndDate = calEndDate;
		this.calColumnNames = calColumnNames;
		this.calResult = calResult;
		this.writeNum = writeNum;
		this.calCostTime = calCostTime;
		this.calType = calType;
	}

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
	 * 编码
	 */
	private String indexCode;
	/**
	 * 执行器名称
	 */
	private String handlerName;
	/**
	 * 计算时间粒度
	 */
	private String intervalId;
	/**
	 * 计算开始日期时间
	 */
	private Date calStartDate;
	/**
	 * 计算结束日期时间
	 */
	private Date calEndDate;
	/**
	 * 计算维度名称
	 */
	private String calColumnNames;
	/**
	 * 计算结果
	 */
	private String calResult;

//	private String readNum;
	/**
	 * 生成指标结果数目
	 */
	private String writeNum;
	/**
	 * 计算用时
	 */
	private String calCostTime;
	/**
	 * 计算类型：系统延时计算、指定的单次计算
	 */
	private String calType;

}
