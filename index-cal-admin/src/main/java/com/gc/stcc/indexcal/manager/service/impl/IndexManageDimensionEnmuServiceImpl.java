package com.gc.stcc.indexcal.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionEnmuEntity;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageDimensionEnmuDao;
import com.gc.stcc.indexcal.manager.service.IndexManageDimensionEnmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author hsd
 */
@Service("indexManageDimensionEnmuService")
public class IndexManageDimensionEnmuServiceImpl extends ServiceImpl<IndexManageDimensionEnmuDao, IndexManageDimensionEnmuEntity> implements IndexManageDimensionEnmuService {

    @Autowired
    private IndexManageDimensionEnmuDao indexManageDimensionEnmuDao;

    @Override
    public IndexManageDimensionEnmuEntity findByCode(String code) {
        IndexManageDimensionEnmuEntity dimensionEnmu = new IndexManageDimensionEnmuEntity();
        dimensionEnmu.setCode(code);
        QueryWrapper<IndexManageDimensionEnmuEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(dimensionEnmu);
        return indexManageDimensionEnmuDao.selectOne(queryWrapper);
    }

    @Override
    public IndexManageDimensionEnmuEntity findByDimensionId(String dimensionId) {
        IndexManageDimensionEnmuEntity dimensionEnmu = new IndexManageDimensionEnmuEntity();
        dimensionEnmu.setDimensionId(dimensionId);
        QueryWrapper<IndexManageDimensionEnmuEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(dimensionEnmu);
        return indexManageDimensionEnmuDao.selectOne(queryWrapper);
    }
}