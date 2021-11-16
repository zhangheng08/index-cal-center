package com.gc.stcc.indexcal.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DimensionProvinceCity
 * @Author: ZhangHeng
 * @Date: 2021/7/9 17:34
 * @Description:
 * @Version:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimensionProvinceCity {

    private int pid;
    private String pname;
    private int cid;
    private String cname;

}
