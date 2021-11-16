package com.gc.stcc.indexcal.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.IndexManageIndexForBuildEntity;


/**
 * 指标管理指标创建表
 *
 * @author hsd
 * @date 2020-07-21 10:53:17
 */
public interface IndexManageIndexForBuildService extends IService<IndexManageIndexForBuildEntity> {
    StringBuilder getCreateTableSql(String indexId);
    int checkTableIsExist(String tableName);
}

