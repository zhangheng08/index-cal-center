package com.gc.stcc.indexcal.common.enums;

/**
 * @author Payne08
 */

public enum EBusDirect {
	双向(-1), 
	上行(0), 
	下行(1);
	private EBusDirect(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = -1;
	
	public int value() {
		return this.value;
	} 
}
