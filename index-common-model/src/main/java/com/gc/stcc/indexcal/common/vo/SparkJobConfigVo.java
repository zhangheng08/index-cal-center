package com.gc.stcc.indexcal.common.vo;

import lombok.Data;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: SparkJobConfigVo
 * Author:  Payne08
 * Date: 2021/6/10 16:42
 * Description: spark job config
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 * @author Payne08
 */

@Data
public class SparkJobConfigVo extends BaseVo {

    private Integer id;
    private String jarPath;
    private String master;
    private String deployMode;
    private Integer driverMemory;
    private Integer executorMemory;
    private Integer executorCores;
    private String otherParams;
    private String mainClass;

}
