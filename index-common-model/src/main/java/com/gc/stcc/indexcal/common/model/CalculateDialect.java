package com.gc.stcc.indexcal.common.model;

import com.gc.stcc.indexcal.common.enums.ECalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: CalculateDialect
 * @Author: ZhangHeng
 * @Date: 2021/7/8 9:49
 * @Description:
 * @Version:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CalculateDialect {

    private ECalType calType;

    String intervalName;

    String startTime;

    String endTime;

    String dimension;

    String originTable;

    List<String> sqlStatements;

}
