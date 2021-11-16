package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexManageIndexDimensionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 指标管理 指标-维度关系表
 * 
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Mapper
public interface IndexManageIndexDimensionDao extends BaseMapper<IndexManageIndexDimensionEntity> {

}