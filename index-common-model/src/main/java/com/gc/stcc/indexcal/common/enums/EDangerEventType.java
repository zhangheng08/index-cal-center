package com.gc.stcc.indexcal.common.enums;

/**
 * 区域类型枚举的定义
 * @author win7
 *
 */
public enum EDangerEventType {
	
	全部(-1),
	
	超速(0),
	
	疲劳驾驶(1),
	
	紧急报警(2);
	
	private EDangerEventType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = 0;
	
	public int value() {
		return this.value;
	}
}
