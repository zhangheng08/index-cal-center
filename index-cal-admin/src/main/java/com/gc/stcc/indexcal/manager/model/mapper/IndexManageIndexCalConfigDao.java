package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexManageIndexCalConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 指标管理 指标计算配置
 * 
 * @author hsd
 * @date 2020-09-22 15:48:01
 */
@Mapper
public interface IndexManageIndexCalConfigDao extends BaseMapper<IndexManageIndexCalConfigEntity> {
}
