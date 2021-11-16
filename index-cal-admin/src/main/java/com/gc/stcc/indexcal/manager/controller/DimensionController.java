package com.gc.stcc.indexcal.manager.controller;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gc.stcc.indexcal.common.model.DimensionValue;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionEntity;
import com.gc.stcc.indexcal.common.model.IndexManageIndexDimensionEntity;
import com.gc.stcc.indexcal.common.model.IndexManageIndexEntity;
import com.gc.stcc.indexcal.common.vo.DimensionVO;
import com.gc.stcc.indexcal.common.vo.Result;
import com.gc.stcc.indexcal.core.exception.ServiceException;
import com.gc.stcc.indexcal.manager.service.DimensionValueService;
import com.gc.stcc.indexcal.manager.service.IndexManageDimensionService;
import com.gc.stcc.indexcal.manager.service.IndexManageIndexDimensionService;
import com.gc.stcc.indexcal.manager.service.IndexManageIndexService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hsd
 * @date 2020-07-09 14:19:41
 */
@RestController
@RequestMapping("index/manage/dimension")
public class DimensionController {

    @Autowired
    private IndexManageDimensionService indexManageDimensionService;
    @Autowired
    private DimensionValueService dimensionValueService;
    @Autowired
    private IndexManageIndexDimensionService indexManageIndexDimensionService;
    @Autowired
    private IndexManageIndexService indexManageIndexService;

    /**
     * 指标管理 新增维度
     * @param dimensionVO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 新增维度")
    @PostMapping("/save")
    public Result save(@RequestBody DimensionVO dimensionVO) throws Exception {
        try {
            Date now = new Date();
            dimensionVO.setCreateDate(now);
            dimensionVO.setUpdateDate(now);
            dimensionVO.setOwnerId("unname");
            return indexManageDimensionService.saveDimensionDefine(dimensionVO);
        } catch (ServiceException e) {
            throw new Exception(e);
        }
    }

    /**
     * 指标管理 查询维度
     * @param traceId
     * @param dimension
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 查询维度")
    @PostMapping("/findDimensions")
    public Result findDimensions(@RequestHeader(name = "trace_id", required = false) String traceId, @RequestBody IndexManageDimensionEntity dimension) throws Exception {
        try {
            Result result = Result.succeed("查询成功");
            List<IndexManageDimensionEntity> list = indexManageDimensionService.findDimensions(dimension);
            result.setData(list);
            return result;
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    /**
     * 指标管理 查询维度视图
     * @param traceId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 查询维度视图")
    @PostMapping("/findDimensionViews")
    public Result findDimensionViews(@RequestHeader(name = "trace_id", required = false) String traceId) throws Exception {
        try {
            Result result = Result.succeed("查询成功");
            List<DimensionVO> list = indexManageDimensionService.selectDimensionDefineVO();
            result.setData(list);
            return result;
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    /**
     * 指标管理 查询维度值List
     * @param traceId
     * @param dimension
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 查询维度值")
    @PostMapping("/findDimensionValues")
    public Result findDimensionValues(@RequestHeader(name = "trace_id", required = false) String traceId, @RequestBody IndexManageDimensionEntity dimension) throws Exception {
        try {
            Result result = Result.succeed("查询成功");
            List<DimensionValue> dimensionValueList = dimensionValueService.getDimension(dimension);
            result.setData(dimensionValueList);
            return result;
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    /**
     * 指标管理 查询维度值Map
     * @param traceId
     * @param index
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 查询指标纬度值Map")
    @PostMapping("/findDimensionValueMap")
    public Result findDimensionValueMap(@RequestHeader(name = "trace_id", required = false) String traceId, @RequestBody IndexManageIndexEntity index) throws Exception {
        try {
            Result result = Result.succeed("查询成功");
            Map<String, List<DimensionValue>> dimensionValueMap = new HashMap<>();
            List<IndexManageDimensionEntity> dimensionList = indexManageDimensionService.findDimensionListByIndexId(String.valueOf(index.getId()));
            for (IndexManageDimensionEntity dimension:dimensionList) {
                List<DimensionValue> dimensionValueList = dimensionValueService.getDimension(dimension);
                dimensionValueMap.put(dimension.getCode(), dimensionValueList);
            }
            result.setData(dimensionValueMap);
            return result;
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    /**
     * 指标管理 查询维度值Map
     * @param indexCode
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 查询指标纬度值Map")
    @PostMapping("/findDimensionValueMapByCode")
    public Map<String, List<DimensionValue>> findDimensionValueMapByCode(@RequestParam(name = "indexCode") String indexCode) throws Exception {
        Map<String, List<DimensionValue>> dimensionValueMap = new HashMap<>();
        try {
            IndexManageIndexEntity index = indexManageIndexService.selectIndexManageIndexEntityByIndexCode(indexCode);
            List<IndexManageDimensionEntity> dimensionList = indexManageDimensionService.findDimensionListByIndexId(String.valueOf(index.getId()));
            for (IndexManageDimensionEntity dimension : dimensionList) {
                List<DimensionValue> dimensionValueList = dimensionValueService.getDimension(dimension);
                dimensionValueMap.put(dimension.getCode(), dimensionValueList);
            }
        } catch (ServiceException e) {
            throw new Exception(e);
        }
        return dimensionValueMap;
    }

    /**
     * 指标管理 修改维度
     * @param dimensionVO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 修改维度")
    @PostMapping("/update")
    public Result update(@RequestBody DimensionVO dimensionVO) throws Exception {
        try {
            Date now = new Date();
            dimensionVO.setUpdateDate(now);
            dimensionVO.setOwnerId("noid");
            // 查询是否有指标关联过该维度
            String dimensionId = dimensionVO.getId();
            IndexManageIndexDimensionEntity indexDimensionQueryParams = new IndexManageIndexDimensionEntity();
            indexDimensionQueryParams.setDimensionId(dimensionId);
            QueryWrapper<IndexManageIndexDimensionEntity> queryWrapper = new QueryWrapper();
            queryWrapper.setEntity(indexDimensionQueryParams);
            List<IndexManageIndexDimensionEntity> list = indexManageIndexDimensionService.list(queryWrapper);
            if(!list.isEmpty()){
                return Result.failed("有指标关联过该维度，无法进行修改");
            }
            return indexManageDimensionService.updateDimensionDefine(dimensionVO);
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    /**
     * 指标管理 新增维度
     * @param dimensionVO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 删除维度")
    @PostMapping("/delete")
    public Result delete(@RequestBody DimensionVO dimensionVO) throws Exception {
        try {
            Date now = new Date();
            dimensionVO.setUpdateDate(now);
            dimensionVO.setOwnerId("noid");
            // 查询是否有指标关联过该维度
            String dimensionId = dimensionVO.getId();
            IndexManageIndexDimensionEntity indexDimensionQueryParams = new IndexManageIndexDimensionEntity();
            indexDimensionQueryParams.setDimensionId(dimensionId);
            QueryWrapper<IndexManageIndexDimensionEntity> queryWrapper = new QueryWrapper();
            queryWrapper.setEntity(indexDimensionQueryParams);
            List<IndexManageIndexDimensionEntity> list = indexManageIndexDimensionService.list(queryWrapper);
            if(!list.isEmpty()){
                return Result.failed("有指标关联过该维度，无法进行删除");
            }
            return indexManageDimensionService.deleteDimensionDefine(dimensionVO);
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }


}
