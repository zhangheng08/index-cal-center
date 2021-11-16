package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionBuildConfigEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 指标管理 维度 数据库类型配置
 * 
 * @author hsd
 * @date 2020-07-21 10:53:17
 */
@Mapper
public interface IndexManageDimensionBuildConfigDao extends BaseMapper<IndexManageDimensionBuildConfigEntity> {
    List<IndexManageDimensionBuildConfigEntity> selectBatchDimensionIds(List<String> dimensionIds);
}
