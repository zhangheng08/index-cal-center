package com.gc.stcc.indexcal.core.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author hsd
 * @date 2020/8/11 11:13
 */
@Data
public class DimensionSqlResult implements Serializable {
    private String dimensionNames;
    private String sql;
    private List<LinkedHashMap<String, Object>> result;
}
