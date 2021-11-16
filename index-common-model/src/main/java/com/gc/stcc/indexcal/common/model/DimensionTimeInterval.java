package com.gc.stcc.indexcal.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DimensionTimeInterval
 * @Author: ZhangHeng
 * @Date: 2021/7/8 14:41
 * @Description:
 * @Version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DimensionTimeInterval {

    Integer intervalId;
    String name;
    String code;
    Integer activate;
    Double value;
    Integer type;

}
