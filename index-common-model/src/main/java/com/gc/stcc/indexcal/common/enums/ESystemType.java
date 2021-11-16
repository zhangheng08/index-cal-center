package com.gc.stcc.indexcal.common.enums;

public enum ESystemType {
	
	tocc监测系统(1), tocc辅助决策(2),应急系统(3),交通出行信息服务系统(4);
	
	
	private ESystemType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = 1;
	
	public int value() {
		return this.value;
	}	

}
