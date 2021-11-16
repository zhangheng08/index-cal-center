package com.gc.stcc.indexcal.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 指标管理 指标值基础类
 *
 * @author hsd
 * @date 2020/7/16 14:13
 */
@Data
public class DimensionValue implements Serializable {

    /**
     * 维度值名称
     */
    private String name;

    /**
     * 维度值
     */
    private String value;
}
