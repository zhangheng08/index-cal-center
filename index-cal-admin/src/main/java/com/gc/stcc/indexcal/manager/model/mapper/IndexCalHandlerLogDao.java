package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexCalHandlerLogEntity;
import org.springframework.stereotype.Component;

/**
 * 指标计算 计算处理记录表
 * 
 * @author hsd
 * @email ${email}
 * @date 2020-08-07 15:02:16
 */

@Component
public interface IndexCalHandlerLogDao extends BaseMapper<IndexCalHandlerLogEntity> {
	
}
