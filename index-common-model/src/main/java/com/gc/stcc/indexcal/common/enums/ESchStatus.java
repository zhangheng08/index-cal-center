package com.gc.stcc.indexcal.common.enums;

public enum ESchStatus {
	
	售票(0),
	检票(1),
	发班(2),
	停班(3),
	停售(4);
	
	private ESchStatus(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = -1;
	
	public int value() {
		return this.value;
	}
}
