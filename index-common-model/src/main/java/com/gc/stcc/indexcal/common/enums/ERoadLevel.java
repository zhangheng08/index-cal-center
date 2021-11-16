package com.gc.stcc.indexcal.common.enums;

/**
 * 
 * @author cyh
 *
 */
public enum ERoadLevel {

	全部(-1), 特大案件(1), 重大案件(2),一般案件(3),轻微案件(4);
	
	private ERoadLevel(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
}
