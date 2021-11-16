package com.gc.stcc.indexcal.common.enums;

public enum EWeekofDay {
	
	日(1),
	一(2),
	二(3),
	三(4),
	四(5),
	五(6),
	六(7);
	
	private EWeekofDay(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = -1;
	
	public int value() {
		return this.value;
	}
}
