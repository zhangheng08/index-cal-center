package com.gc.stcc.indexcal.common.enums;

public enum EPositionStatus {
	
	//0  空载  1  载客  2  终端脱网
	空载(0),载客(1),终端脱网(2);
	
	//其它领域依次类推,建立枚举
	
	private EPositionStatus(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = 0;
	
	public int value() {
		return this.value;
	}
}
