package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 指标管理 指标
 * 
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Data
@TableName("index_manage_index_cal_config")
public class IndexManageIndexCalConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId(type = IdType.AUTO)
	private String id;
	/**
	 * 指标ID
	 */
	private String indexId;
	/**
	 * 计算配置信息
	 */
	private String jsonConfig;
	/**
	 * 创建日期时间
	 */
	private Date createDate;

}
