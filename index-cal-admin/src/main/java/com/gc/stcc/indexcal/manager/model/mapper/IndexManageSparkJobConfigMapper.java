package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexManageSparkJobConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Payne08
 */
@Component
public interface IndexManageSparkJobConfigMapper extends BaseMapper<IndexManageSparkJobConfig> {

    /**
     * getConfigByJobId
     *
     * @param jobId
     * @return IndexManageSparkJobConfig
     */
    IndexManageSparkJobConfig getConfigByJobId(@Param("jobId")Integer jobId);

}
