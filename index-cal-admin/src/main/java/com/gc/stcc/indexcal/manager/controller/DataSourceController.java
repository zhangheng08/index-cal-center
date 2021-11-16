package com.gc.stcc.indexcal.manager.controller;

import com.gc.stcc.indexcal.common.model.DbConnParam;
import com.gc.stcc.indexcal.common.vo.Result;
import com.gc.stcc.indexcal.core.ds2.config.DynamicDataSource;
import com.gc.stcc.indexcal.core.ds2.context.DynamicDataSourceContextHolder;
import com.gc.stcc.indexcal.core.ds2.context.SpringContextHolder;
import com.gc.stcc.indexcal.core.utils.DataSourceUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DataSourceController
 * @Author: ZhangHeng
 * @Date: 2021/8/3 11:27
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping(path = "/ds")
public class DataSourceController {

    @PostMapping(value = "/addDs")
    public Result<List<Map<String, Object>>> addDataSource(@RequestBody DbConnParam dbConnParam) {

        Result<List<Map<String, Object>>> result = new Result<>();

        String newDsKey = System.currentTimeMillis() + "";

        final JdbcTemplate jdbcTemplate = DataSourceUtil.initParamDynamicDbTemplate(newDsKey, dbConnParam);

        final List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from dm_ythjc_dist_congestion_minu_index_cal_test limit 0, 10");

        DynamicDataSource dynamicDataSource = SpringContextHolder.getContext().getBean(DynamicDataSource.class);

        dynamicDataSource.del(newDsKey);

        DynamicDataSourceContextHolder.removeContextKey();

        result.setData(maps);

        result.setResp_msg("true");

        return result;
    }

}
