package com.gc.stcc.indexcal.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DimensionDefinitionVo
 * @Author: ZhangHeng
 * @Date: 2021/6/30 11:14
 * @Description:
 * @Version:
 */
@Data
public class DimensionDefinitionVo {

    private List<DimensionLayerInfoBean> dimensionLayerInfo;

    @Data
    public static class DimensionLayerInfoBean {

        private String dimensionType;

        private List<DimensionGroupBean> dimensionGroup;

        @Data
        public static class DimensionGroupBean {

            private String dimensionTable;
            private String dimensionDesc;
            private String dimensionColumn;
            private String dimensionCode;
            private int stratum;

        }
    }

}
