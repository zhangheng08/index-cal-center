package com.gc.stcc.indexcal.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: IndexManageSparkJobConfig
 * Author:  Payne08
 * Date: 2021/6/10 15:48
 * Description: spark job config
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 * @author Payne08
 */
@Data
@TableName("index_manage_spark_job_config")
public class IndexManageSparkJobConfig {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("jar_path")
    private String jarPath;
    @TableField("master")
    private String master;
    @TableField("deploy_mode")
    private String deployMode;
    @TableField("driver_memory")
    private Integer driverMemory;
    @TableField("executor_memory")
    private Integer executorMemory;
    @TableField("executor_cores")
    private Integer executorCores;
    @TableField("other_params")
    private String otherParams;
    @TableField("main_class")
    private String mainClass;

}
