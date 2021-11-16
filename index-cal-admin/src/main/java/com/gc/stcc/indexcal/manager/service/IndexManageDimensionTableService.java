package com.gc.stcc.indexcal.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.DimensionValue;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionTableEntity;

import java.util.List;

/**
 * 指标管理 数据库维度
 *
 * @author hsd
 * @email ${email}
 * @date 2020-07-20 14:55:43
 */
public interface IndexManageDimensionTableService extends IService<IndexManageDimensionTableEntity> {
    IndexManageDimensionTableEntity findByCode(String code);
    List<DimensionValue> findDimensionValues(String name, String value, String table);
    IndexManageDimensionTableEntity findByDimensionId(String dimensionId);
}

