package com.gc.stcc.indexcal.common.enums;

/**
 * 危险车辆类型枚举的定义
 * 
 * @author win7
 *
 */
public enum EDangerType {

	全部(1), 腐蚀性(2), 放射性(3),易燃易爆(4),其它(5);
	
	private EDangerType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 1;

	public int value() {
		return this.value;
	}
}
