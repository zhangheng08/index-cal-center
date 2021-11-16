package com.gc.stcc.indexcal.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionEnmuEntity;


/**
 * 指标管理 枚举维度
 *
 * @author hsd
 * @date 2020-07-20 11:36:22
 */
public interface IndexManageDimensionEnmuService extends IService<IndexManageDimensionEnmuEntity> {
    IndexManageDimensionEnmuEntity findByCode(String code);

    IndexManageDimensionEnmuEntity findByDimensionId(String dimensionId);
}

