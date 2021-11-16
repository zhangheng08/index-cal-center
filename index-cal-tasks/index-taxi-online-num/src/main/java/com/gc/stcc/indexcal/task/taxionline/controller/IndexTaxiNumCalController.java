package com.gc.stcc.indexcal.task.taxionline.controller;

import com.gc.stcc.indexcal.task.taxionline.job.IndexTaxiOnlineNumCalJob;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: IndexTaxiOnlineNumCalController
 * @Author: ZhangHeng
 * @Date: 2021/6/29 16:21
 * @Description:
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping(path = "/taxiNum")
public class IndexTaxiNumCalController {

    @Autowired
    private IndexTaxiOnlineNumCalJob indexTaxiOnlineNumCalJob;

    @RequestMapping(value = "/jobV1/foo")
    public String fooMethod() {
        return indexTaxiOnlineNumCalJob.getClass().getName();
    }

    /**
     * @desc 通过web调用执行job
     * @author zhangheng
     * @date 2021/7/5
     * @param params:
     * @return: com.xxl.job.core.biz.model.ReturnT<java.lang.String>
     */
    @GetMapping(value = "/jobV1/{params}")
    public ReturnT<Object> calJobMain(@PathVariable("params")String params) {
        return indexTaxiOnlineNumCalJob.startCalculate(params);
    }

}
