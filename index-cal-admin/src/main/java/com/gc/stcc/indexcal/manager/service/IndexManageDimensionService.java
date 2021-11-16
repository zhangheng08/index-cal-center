package com.gc.stcc.indexcal.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionEntity;
import com.gc.stcc.indexcal.common.vo.DimensionVO;
import com.gc.stcc.indexcal.common.vo.Result;

import java.util.List;


/**
 *
 *
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
public interface IndexManageDimensionService extends IService<IndexManageDimensionEntity> {

    List<IndexManageDimensionEntity> findDimensions(IndexManageDimensionEntity dimensionParams);

    List<IndexManageDimensionEntity> findDimensionListByIndexId(String indexId);

    List<DimensionVO> selectDimensionDefineVO();

    Result saveDimensionDefine(DimensionVO dimensionVO);

    Result updateDimensionDefine(DimensionVO dimensionVO);

    Result deleteDimensionDefine(DimensionVO dimensionVO);
}

