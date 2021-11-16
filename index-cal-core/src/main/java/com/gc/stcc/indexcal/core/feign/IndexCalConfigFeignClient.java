package com.gc.stcc.indexcal.core.feign;

import com.gc.stcc.indexcal.common.model.*;
import com.gc.stcc.indexcal.common.vo.IndexCalDefineVO;
import com.gc.stcc.indexcal.common.vo.Result;
import com.gc.stcc.indexcal.core.config.FeignExceptionConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author hsd
 * @date 2020/8/6 15:05
 */
@FeignClient(value = "index-calculate-manager", configuration = FeignExceptionConfig.class, fallbackFactory = IndexCalConfigFeignClientFallBackFactory.class)
public interface IndexCalConfigFeignClient {

    /**
     * @desc 查询维度定义信息
     * @author zhangheng
     * @date 2021/7/5
     * @param indexCode:
     * @return: java.util.Map<java.lang.String,java.util.List<com.gc.stcc.indexcal.common.model.DimensionValue>>
     */
    @PostMapping("/index/manage/dimension/findDimensionValueMapByCode")
    Map<String, List<DimensionValue>> findDimensionValueMapByCode(@RequestParam(name = "indexCode") String indexCode);

    /**
     * @desc 查询任务定义信息
     * @author zhangheng
     * @date 2021/7/5
     * @param indexCode:
     * @return: java.util.List<com.gc.stcc.indexcal.common.vo.IndexCalDefineVO>
     */
    @PostMapping("/index/manage/index/findIndexCalDefineByCode")
    List<IndexCalDefineVO> findIndexCalDefineByCode(@RequestParam(name = "indexCode") String indexCode);

    /**
     * @desc
     * @author zhangheng
     * @date 2021/7/5
     * @param traceId:
     * @param dimension:
     * @return: com.gc.stcc.indexcal.common.vo.Result
     */
    @PostMapping("/index/manage/dimension/findDimensions")
    Result findDimensions(@RequestHeader(name = "trace_id", required = false) String traceId, @RequestBody IndexManageDimensionEntity dimension);

    /**
     * @desc 查询任务定义信息
     * @author zhangheng
     * @date 2021/7/5
     * @param indexCode:
     * @param handlerName:
     * @param dimensionDefinitionId:
     * @return: com.gc.stcc.indexcal.common.vo.Result<com.gc.stcc.indexcal.common.model.IndexCalHandlerDefineEntity>
     */
    @RequestMapping(value = "/config/fetchCalDefine/{indexCode}/{handlerName}")
    Result<IndexCalHandlerDefineEntity> fetchCalDefine(@PathVariable("indexCode")String indexCode, @PathVariable("handlerName")String handlerName);

    /**
     * @desc
     * @author zhangheng
     * @date 2021/7/9
     * @param jsonString:
     * @return: com.gc.stcc.indexcal.common.vo.Result<java.lang.Boolean>
     */
    @RequestMapping(value = "/config/updateCalDefine")
    Result<Boolean> updateCalDefinition(@RequestBody String jsonString);

    /**
     * @desc 获取任务执行日志
     * @author zhangheng
     * @date 2021/7/5
     * @param indexCode:
     * @param handlerName:
     * @param intervalId:
     * @return: com.gc.stcc.indexcal.common.vo.Result<com.gc.stcc.indexcal.common.model.IndexCalHandlerLogEntity>
     */
    @RequestMapping(value = "/config/fetchJobLog/{indexCode}/{handlerName}/{intervalId}")
    Result<IndexCalHandlerLogEntity> fetchJobLog(@PathVariable("indexCode")String indexCode, @PathVariable("handlerName")String handlerName, @PathVariable("intervalId")String intervalId);

    /**
     * @desc 保存任务执行日志
     * @author zhangheng
     * @date 2021/7/5
     * @param logJson:
     * @return: com.gc.stcc.indexcal.common.vo.Result<com.gc.stcc.indexcal.common.model.IndexCalHandlerLogEntity>
     */
    @PostMapping(value = "/config/saveJobLog")
    Result<IndexCalHandlerLogEntity> saveJobLog(@RequestBody String logJson);

    /**
     * @desc 保存任务定义信息
     * @author zhangheng
     * @date 2021/7/5
     * @param jobHandlerJson:
     * @return: com.gc.stcc.indexcal.common.vo.Result<com.gc.stcc.indexcal.common.model.IndexCalHandlerDefineEntity>
     */
    @PostMapping(value = "/config/saveJobHandler")
    Result<IndexCalHandlerDefineEntity> saveJobHandler(@RequestBody String jobHandlerJson);

    /**
     * @desc 获取维度配置信息
     * @author zhangheng
     * @date 2021/7/5
     * @param dimensionDefId:
     * @return: com.gc.stcc.indexcal.common.vo.Result<com.gc.stcc.indexcal.common.model.IndexManageDimensionDefinition>
     */
    @PostMapping(value = "/config/fetchDimensionDefinition/{dimension_def_id}")
    Result<IndexManageDimensionDefinition> fetchDimensionDefinition(@PathVariable("dimension_def_id")Long dimensionDefId);

    /**
     * @desc
     * @author zhangheng
     * @date 2021/7/22
     * @param indexId:
     * @return: com.gc.stcc.indexcal.common.vo.Result<com.gc.stcc.indexcal.common.model.IndexManageIndexForCalEntity>
     */
    @PostMapping("/config/fetchIndexManageForCal/{index_id}")
    Result<IndexManageIndexForCalEntity> fetchIndexManageForCal(@PathVariable("index_id") Long indexId);

}
