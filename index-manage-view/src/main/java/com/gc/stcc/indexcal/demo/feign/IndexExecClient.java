package com.gc.stcc.indexcal.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Payne08
 */
@FeignClient(name="index-calculate-executor")
public interface IndexExecClient {

    @RequestMapping(value = "/hive2/table/desc")
    List fetchTableDesc(@RequestParam("tableName")String tableName);

    @RequestMapping(value = "/hive2/table/list")
    List fetchTableList();

    @RequestMapping(value = "/timeInterval/all")
    List fetchDimTimeInterval();

}
