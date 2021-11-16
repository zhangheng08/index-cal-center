package com.gc.stcc.indexcal.common.enums;

/**
 * 普通公路事件等级枚举的定义
 * 
 * @author win7
 *
 */
public enum ERoadEventLevel {
	
	全部(-1),轻微案件(1),一般案件(2),重大案件(3), 特大案件(4);
	
	private ERoadEventLevel(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
}
