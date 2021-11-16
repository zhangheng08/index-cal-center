package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexManageDimBuild;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Payne08
 */
@Component
public interface IndexManageDimBuildMapper extends BaseMapper<IndexManageDimBuild> {

    /**
     * fetchByDimCode
     * @param code
     * @return List<IndexManageDimBuild>
     */
    List<IndexManageDimBuild> fetchByDimCode(@Param("dimBuildCode")String code);

}
