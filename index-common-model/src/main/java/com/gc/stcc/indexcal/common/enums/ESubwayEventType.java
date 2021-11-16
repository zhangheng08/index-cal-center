package com.gc.stcc.indexcal.common.enums;

/**
 * 普通公路事件等级枚举的定义
 * 
 * @author win7
 *
 */
public enum ESubwayEventType {
	
	全部(-1),列车脱轨(1),列车冲突(2),人员伤亡(3), 火灾(4),突发大客流(5),设备故障(6);
	private ESubwayEventType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
}
