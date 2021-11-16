package com.gc.stcc.indexcal.common.enums;


/**
 * 拥堵等级枚举的定义
 * 
 * @author ww
 *
 */
public enum EJamGrade {

	畅通(1),基本畅通(2),轻微拥堵(3),中度拥堵(4),严重拥堵(5);
	
	private EJamGrade(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
	
	  // 普通方法
    public static String getName(int value) {
        for (EJamGrade c : EJamGrade.values()) {
            if (c.value == value) {
                return c.name();
            }
        }
        return null;
    }
}
