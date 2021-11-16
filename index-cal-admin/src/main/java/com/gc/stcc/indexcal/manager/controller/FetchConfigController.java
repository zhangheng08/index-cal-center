package com.gc.stcc.indexcal.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gc.stcc.indexcal.common.model.IndexCalHandlerDefineEntity;
import com.gc.stcc.indexcal.common.model.IndexCalHandlerLogEntity;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionDefinition;
import com.gc.stcc.indexcal.common.model.IndexManageIndexForCalEntity;
import com.gc.stcc.indexcal.common.vo.IndexCalDefineVO;
import com.gc.stcc.indexcal.common.vo.Result;
import com.gc.stcc.indexcal.core.exception.ServiceException;
import com.gc.stcc.indexcal.manager.service.IndexCalHandlerDefineService;
import com.gc.stcc.indexcal.manager.service.IndexCalHandlerLogService;
import com.gc.stcc.indexcal.manager.service.IndexManageDimensionDefinitionService;
import com.gc.stcc.indexcal.manager.service.IndexManageIndexForCalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: FetchConfigController
 * @Author: ZhangHeng
 * @Date: 2021/6/1 08:53
 * @Description:
 * @Version:
 */
@Api(value = "IndexDefineController", hidden = true)
@RestController
@RequestMapping(path = "/config")
public class FetchConfigController {

    @Autowired
    private IndexCalHandlerDefineService indexCalHandlerDefineService;
    @Autowired
    private IndexCalHandlerLogService indexCalHandlerLogService;
    @Autowired
    private IndexManageDimensionDefinitionService indexManageDimensionDefinitionService;
    @Autowired
    private IndexManageIndexForCalService indexManageIndexForCalService;

    @RequestMapping(value = "/fetchCalDefine/{indexCode}/{handlerName}")
    public Result<IndexCalHandlerDefineEntity> fetchCalDefine(@PathVariable("indexCode")String indexCode, @PathVariable("handlerName")String handlerName) {
        final IndexCalHandlerDefineEntity indexCalHandlerDefineEntity = indexCalHandlerDefineService.getCalDefineByCodeAndHandlerNameAndDimensionDef(indexCode, handlerName);
        Result<IndexCalHandlerDefineEntity> result = new Result<>();
        result.setData(indexCalHandlerDefineEntity);
        result.setResp_msg("success");
        return result;
    }

    @RequestMapping(value = "/updateCalDefine")
    public Result<Boolean> updateCalDefinition(@RequestBody String jsonString) {
        Result<Boolean> result = new Result<>();
        final IndexCalHandlerDefineEntity indexCalHandlerDefineEntity = JSONObject.parseObject(jsonString, IndexCalHandlerDefineEntity.class);
        final boolean ret = indexCalHandlerDefineService.updateById(indexCalHandlerDefineEntity);
        result.setData(ret);
        result.setResp_msg(ret ? "success" : "failed");
        return result;
    }

    @RequestMapping(value = "/fetchJobLog/{indexCode}/{handlerName}/{intervalId}")
    public Result<IndexCalHandlerLogEntity> fetchJobLog(@PathVariable("indexCode")String indexCode, @PathVariable("handlerName")String handlerName, @PathVariable("intervalId")String intervalId) {
        final IndexCalHandlerLogEntity calLogByCodeAndHandlerNameAndInterval = indexCalHandlerLogService.getCalLogByCodeAndHandlerNameAndInterval(indexCode, handlerName, intervalId);
        Result<IndexCalHandlerLogEntity> result = new Result<>();
        result.setData(calLogByCodeAndHandlerNameAndInterval);
        return result;
    }

    @PostMapping(value = "/saveJobLog")
    public Result<IndexCalHandlerLogEntity> saveJobLog(@RequestBody String logJson) {
        final IndexCalHandlerLogEntity indexCalHandlerLogEntity = JSONObject.parseObject(logJson, IndexCalHandlerLogEntity.class);
        boolean res = indexCalHandlerLogService.save(indexCalHandlerLogEntity);
        Result<IndexCalHandlerLogEntity> result = new Result<>();
        if(res) {
            result.setResp_msg("success");
            result.setData(indexCalHandlerLogEntity);
        } else {
            result.setResp_msg("failed");
        }
        return result;
    }

    @PostMapping(value = "/saveJobHandler")
    public Result<IndexCalHandlerDefineEntity> saveJobHandler(@RequestBody String jobHandlerJson) {
        final IndexCalHandlerDefineEntity indexCalHandlerDefineEntity = JSONObject.parseObject(jobHandlerJson, IndexCalHandlerDefineEntity.class);
        boolean res = indexCalHandlerDefineService.save(indexCalHandlerDefineEntity);
        Result<IndexCalHandlerDefineEntity> result = new Result<>();
        if(res) {
            result.setResp_msg("success");
            result.setData(indexCalHandlerDefineEntity);
        } else {
            result.setResp_msg("failed");
        }
        return result;
    }

    @PostMapping(value = "/fetchDimensionDefinition/{dimension_def_id}")
    public Result<IndexManageDimensionDefinition> fetchDimensionDefinition(@PathVariable("dimension_def_id")Long dimensionDefId) {
        Result<IndexManageDimensionDefinition> result = new Result<>();
        final IndexManageDimensionDefinition indexManageDimensionDefinition = indexManageDimensionDefinitionService.findById(dimensionDefId);
        if(indexManageDimensionDefinition != null) {
            result.setResp_msg("success");
            result.setData(indexManageDimensionDefinition);
        } else {
            result.setResp_msg("failed");
        }
        return result;
    }

    @PostMapping("/fetchIndexManageForCal/{index_id}")
    public Result<IndexManageIndexForCalEntity> fetchIndexManageForCal(@PathVariable("index_id") Long indexId) {
        Result<IndexManageIndexForCalEntity> result = new Result<>();
        QueryWrapper<IndexManageIndexForCalEntity> queryWrapper = new QueryWrapper<>();
        IndexManageIndexForCalEntity param = new IndexManageIndexForCalEntity();
        param.setIndexId(indexId);
        queryWrapper.setEntity(param);
        final IndexManageIndexForCalEntity one = indexManageIndexForCalService.getOne(queryWrapper);
        if(one != null) {
            result.setData(one);
            result.setResp_msg("true");
        } else {
            result.setResp_msg("false");
        }
        return result;
    }

}
