package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionEntity;
import com.gc.stcc.indexcal.common.vo.DimensionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 指标管理 维度
 * 
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Component
public interface IndexManageDimensionDao extends BaseMapper<IndexManageDimensionEntity> {
    @Select(" SELECT" +
            "     index_manage_dimension.id," +
            "     index_manage_dimension.create_date," +
            "     index_manage_dimension.update_date," +
            "     index_manage_dimension.owner_id," +
            "     index_manage_dimension.name," +
            "     index_manage_dimension.type," +
            "     index_manage_dimension.column_name," +
            "     index_manage_dimension.sort," +
            "     index_manage_dimension.is_default," +
            "     index_manage_dimension.code" +
            " FROM" +
            "     index_manage_dimension" +
            " LEFT JOIN" +
            "     index_manage_index_dimension" +
            " ON" +
            "     index_manage_index_dimension.dimension_id = index_manage_dimension.id" +
            " WHERE" +
            "     index_manage_index_dimension.index_id = #{indexId}")
    List<IndexManageDimensionEntity> findDimensionListByIndexId(@Param("indexId") String indexId);

    List<DimensionVO> selectDimensionDefineVO();
}
