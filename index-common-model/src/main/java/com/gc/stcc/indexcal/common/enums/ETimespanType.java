package com.gc.stcc.indexcal.common.enums;

/**
 * 时间段的枚举定义
 * 
 * @author win7
 *
 */
public enum ETimespanType {

	全天(0),
	
	早高峰(1),
	
	晚高峰(2),
	
	当前(3),
	
	日高峰(4),
	
	日平峰(5);
	
	private ETimespanType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
}
