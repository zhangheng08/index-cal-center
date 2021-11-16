package com.gc.stcc.indexcal.common.enums;

public enum EDateFeature {
	全部(-1),
	工作日(1),
	节假日(2),
	周末(3),
	非工作日(4);
	
	private EDateFeature(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = -1;
	
	public int value() {
		return this.value;
	}
}
