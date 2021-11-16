package com.gc.stcc.indexcal.manager.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.IndexCalHandlerLogEntity;

import java.util.Date;


/**
 * 指标计算 计算处理记录表
 *
 * @author hsd
 * @email ${email}
 * @date 2020-08-07 15:02:16
 */

public interface IndexCalHandlerLogService extends IService<IndexCalHandlerLogEntity> {
    IndexCalHandlerLogEntity getCalLogByCodeAndHandlerNameAndInterval(String indexCode, String handlerName, String intervalId);
    boolean updateCalLogToRecalByDate(String indexCode, String handlerName, String intervalId, Date startDate, Date endDate);
}

