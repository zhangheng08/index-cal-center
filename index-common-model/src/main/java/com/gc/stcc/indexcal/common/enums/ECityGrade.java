package com.gc.stcc.indexcal.common.enums;


/**
 * 区域类型枚举的定义
 * 
 * @author win7
 *
 */
public enum ECityGrade {

	全路网(-1),快速路(3),主干道(2),次干道(1),支路(0);
	
	private ECityGrade(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
	
	  // 普通方法
    public static String getName(int value) {
        for (ECityGrade c : ECityGrade.values()) {
            if (c.value == value) {
                return c.name();
            }
        }
        return null;
    }
}
