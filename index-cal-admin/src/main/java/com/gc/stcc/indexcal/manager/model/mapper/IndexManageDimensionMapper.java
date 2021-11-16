package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexManageDimension;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Payne08
 */
@Component
public interface IndexManageDimensionMapper extends BaseMapper<IndexManageDimension> {

    /**
     * getAll
     *
     * @param
     * @param
     * @param
     * @return
     */
    List<IndexManageDimension> getAll();

}
