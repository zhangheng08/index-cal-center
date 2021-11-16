package com.gc.stcc.indexcal.common.enums;

/**
 * @author Payne08
 */

public enum EAppEventType {
	
	其他(-1),
	交通拥堵(1),
	道路施工(2),
	路面损毁(3),
	路面积水(4);
	
	private EAppEventType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = -1;
	
	public int value() {
		return this.value;
	}
}
