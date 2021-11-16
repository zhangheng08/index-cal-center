package com.gc.stcc.indexcal.common.enums;

public enum EFlightDirection {

	进港("A"),
	出港("D");

	private EFlightDirection(String value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private String value = "";
	
	public String value() {
		return this.value;
	}
}
