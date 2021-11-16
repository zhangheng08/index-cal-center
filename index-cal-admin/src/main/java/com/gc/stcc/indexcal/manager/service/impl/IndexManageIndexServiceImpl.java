package com.gc.stcc.indexcal.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.*;
import com.gc.stcc.indexcal.common.vo.IndexDefineVO;
import com.gc.stcc.indexcal.common.vo.Result;
import com.gc.stcc.indexcal.core.utils.StringUtil;
import com.gc.stcc.indexcal.manager.model.mapper.*;
import com.gc.stcc.indexcal.manager.service.IndexManageIndexService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("indexManageIndexService")
public class IndexManageIndexServiceImpl extends ServiceImpl<IndexManageIndexDao, IndexManageIndexEntity> implements IndexManageIndexService {

    @Autowired
    private IndexManageDimensionDao indexManageDimensionDao;
    @Autowired
    private IndexManageIndexDao indexManageIndexDao;
    @Autowired
    private IndexManageIndexDimensionDao indexManageIndexDimensionDao;
    @Autowired
    private IndexManageIndexForCalDao indexManageIndexForCalDao;
    @Autowired
    private IndexManageIndexForQueryDao indexManageIndexForQueryDao;
    @Autowired
    private IndexManageIndexForBuildDao indexManageIndexForBuildDao;


    /**
     * 新增指标
     * @param indexDefineVO
     * @return
     */
    @Transactional
    @Override
    public Result saveIndexDefine(IndexDefineVO indexDefineVO){
        // 指标表
        IndexManageIndexEntity indexEntity = new IndexManageIndexEntity();
        // 指标计算表
        IndexManageIndexForCalEntity indexCalEntity = new IndexManageIndexForCalEntity();
        // 指标查询表
        IndexManageIndexForQueryEntity indexQueryEntity = new IndexManageIndexForQueryEntity();
        // 指标创建表
        IndexManageIndexForBuildEntity indexBuildEntity = new IndexManageIndexForBuildEntity();

        BeanUtils.copyProperties(indexDefineVO, indexEntity);
        BeanUtils.copyProperties(indexDefineVO, indexCalEntity);
        BeanUtils.copyProperties(indexDefineVO, indexQueryEntity);
        BeanUtils.copyProperties(indexDefineVO, indexBuildEntity);

        // 保存 指标表
        indexManageIndexDao.insert(indexEntity);
        String id = String.valueOf(indexEntity.getId());
        String dimensionIds = indexDefineVO.getDimensionIds();
        // 保存 指标关联表
        for (String dimensionId: dimensionIds.split("\\,")){
            IndexManageIndexDimensionEntity indexDimension = new IndexManageIndexDimensionEntity();
            indexDimension.setDimensionId(dimensionId.trim());
            indexDimension.setIndexId(id);
            indexManageIndexDimensionDao.insert(indexDimension);
        }
        // 根据ids 查询出维度对应的列名
        List<IndexManageDimensionEntity> dimensionList = indexManageDimensionDao.selectList(new QueryWrapper());
        Map<String, String> dimensionMap = new HashMap<String, String>();
        for (IndexManageDimensionEntity dimension :dimensionList) {
            dimensionMap.put(dimension.getId(), dimension.getColumnName());
        }
        String columnNames = getColumnNames(dimensionIds, dimensionMap);
        indexCalEntity.setColumnNames(columnNames);
        indexCalEntity.setCalColumnNames(getColumnNames(indexDefineVO.getCalDimensionIds(), dimensionMap));
        indexCalEntity.setIndexId(Long.parseLong(id));
        indexQueryEntity.setColumnNames(columnNames);
        indexQueryEntity.setIndexId(id);
        indexBuildEntity.setIndexId(id);
        // 保存 指标计算表
        indexManageIndexForCalDao.insert(indexCalEntity);
        // 保存 指标查询表
        indexManageIndexForQueryDao.insert(indexQueryEntity);
        // 保存 指标创建表
        indexManageIndexForBuildDao.insert(indexBuildEntity);
        return Result.succeed("保存成功");
    }

    @Override
    public List<IndexDefineVO> selectIndexDefineVO() {
        return indexManageIndexDao.selectIndexDefineVO();
    }

    /**
     * 根据维度 ids 获取列名称
     * @param dimensionIds
     * @param dimensionMap
     * @return
     */
    private String getColumnNames(String dimensionIds, Map<String, String> dimensionMap) {
        if(StringUtil.isEmpty(dimensionIds)){
            return "";
        }
        StringBuilder columnNamesSb = new StringBuilder();
        for (String dimensionId: dimensionIds.split("\\,")){
            String columnName = dimensionMap.get(dimensionId.trim());
            if(StringUtil.isNotEmpty(columnName)){
                columnNamesSb.append(columnName + ",");
            }
        }
        columnNamesSb = columnNamesSb.length()==0?columnNamesSb:columnNamesSb.deleteCharAt(columnNamesSb.length()-1);
        return columnNamesSb.toString();
    }

