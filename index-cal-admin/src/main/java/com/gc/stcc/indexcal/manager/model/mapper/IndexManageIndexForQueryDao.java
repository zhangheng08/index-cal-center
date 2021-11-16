package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexManageIndexForQueryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 指标管理指标查询表
 * 
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Mapper
public interface IndexManageIndexForQueryDao extends BaseMapper<IndexManageIndexForQueryEntity> {
	
}
