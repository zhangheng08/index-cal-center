package com.gc.stcc.indexcal.common.enums;

/**
 * @author Payne08
 */

public enum ECalType {
	HIVE( 0),
	MYSQL(1),
	SPARK(2);
	private ECalType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = -1;
	
	public int value() {
		return this.value;
	} 
}