    /**
     * 修改 指标
     * @param indexDefineVO
     * @return
     */
    @Override
    @Transactional
    public Result updateIndexDefineVO(IndexDefineVO indexDefineVO) {
        // 指标表
        IndexManageIndexEntity indexEntity = new IndexManageIndexEntity();
        // 指标计算表
        IndexManageIndexForCalEntity indexCalEntity = new IndexManageIndexForCalEntity();
        // 指标查询表
        IndexManageIndexForQueryEntity indexQueryEntity = new IndexManageIndexForQueryEntity();
        // 指标创建表
        IndexManageIndexForBuildEntity indexBuildEntity = new IndexManageIndexForBuildEntity();

        BeanUtils.copyProperties(indexDefineVO, indexEntity);
        BeanUtils.copyProperties(indexDefineVO, indexCalEntity);
        indexCalEntity.setId(Long.parseLong(indexDefineVO.getCalId()));
        BeanUtils.copyProperties(indexDefineVO, indexQueryEntity);
        indexQueryEntity.setId(indexDefineVO.getQueryId());
        BeanUtils.copyProperties(indexDefineVO, indexBuildEntity);
        indexBuildEntity.setId(indexDefineVO.getBuildId());

        String dimensionIds = indexDefineVO.getDimensionIds();
        // 清空 该指标的关联表 并重新添加
        String indexId = indexDefineVO.getId();
        IndexManageIndexDimensionEntity indexDimensionDeleteParams = new IndexManageIndexDimensionEntity();
        indexDimensionDeleteParams.setIndexId(indexId);
        QueryWrapper<IndexManageIndexDimensionEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(indexDimensionDeleteParams);
        indexManageIndexDimensionDao.delete(queryWrapper);
        // 保存 指标关联表
        for (String dimensionId: dimensionIds.split("\\,")){
            IndexManageIndexDimensionEntity indexDimension = new IndexManageIndexDimensionEntity();
            indexDimension.setDimensionId(dimensionId.trim());
            indexDimension.setIndexId(indexId);
            indexManageIndexDimensionDao.insert(indexDimension);
        }
        // 根据ids 查询出维度对应的列名
        List<IndexManageDimensionEntity> dimensionList = indexManageDimensionDao.selectList(new QueryWrapper());
        Map<String, String> dimensionMap = new HashMap<String, String>();
        for (IndexManageDimensionEntity dimension :dimensionList) {
            dimensionMap.put(dimension.getId(), dimension.getColumnName());
        }
        String columnNames = getColumnNames(dimensionIds, dimensionMap);
        indexCalEntity.setColumnNames(columnNames);
        indexCalEntity.setCalColumnNames(getColumnNames(indexDefineVO.getCalDimensionIds(), dimensionMap));
        indexQueryEntity.setColumnNames(columnNames);

        // 修改 指标表
        indexManageIndexDao.updateById(indexEntity);
        // 修改 指标计算表
        indexManageIndexForCalDao.updateById(indexCalEntity);
        // 修改 指标查询表
        indexManageIndexForQueryDao.updateById(indexQueryEntity);
        // 修改 指标创建表
        indexManageIndexForBuildDao.updateById(indexBuildEntity);

        return Result.succeed("修改成功");
    }

    @Override
    @Transactional
    public Result deleteIndexDefineVO(IndexDefineVO indexDefineVO) {
        // 清空 该指标的关联表
        String indexId = indexDefineVO.getId();
        IndexManageIndexDimensionEntity indexDimensionDeleteParams = new IndexManageIndexDimensionEntity();
        indexDimensionDeleteParams.setIndexId(indexId);
        QueryWrapper<IndexManageIndexDimensionEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(indexDimensionDeleteParams);
        indexManageIndexDimensionDao.delete(queryWrapper);
        // 删除 指标计算表
        indexManageIndexForCalDao.deleteById(indexDefineVO.getCalId());
        // 删除 指标查询表
        indexManageIndexForQueryDao.deleteById(indexDefineVO.getQueryId());
        // 删除 指标创建表
        indexManageIndexForBuildDao.deleteById(indexDefineVO.getBuildId());
        // 删除 指标表
        indexManageIndexDao.deleteById(indexDefineVO.getId());
        return Result.succeed("删除成功");
    }

    @Override
    public IndexManageIndexEntity selectIndexManageIndexEntityByIndexCode(String indexCode) {
        IndexManageIndexEntity indexManageIndexEntityParams = new IndexManageIndexEntity();
        indexManageIndexEntityParams.setCode(indexCode);
        QueryWrapper<IndexManageIndexEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(indexManageIndexEntityParams);
        List<IndexManageIndexEntity> list = indexManageIndexDao.selectList(queryWrapper);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

}