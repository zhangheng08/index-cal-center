package com.gc.stcc.indexcal.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.DimensionValue;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionTableEntity;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageDimensionTableDao;
import com.gc.stcc.indexcal.manager.service.IndexManageDimensionTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 指标管理 数据库维度
 *
 * @author hsd
 * @email ${email}
 * @date 2020-07-20 14:55:43
 */
@Service("indexManageDimensionTableService")
public class IndexManageDimensionTableServiceImpl extends ServiceImpl<IndexManageDimensionTableDao, IndexManageDimensionTableEntity> implements IndexManageDimensionTableService {

    @Autowired
    IndexManageDimensionTableDao indexManageDimensionTableDao;

    @Override
    public IndexManageDimensionTableEntity findByCode(String code) {
        IndexManageDimensionTableEntity dimensiontable = new IndexManageDimensionTableEntity();
        dimensiontable.setCode(code);
        QueryWrapper<IndexManageDimensionTableEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(dimensiontable);
        return indexManageDimensionTableDao.selectOne(queryWrapper);
    }

    @Override
    public List<DimensionValue> findDimensionValues(String name, String value, String table) {
        return indexManageDimensionTableDao.findDimensionValues(name, value, table);
    }

    @Override
    public IndexManageDimensionTableEntity findByDimensionId(String dimensionId) {
        IndexManageDimensionTableEntity dimensionEnmu = new IndexManageDimensionTableEntity();
        dimensionEnmu.setDimensionId(dimensionId);
        QueryWrapper<IndexManageDimensionTableEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(dimensionEnmu);
        return indexManageDimensionTableDao.selectOne(queryWrapper);
    }
}