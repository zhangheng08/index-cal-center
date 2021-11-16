package com.gc.stcc.indexcal.manager.service;

import com.gc.stcc.indexcal.common.vo.Result;

import java.util.List;
import java.util.Map;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DwdService
 * @Author: ZhangHeng
 * @Date: 2021/8/2 15:42
 * @Description:
 * @Version:
 */
public interface DwdService {

    Result<List<Map<String, Object>>> fetchTimeIntervalCode();

    Result<List<Object>> fetchDimTables();

    Result<List<Object>> fetchFactTables();

    Result fetchDimTableFields(String tabName);

    Result createDwdFactTable(String createConfigJsonStr);

    Result fetchDwdTableFields(String dbJsonStr);

}
