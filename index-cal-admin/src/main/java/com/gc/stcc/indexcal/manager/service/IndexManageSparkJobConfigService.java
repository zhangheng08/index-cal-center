package com.gc.stcc.indexcal.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.IndexManageSparkJobConfig;

import java.io.IOException;

/**
 * @author Payne08
 */
public interface IndexManageSparkJobConfigService extends IService<IndexManageSparkJobConfig> {

    /**
     * getConfigByJobId
     * @param jobId
     * @return IndexManageSparkJobConfig
     */
    IndexManageSparkJobConfig getConfigByJobId(Integer jobId);

}
