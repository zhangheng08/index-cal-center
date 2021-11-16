package com.gc.stcc.indexcal.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionDefinition;

import java.io.Serializable;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: IndexManageDimensionDefinitionService
 * @Author: ZhangHeng
 * @Date: 2021/6/30 10:55
 * @Description:
 * @Version:
 */
public interface IndexManageDimensionDefinitionService extends IService<IndexManageDimensionDefinition> {

    IndexManageDimensionDefinition findById(Long id);
}
