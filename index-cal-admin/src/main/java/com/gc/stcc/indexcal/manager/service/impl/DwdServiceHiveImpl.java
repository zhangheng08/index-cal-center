package com.gc.stcc.indexcal.manager.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gc.stcc.indexcal.common.vo.Result;
import com.gc.stcc.indexcal.manager.service.DwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DwdServiceHiveImpl
 * @Author: ZhangHeng
 * @Date: 2021/8/2 15:45
 * @Description:
 * @Version:
 */
@Service("HIVE")
public class DwdServiceHiveImpl implements DwdService {

    @Autowired
    @Qualifier("hiveTemplate")
    private JdbcTemplate hiveTemplate;

    @Override
    public Result<List<Map<String, Object>>> fetchTimeIntervalCode() {

        Result<List<Map<String, Object>>> result = new Result<>();

        try {
            String sql = "select * from dwd_dim_time_interval";

            final List<Map<String, Object>> maps = hiveTemplate.queryForList(sql);

            result.setData(maps);

            result.setResp_msg("true");

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Result<List<Object>> fetchDimTables() {

        Result<List<Object>> result = new Result<>();

        try {

            String sql = "show tables in stcc_common_model";

            final List<Map<String, Object>> maps = hiveTemplate.queryForList(sql);

            final List<Object> databasesNames = maps.stream().map(map -> map.get("tab_name")).filter(tabName -> String.valueOf(tabName).contains("dwd_dim_")).collect(Collectors.toList());

            result.setData(databasesNames);

            result.setResp_msg("true");

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Result<List<Object>> fetchFactTables() {

        Result<List<Object>> result = new Result<>();

        try {

            String sql = "show tables in stcc_common_model";

            final List<Map<String, Object>> maps = hiveTemplate.queryForList(sql);

            final List<Object> databasesNames = maps.stream().map(map -> map.get("tab_name")).filter(tabName -> !String.valueOf(tabName).contains("dwd_dim_")).collect(Collectors.toList());

            result.setData(databasesNames);

            result.setResp_msg("true");

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Result fetchDimTableFields(String tabName) {

        Result result = new Result();

        try {

            String sql = "desc stcc_common_model.tab";

            sql = sql.replace("tab", tabName);

            final List<Map<String, Object>> maps = hiveTemplate.queryForList(sql);

            final List<Object> dimColumns = maps.stream()
                    .map(map -> map.get("col_name"))
                    .filter(col -> !StringUtils.isEmpty(String.valueOf(col)) && !String.valueOf(col).startsWith("#"))
                    .distinct()
                    .collect(Collectors.toList());

            result.setData(dimColumns);

            result.setResp_msg("true");

        } catch (Exception e) {

            e.printStackTrace();

            result.setResp_msg("false");
        }

        return result;
    }

    @Override
    public Result createDwdFactTable(String createConfigJsonStr) {

        Result result = new Result();

        try {

            final JSONObject jsonObject = JSONObject.parseObject(createConfigJsonStr);

            final String dbName = jsonObject.getString("dbName");

            final String tabName = jsonObject.getString("tabName");

            final JSONArray businessDims = jsonObject.getJSONArray("businessDims");

            final JSONArray calDims = jsonObject.getJSONArray("calDims");

            final JSONArray timeDims = jsonObject.getJSONArray("timeDims");

            List columnList = new ArrayList<>();

            columnList.addAll(calDims);

            columnList.addAll(businessDims);

            columnList.addAll(timeDims);

            final Object compare = columnList.stream().sorted().reduce("", (str1, str2) -> String.valueOf(str1) + "|" + String.valueOf(str2));

            System.out.println(compare);

            String sql = "show tables in stcc_common_model";

            final List<Map<String, Object>> maps = hiveTemplate.queryForList(sql);

            final List<Object> factTabs = maps.stream().map(map -> map.get("tab_name")).filter(tab -> !String.valueOf(tab).contains("dwd_dim_")).collect(Collectors.toList());

            boolean isExist = false;

            for (Object tab : factTabs) {

                String eachSql = "desc stcc_common_model.tab";

                eachSql = eachSql.replace("tab", String.valueOf(tab));

                final List<Map<String, Object>> colMaps = hiveTemplate.queryForList(eachSql);

                final Object colNames = colMaps.stream()
                        .map(map -> map.get("col_name"))
                        .filter(col -> !StringUtils.isEmpty(String.valueOf(col)) && !String.valueOf(col).startsWith("#"))
                        .distinct()
                        .sorted()
                        .reduce("", (str1, str2) -> String.valueOf(str1) + "|" + String.valueOf(str2));

                System.out.println(colNames);

                if(String.valueOf(colNames).contains(String.valueOf(compare))) {

                    isExist = true;

                    break;

                }
            }

            result.setData(Boolean.valueOf(isExist));

            result.setResp_msg(isExist ? "已存在" : "不存在");

        } catch (Exception e) {

            e.printStackTrace();

            result.setResp_msg("false");
        }

        return result;
    }

    @Override
    public Result fetchDwdTableFields(@RequestBody String dbJsonStr) {

        Result result = new Result();

        try {

            final JSONObject jsonObject = JSONObject.parseObject(dbJsonStr);

            final String dbName = jsonObject.getString("dbName");

            final String tabName = jsonObject.getString("tabName");

            if(StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tabName)) {

                throw new Exception("dbName or tabName could not be empty !");

            }

            String sql = "desc db.tab";

            sql = sql.replace("db", dbName).replace("tab", tabName);

            final List<Map<String, Object>> maps = hiveTemplate.queryForList(sql);

            final List<Map<String, Object>> fields = maps.stream().filter(map -> {

                final String colName = (String) map.get("col_name");

                final String dataType = (String) map.get("data_type");

                return !StringUtils.isEmpty(colName) && !StringUtils.isEmpty(dataType) && !colName.startsWith("#");

            }).distinct().collect(Collectors.toList());

            result.setData(fields);

            result.setResp_msg("true");

        } catch (Exception e) {

            e.printStackTrace();

            result.setResp_msg("false");
        }

        return result;
    }

}
