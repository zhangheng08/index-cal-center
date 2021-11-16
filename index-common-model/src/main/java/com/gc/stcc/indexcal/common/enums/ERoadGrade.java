package com.gc.stcc.indexcal.common.enums;

/**
 * 区域类型枚举的定义
 * 
 * @author win7
 *
 */
public enum ERoadGrade {

	全部(-1), 国道(4), 省道(5),县道(6);
	
	private ERoadGrade(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
}
