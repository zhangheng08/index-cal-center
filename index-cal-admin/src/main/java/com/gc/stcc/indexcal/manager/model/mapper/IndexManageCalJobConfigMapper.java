package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexManageCalJobConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Payne08
 */
@Component
public interface IndexManageCalJobConfigMapper extends BaseMapper<IndexManageCalJobConfig> {

    /**
     * fetchById
     * @param jobId
     * @return IndexManageCalJobConfig
     */
    IndexManageCalJobConfig fetchById(@Param("jobId")Integer jobId);

}
