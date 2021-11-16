package com.gc.stcc.indexcal.common.enums;

/**
 * 区域类型枚举的定义
 * @author win7
 *
 */
public enum EDistType {
	/**
	 * 行政区域(Dist)
	 */
	行政区域(0),
	
	/**
	 * 重点区域(DistHot)
	 */
	重点区域(1), 
	
	/**
	 * 交通小区DistTraffic
	 */
	交通小区(2),
	
	/**
	 * 环线DistRound
	 * @param value
	 */
	环线区域(3);
	
	private EDistType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = 0;
	
	public int value() {
		return this.value;
	}
}
