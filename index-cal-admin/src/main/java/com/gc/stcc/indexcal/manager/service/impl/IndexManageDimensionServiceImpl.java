package com.gc.stcc.indexcal.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.enums.EDimensionType;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionBuildConfigEntity;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionEnmuEntity;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionEntity;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionTableEntity;
import com.gc.stcc.indexcal.common.vo.DimensionVO;
import com.gc.stcc.indexcal.common.vo.Result;
import com.gc.stcc.indexcal.core.exception.ServiceException;
import com.gc.stcc.indexcal.core.utils.StringUtil;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageDimensionBuildConfigDao;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageDimensionDao;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageDimensionEnmuDao;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageDimensionTableDao;
import com.gc.stcc.indexcal.manager.service.IndexManageDimensionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("indexManageDimensionService")
public class IndexManageDimensionServiceImpl extends ServiceImpl<IndexManageDimensionDao, IndexManageDimensionEntity> implements IndexManageDimensionService {

    @Autowired
    private IndexManageDimensionDao indexManageDimensionDao;
    @Autowired
    private IndexManageDimensionBuildConfigDao indexManageDimensionBuildConfigDao;
    @Autowired
    private IndexManageDimensionEnmuDao indexManageDimensionEnmuDao;
    @Autowired
    private IndexManageDimensionTableDao indexManageDimensionTableDao;

    @Override
    public List<IndexManageDimensionEntity> findDimensions(IndexManageDimensionEntity dimensionParams) throws ServiceException {

        QueryWrapper<IndexManageDimensionEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(dimensionParams);
        queryWrapper.orderByAsc("sort");
        List<IndexManageDimensionEntity> resultList = indexManageDimensionDao.selectList(queryWrapper);
        return resultList;
    }

    @Override
    public List<IndexManageDimensionEntity> findDimensionListByIndexId(String indexId) {
        return indexManageDimensionDao.findDimensionListByIndexId(indexId);
    }

    @Override
    public List<DimensionVO> selectDimensionDefineVO() {
        return indexManageDimensionDao.selectDimensionDefineVO();
    }

    @Override
    @Transactional
    public Result saveDimensionDefine(DimensionVO dimensionVO) {
        // ?????????
        IndexManageDimensionEntity dimensionEntity = new IndexManageDimensionEntity();
        // ?????? ???????????????
        IndexManageDimensionEnmuEntity enmuEntity = new IndexManageDimensionEnmuEntity();
        // ?????? ??????????????????
        IndexManageDimensionTableEntity tableEntity = new IndexManageDimensionTableEntity();
        // ?????? ???????????????
        IndexManageDimensionBuildConfigEntity dimensionBuildConfig = new IndexManageDimensionBuildConfigEntity();

        BeanUtils.copyProperties(dimensionVO, dimensionEntity);
        BeanUtils.copyProperties(dimensionVO, enmuEntity);
        BeanUtils.copyProperties(dimensionVO, tableEntity);
        BeanUtils.copyProperties(dimensionVO, dimensionBuildConfig);

        // ?????? ????????? ??????????????? ??????????????????
        indexManageDimensionDao.insert(dimensionEntity);
        String dimensionId = dimensionEntity.getId();
        if(EDimensionType.Enum.value().equals(dimensionVO.getType())){
            enmuEntity.setDimensionId(dimensionId);
            indexManageDimensionEnmuDao.insert(enmuEntity);
        }else {
            tableEntity.setDimensionId(dimensionId);
            indexManageDimensionTableDao.insert(tableEntity);
        }
        // ?????????????????????
        dimensionBuildConfig.setDimensionId(dimensionEntity.getId());
        indexManageDimensionBuildConfigDao.insert(dimensionBuildConfig);
        return Result.succeed("????????????");
    }

    @Override
    @Transactional
    public Result updateDimensionDefine(DimensionVO dimensionVO) {
        // ?????????
        IndexManageDimensionEntity dimensionEntity = new IndexManageDimensionEntity();
        // ?????? ???????????????
        IndexManageDimensionEnmuEntity enmuEntity = new IndexManageDimensionEnmuEntity();
        // ?????? ??????????????????
        IndexManageDimensionTableEntity tableEntity = new IndexManageDimensionTableEntity();
        // ?????? ???????????????
        IndexManageDimensionBuildConfigEntity dimensionBuildConfig = new IndexManageDimensionBuildConfigEntity();

        BeanUtils.copyProperties(dimensionVO, dimensionEntity);
        BeanUtils.copyProperties(dimensionVO, enmuEntity);
        BeanUtils.copyProperties(dimensionVO, tableEntity);
        BeanUtils.copyProperties(dimensionVO, dimensionBuildConfig);

        // ?????? ????????? ??????????????? ??????????????????
        indexManageDimensionDao.updateById(dimensionEntity);
        String dimensionId = dimensionEntity.getId();
        if(EDimensionType.Enum.value().equals(dimensionVO.getType())){
            if(StringUtil.isBlank(dimensionVO.getEnumId())){
                indexManageDimensionEnmuDao.insert(enmuEntity);
            } else {
                enmuEntity.setId(dimensionVO.getEnumId());
                indexManageDimensionEnmuDao.updateById(enmuEntity);
            }
        }else {
            if(StringUtil.isBlank(dimensionVO.getTableId())){
                indexManageDimensionTableDao.insert(tableEntity);
            } else {
                tableEntity.setId(dimensionVO.getTableId());
                indexManageDimensionTableDao.updateById(tableEntity);
            }
        }
        // ?????????????????????
        dimensionBuildConfig.setId(dimensionVO.getBuildId());
        indexManageDimensionBuildConfigDao.updateById(dimensionBuildConfig);
        return Result.succeed("????????????");
    }

    @Override
    @Transactional
    public Result deleteDimensionDefine(DimensionVO dimensionVO) {
        // ?????? ????????? ??????????????? ??????????????? ??????????????????
        indexManageDimensionDao.deleteById(dimensionVO.getId());
        indexManageDimensionBuildConfigDao.deleteById(dimensionVO.getBuildId());
        if(StringUtil.isNotEmpty(dimensionVO.getEnumId())){
            indexManageDimensionEnmuDao.deleteById(dimensionVO.getEnumId());
        }
        if(StringUtil.isNotEmpty(dimensionVO.getTableId())){
            indexManageDimensionTableDao.deleteById(dimensionVO.getTableId());
        }
        return Result.succeed("????????????");
    }

}