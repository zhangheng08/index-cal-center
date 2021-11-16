package com.gc.stcc.indexcal.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.IndexManageIndexCalConfigEntity;


/**
 * 指标管理 指标计算配置表
 *
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
public interface IndexManageIndexCalConfigService extends IService<IndexManageIndexCalConfigEntity> {
    IndexManageIndexCalConfigEntity getIndexCalConfig(String indexId);
}

