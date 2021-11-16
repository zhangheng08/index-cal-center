package com.gc.stcc.indexcal.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gc.stcc.indexcal.common.vo.Result;
import com.gc.stcc.indexcal.core.utils.DataSourceConstants;
import com.gc.stcc.indexcal.manager.config.DwdDbType;
import com.gc.stcc.indexcal.manager.service.DwdService;
import com.gc.stcc.indexcal.manager.service.impl.DwdServiceHiveImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: StandardModDwdController
 * @Author: ZhangHeng
 * @Date: 2021/7/15 17:16
 * @Description:
 * @Version:
 */
@Api("StandardModDwdController")
@RestController
@RequestMapping(path = "/dwd_model")
public class DwdController {

    private DwdDbType dwdDbType;

    @Qualifier("HIVE")
    private DwdService dwdHiveService;

    private DwdService dwdService;

    @Autowired
    public DwdController(DwdDbType dwdDbType, @Qualifier("HIVE") DwdService dwdHiveService) {

        this.dwdDbType = dwdDbType;

        this.dwdHiveService = dwdHiveService;

        switch (dwdDbType.getType()) {
            case DataSourceConstants.DS_DB_HIVE:
                this.dwdService = this.dwdHiveService;
                break;
            case DataSourceConstants.DS_DB_MYSQL:
            default:
                break;
        }
    }

    @ApiOperation(value = "获取时间间隔维度")
    @PostMapping(value = "/dim/timeIntervals")
    public Result<List<Map<String, Object>>> fetchTimeIntervalCode() {
        return dwdService.fetchTimeIntervalCode();
    }

    @ApiOperation(value = "获取dwd层维度表")
    @PostMapping(value = "/dim/list")
    public Result<List<Object>> fetchDimTables() {

        return dwdService.fetchDimTables();
    }

    @ApiOperation(value = "获取dwd层事实表")
    @PostMapping(value = "/fact/list")
    public Result<List<Object>> fetchFactTables() {

        return dwdService.fetchFactTables();
    }

    @ApiOperation(value = "获取维度选项")
    @PostMapping(value = "dim/select/{tabName}")
    public Result fetchDimTableFields(@PathVariable("tabName") String tabName) {

        return dwdService.fetchDimTableFields(tabName);
    }

    @PostMapping(value = "/createFactTable")
    public Result createDwdFactTable(@RequestBody String createConfigJsonStr) {

        return dwdService.createDwdFactTable(createConfigJsonStr);
    }

    @ApiOperation(value = "获取表字段")
    @PostMapping(value = "table/fields")
    public Result fetchDwdTableFields(@RequestBody String dbJsonStr) {

        return dwdService.fetchDwdTableFields(dbJsonStr);
    }

}
