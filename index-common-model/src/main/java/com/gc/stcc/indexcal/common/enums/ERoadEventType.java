package com.gc.stcc.indexcal.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通公路事件类型枚举的定义
 * 
 * @author win7
 *
 */
public enum ERoadEventType {

	全部(-1),养护事件(1),路政事件(2),阻断事件(3);
	
	private ERoadEventType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
	
	  // 普通方法
    public static String getName(int value) {
        for (ERoadEventType c : ERoadEventType.values()) {
            if (c.value == value) {
                return c.name();
            }
        }
        return null;
    }
    
}
