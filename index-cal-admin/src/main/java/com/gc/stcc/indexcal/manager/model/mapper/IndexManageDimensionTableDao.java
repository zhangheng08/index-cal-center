package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.DimensionValue;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionTableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 指标管理 数据库维度
 * 
 * @author hsd
 * @date 2020-07-20 14:55:43
 */
@Mapper
public interface IndexManageDimensionTableDao extends BaseMapper<IndexManageDimensionTableEntity> {

    @Select("select ${name} as name, ${value} as value from ${table}")
    List<DimensionValue> findDimensionValues(@Param("name") String name, @Param("value") String value, @Param("table") String table);
}
