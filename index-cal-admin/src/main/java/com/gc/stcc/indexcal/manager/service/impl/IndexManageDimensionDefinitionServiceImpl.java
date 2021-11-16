package com.gc.stcc.indexcal.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionDefinition;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageDimensionDefinitionMapper;
import com.gc.stcc.indexcal.manager.service.IndexManageDimensionDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: IndexManageDimensionDefinitionServiceImpl
 * @Author: ZhangHeng
 * @Date: 2021/6/30 10:56
 * @Description:
 * @Version:
 */
@Service
public class IndexManageDimensionDefinitionServiceImpl extends ServiceImpl<IndexManageDimensionDefinitionMapper, IndexManageDimensionDefinition> implements IndexManageDimensionDefinitionService {

    @Autowired
    private IndexManageDimensionDefinitionMapper indexManageDimensionDefinitionMapper;

    @Override
    public IndexManageDimensionDefinition findById(Long id) {
        final IndexManageDimensionDefinition indexManageDimensionDefinition = indexManageDimensionDefinitionMapper.selectById(id);
        return indexManageDimensionDefinition;
    }
}
