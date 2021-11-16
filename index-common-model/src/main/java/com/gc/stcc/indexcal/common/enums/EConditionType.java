package com.gc.stcc.indexcal.common.enums;

/**
 * 摄像头类型、诱导屏
 * 
 * @author win7
 *
 */
public enum EConditionType {

	诱导屏(0),枪机(1),球机(2);
	
	private EConditionType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
	
	  // 普通方法
    public static String getName(int value) {
        for (EConditionType c : EConditionType.values()) {
            if (c.value == value) {
                return c.name();
            }
        }
        return null;
    }
    
}
