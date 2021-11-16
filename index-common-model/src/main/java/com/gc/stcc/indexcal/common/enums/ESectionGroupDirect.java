package com.gc.stcc.indexcal.common.enums;

public enum ESectionGroupDirect {
	上行(0), 
	下行(1);
	private ESectionGroupDirect(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = 0;
	
	public int value() {
		return this.value;
	} 
}
