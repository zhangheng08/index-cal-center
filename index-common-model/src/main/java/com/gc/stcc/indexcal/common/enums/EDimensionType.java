package com.gc.stcc.indexcal.common.enums;

public enum EDimensionType {

	Enum("enum"),
	字典("dictionary"),
	数据库("table");

	private EDimensionType(String value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private String value = "";
	
	public String value() {
		return this.value;
	}
}
