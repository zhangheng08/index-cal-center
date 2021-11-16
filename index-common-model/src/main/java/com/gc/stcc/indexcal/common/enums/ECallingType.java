package com.gc.stcc.indexcal.common.enums;

public enum ECallingType {
	
	电召(0),电话(1),APP(2);
	
	//其它领域依次类推,建立枚举
	
	private ECallingType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = 0;
	
	public int value() {
		return this.value;
	}
}
