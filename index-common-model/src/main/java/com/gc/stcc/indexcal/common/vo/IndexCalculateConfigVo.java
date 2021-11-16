package com.gc.stcc.indexcal.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: IndexCalculteConfigVo
 * Author:  Payne08
 * Date: 2021/6/1 8:56
 * Description: 指标计算配置信息
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 * @author Payne08
 */
@Data
@NoArgsConstructor
public class IndexCalculateConfigVo extends BaseVo {

    private String indexCode;
    private String factTableName;
    private String tenantId;
    private String aggMethod;
    private String aggField;
    private List<IndexCalculateConfigVo.DimConfig> configList;

    @Data
    @NoArgsConstructor
    public static class DimConfig {
        private int dimId;
        private String dimTable;
        private String dimColumn;
        private String[] attach;
        private Integer[] logic;
    }

}
