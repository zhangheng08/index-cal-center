package com.gc.stcc.indexcal.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.IndexManageCalJobConfig;
import com.gc.stcc.indexcal.common.vo.IndexCalculateConfigVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author Payne08
 */
public interface IndexManageCalJobConfigService extends IService<IndexManageCalJobConfig> {

    /**
     * fetchById
     * @param jobId
     * @return IndexManageCalJobConfig
     */
    IndexCalculateConfigVo fetchById(@Param("jobId")Integer jobId);

}
