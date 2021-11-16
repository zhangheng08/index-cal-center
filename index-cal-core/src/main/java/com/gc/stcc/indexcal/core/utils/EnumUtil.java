package com.gc.stcc.indexcal.core.utils;

import com.gc.stcc.indexcal.common.model.DimensionValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hsd
 * @date 2020/7/20 10:18
 */
public class EnumUtil {


    /**
     * 根据维度 全类名路径读取维度值
     * @param className
     * @return
     */
    public static List<DimensionValue> getEnmuDimensionValueByClassName(String className) {
        List<DimensionValue> dimensionValueList = new ArrayList<DimensionValue>();
        try {
            Class c2 = Class.forName(className);
            Method getValue = c2.getMethod("value");
            Enum[] enumConstants = (Enum[]) c2.getEnumConstants();
            //遍历枚举
            for (Enum e : enumConstants) {
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(e.name());
                dimensionValue.setValue(String.valueOf(getValue.invoke(e)));
                dimensionValueList.add(dimensionValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dimensionValueList;
    }
}
