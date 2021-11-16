package com.gc.stcc.indexcal.common.enums;

public enum EHighwayChannelType {
	
	高速路网通道(0),
	全高速路网(2),
	高速路网(1);
	
	private EHighwayChannelType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = -1;
	
	public int value() {
		return this.value;
	}
}
