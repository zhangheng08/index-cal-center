package com.gc.stcc.indexcal.manager.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.IndexCalHandlerDefineEntity;

/**
 * 指标计算 处理类定义表
 *
 * @author hsd
 * @email ${email}
 * @date 2020-08-07 15:02:15
 */
public interface IndexCalHandlerDefineService extends IService<IndexCalHandlerDefineEntity> {
    IndexCalHandlerDefineEntity getCalDefineByCodeAndHandlerNameAndDimensionDef(String indexCode, String handlerName);
}

