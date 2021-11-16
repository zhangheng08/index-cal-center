package com.gc.stcc.indexcal.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.IndexManageSparkJobConfig;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageSparkJobConfigMapper;
import com.gc.stcc.indexcal.manager.service.IndexManageSparkJobConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: IndexManageSparkJobConfigServiceImpl
 * Author:  Payne08
 * Date: 2021/6/10 16:07
 * Description: IndexManageSparkJobConfigServiceImpl
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 * @author Payne08
 */

@Service
public class IndexManageSparkJobConfigServiceImpl extends ServiceImpl<IndexManageSparkJobConfigMapper, IndexManageSparkJobConfig> implements IndexManageSparkJobConfigService {

    @Autowired
    private IndexManageSparkJobConfigMapper indexManageSparkJobConfigMapper;

    @Override
    public IndexManageSparkJobConfig getConfigByJobId(Integer jobId) {
        return indexManageSparkJobConfigMapper.getConfigByJobId(jobId);
    }

}
