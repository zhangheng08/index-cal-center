package com.gc.stcc.indexcal.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gc.stcc.indexcal.common.model.IndexManageIndexEntity;
import com.gc.stcc.indexcal.common.model.IndexManageIndexForCalEntity;
import com.gc.stcc.indexcal.common.vo.Result;
import com.gc.stcc.indexcal.core.utils.DataSourceConstants;
import com.gc.stcc.indexcal.manager.config.DwdDbType;
import com.gc.stcc.indexcal.manager.service.IndexCalHandlerDefineService;
import com.gc.stcc.indexcal.manager.service.IndexManageIndexForCalService;
import com.gc.stcc.indexcal.manager.service.IndexManageIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: AssociationGraphController
 * @Author: ZhangHeng
 * @Date: 2021/8/2 14:28
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping(path = "/graph")
public class AssociationGraphController {

    @Autowired
    private IndexManageIndexService indexManageIndexService;

    @Autowired
    private IndexManageIndexForCalService indexManageIndexForCalService;

    @Autowired
    private IndexCalHandlerDefineService indexCalHandlerDefineService;

    @Autowired
    private DwdController dwdController;


    @PostMapping("/init/{indexCode}/{jobHandler}")
    public String initCalculateGraph(@PathVariable("indexCode")String indexCode, @PathVariable("jobHandler")String jobHandler) {

        final IndexManageIndexEntity indexManageIndexEntity = indexManageIndexService.selectIndexManageIndexEntityByIndexCode(indexCode);
        final IndexManageIndexForCalEntity queryCond = new IndexManageIndexForCalEntity();
        queryCond.setIndexId(indexManageIndexEntity.getId());
        final QueryWrapper<IndexManageIndexForCalEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(queryCond);
        final IndexManageIndexForCalEntity indexForCal = indexManageIndexForCalService.getOne(queryWrapper);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dbName", "stcc_common_model");
        jsonObject.put("tabName", indexForCal.getOriginTableName());
        final Result result = dwdController.fetchDwdTableFields(jsonObject.toJSONString());
        final Result intervals = dwdController.fetchTimeIntervalCode();

        final List<Map<String, Object>> fields  = (List<Map<String, Object>>) result.getData();
        final List<Map<String, Object>> dims = (List<Map<String, Object>>) intervals.getData();

        final Set<String> intervalCodes = new HashSet<>();

        dims.forEach(map -> {
            final String code = (String) map.get("dwd_dim_time_interval.code");
            intervalCodes.add(code);
        });

        final List<Object> allowedTimeIntervals = fields.stream().filter(map -> {
            final String colName = (String) map.get("col_name");
            return intervalCodes.contains(colName);
        }).map(map -> map.get("col_name")).collect(Collectors.toList());

        final List<Object> allowedAggregation = fields.stream().filter(map -> {
            final String colName = (String) map.get("col_name");
            return colName.startsWith("ext");
        }).map(map -> map.get("col_name")).collect(Collectors.toList());

        return String.valueOf(result);
    }


}
