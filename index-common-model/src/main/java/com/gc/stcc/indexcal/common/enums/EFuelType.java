package com.gc.stcc.indexcal.common.enums;

/**
 * 区域类型枚举的定义
 * @author win7
 *
 */
public enum EFuelType {
	
	全部(-1),
	
	汽油(1),
	
	乙醇汽油(2),
	
	柴油(3),
	
	液化石油气(4),
	
	天燃气(5), 
	
	电(6);
	
	private EFuelType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = 0;
	
	public int value() {
		return this.value;
	}
}
