package com.gc.stcc.indexcal.common.enums;

/**
 * @author changcheng
 * @since 2020-07-14 11:26
 */
public enum ECommonDimensions {

    与该维度无关(-3);

    private ECommonDimensions(int value) { // 必须是private的，否则编译错误
        this.value = value;
    }

    private int value = 0;

    public int value() {
        return this.value;
    }
}
