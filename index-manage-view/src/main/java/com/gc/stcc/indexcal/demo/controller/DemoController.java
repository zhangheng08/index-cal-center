package com.gc.stcc.indexcal.demo.controller;

import com.gc.stcc.indexcal.common.vo.BaseVo;
import com.gc.stcc.indexcal.demo.feign.IndexExecClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: DemoController
 * Author:  Payne08
 * Date: 2021/6/1 14:57
 * Description: demo
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 * @author Payne08
 */
@Controller
@RequestMapping(path = "/demo")
public class DemoController {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private IndexExecClient indexExecClient;

    @RequestMapping(value = "/1", method = RequestMethod.GET)
    public ModelAndView welcomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/views/first_page");
        return modelAndView;
    }

    @RequestMapping(value = "/2", method = RequestMethod.GET)
    public ModelAndView commonPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/views/common_page");
        return modelAndView;
    }

    @RequestMapping(value = "/columnInfo/{tableName}")
    public @ResponseBody BaseVo fetchTableColumnsInfoes(@PathVariable("tableName")String tabeName) throws Exception {
        BaseVo baseVo = new BaseVo();
        List list = indexExecClient.fetchTableDesc(tabeName);
        baseVo.setStatus(true);
        baseVo.setResultData(list);
        baseVo.setResultMessage("success");
        baseVo.setTimestamp(SDF.format(new Date()));
        return baseVo;
    }

    @RequestMapping(value = "/fetchTableList")
    public @ResponseBody BaseVo fetchTableList() throws Exception {
        BaseVo baseVo = new BaseVo();
        List list = indexExecClient.fetchTableList();
        baseVo.setStatus(true);
        baseVo.setResultData(list);
        baseVo.setResultMessage("success");
        baseVo.setTimestamp(SDF.format(new Date()));
        return baseVo;
    }

    @RequestMapping(value = "/dim/timeInterval")
    public @ResponseBody BaseVo fetchDimTimeInterval() {
        BaseVo baseVo = new BaseVo();
        List list = indexExecClient.fetchDimTimeInterval();
        baseVo.setStatus(true);
        baseVo.setResultData(list);
        baseVo.setResultMessage("success");
        baseVo.setTimestamp(SDF.format(new Date()));
        return baseVo;
    }

}
