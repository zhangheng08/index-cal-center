package com.gc.stcc.indexcal.common.enums;

/**
 * 普通公路事件等级枚举的定义
 * 
 * @author win7
 *
 */
public enum ESubwayEventLevel {
	
	全部(-1),特大运营事件(1),重大运营事件(2),较大运营事件(3), 一般运营事件(4);
	
	private ESubwayEventLevel(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
}
