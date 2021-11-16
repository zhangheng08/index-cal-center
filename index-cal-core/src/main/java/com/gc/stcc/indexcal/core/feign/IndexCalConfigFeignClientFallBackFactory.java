package com.gc.stcc.indexcal.core.feign;

import com.gc.stcc.indexcal.common.model.*;
import com.gc.stcc.indexcal.common.vo.IndexCalDefineVO;
import com.gc.stcc.indexcal.common.vo.Result;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hsd
 * @date 2020/8/6 17:38
 */
@Slf4j
@Component
public class IndexCalConfigFeignClientFallBackFactory implements FallbackFactory<IndexCalConfigFeignClient> {

    @Override
    public IndexCalConfigFeignClient create(Throwable throwable) {

        return new IndexCalConfigFeignClient() {

            @Override
            public Map<String, List<DimensionValue>> findDimensionValueMapByCode(String indexCode) {
                Map<String, List<DimensionValue>> map = new HashMap<>();
                log.error(indexCode + "异常"+ throwable);
                return map;
            }

            @Override
            public List<IndexCalDefineVO> findIndexCalDefineByCode(String indexCode) {
                List<IndexCalDefineVO> list = new ArrayList<>();
                log.error(indexCode + "异常"+ throwable);
                return list;
            }

            @Override
            public Result<IndexManageDimensionEntity> findDimensions(String traceId, IndexManageDimensionEntity dimension) {
                Result<IndexManageDimensionEntity> result = new Result<>();
                log.error(dimension.toString() + "异常"+ throwable);
                return result;
            }

            @Override
            public Result<IndexCalHandlerDefineEntity> fetchCalDefine(String indexCode, String handlerName) {
                Result<IndexCalHandlerDefineEntity> result = new Result<>();
                log.error(indexCode + "异常"+ throwable);
                return result;
            }

            @Override
            public Result<Boolean> updateCalDefinition(String jsonString) {
                Result<Boolean> result = new Result<>();
                log.error(jsonString + "异常"+ throwable);
                return result;
            }

            @Override
            public Result<IndexCalHandlerLogEntity> fetchJobLog(String indexCode, String handlerName, String intervalId) {
                Result<IndexCalHandlerLogEntity> result = new Result<>();
                log.error(indexCode + "异常"+ throwable);
                return result;
            }

            @Override
            public Result<IndexCalHandlerLogEntity> saveJobLog(String logJson) {
                Result<IndexCalHandlerLogEntity> result = new Result<>();
                log.error(logJson + "异常"+ throwable);
                return result;
            }

            @Override
            public Result<IndexCalHandlerDefineEntity> saveJobHandler(String jobHandlerJson) {
                Result<IndexCalHandlerDefineEntity> result = new Result<>();
                log.error(jobHandlerJson + "异常"+ throwable);
                return result;
            }

            @Override
            public Result<IndexManageDimensionDefinition> fetchDimensionDefinition(Long dimensionDefId) {
                Result<IndexManageDimensionDefinition> result = new Result<>();
                log.error(dimensionDefId + "异常"+ throwable);
                return result;
            }

            @Override
            public Result<IndexManageIndexForCalEntity> fetchIndexManageForCal(Long indexId) {
                Result<IndexManageIndexForCalEntity> result = new Result<>();
                log.error(indexId + "异常"+ throwable);
                return result;
            }


        };
    }
}
