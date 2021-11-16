package com.gc.stcc.indexcal.common.enums;

public enum EFlightCountryType {

	国内航班("D"),
	国际航班("I"),
	混合航班("M"),
	返回航班("R");

	private EFlightCountryType(String value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private String value = "";
	
	public String value() {
		return this.value;
	}
}
