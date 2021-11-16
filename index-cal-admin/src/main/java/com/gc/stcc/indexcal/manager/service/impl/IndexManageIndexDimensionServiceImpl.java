package com.gc.stcc.indexcal.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.IndexManageIndexDimensionEntity;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageIndexDimensionDao;
import com.gc.stcc.indexcal.manager.service.IndexManageIndexDimensionService;
import org.springframework.stereotype.Service;


@Service("indexManageIndexDimensionService")
public class IndexManageIndexDimensionServiceImpl extends ServiceImpl<IndexManageIndexDimensionDao, IndexManageIndexDimensionEntity> implements IndexManageIndexDimensionService {

}