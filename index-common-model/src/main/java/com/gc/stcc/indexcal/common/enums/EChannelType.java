package com.gc.stcc.indexcal.common.enums;

public enum EChannelType {
	
	高速路网通道(2),
	地面公交通道(4),
	地面公交专用通道(5),
	普通公路通道(7),
	国道(28);
	
	private EChannelType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = -1;
	
	public int value() {
		return this.value;
	}
}
