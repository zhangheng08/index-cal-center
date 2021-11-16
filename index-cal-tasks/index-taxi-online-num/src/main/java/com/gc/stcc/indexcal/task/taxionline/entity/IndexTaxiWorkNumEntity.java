package com.gc.stcc.indexcal.task.taxionline.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: IndexTexiWorkNumEntity
 * @Author: ZhangHeng
 * @Date: 2021/7/1 14:13
 * @Description:
 * @Version:
 */
@Data
public class IndexTaxiWorkNumEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private String id;
    /**
     * 时间间隔-年
     */
    private String year;
    /**
     * 时间间隔-季度
     */
    private String quarter;
    /**
     * 时间间隔-月
     */
    private String month;
    /**
     * 时间间隔-日
     */
    private String day;

    private String min5;
    /**
     * ext1
     */
    private String ext1;
    /**
     * ext2
     */
    private String ext2;
    /**
     * 总数
     */
    private Integer num;

    private String tenantId;

    private String version;

}
