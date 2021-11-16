package com.gc.stcc.indexcal.common.enums;

/**
 * 临江运营区域特征（临时，后期可能使用区域匹配）
 * @author ww
 *
 */
public enum ETaxiDist {
	市内(0), 
	市外(1),
	跨域(2);
	private ETaxiDist(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = -1;
	
	public int value() {
		return this.value;
	} 
}
