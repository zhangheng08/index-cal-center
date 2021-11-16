package com.gc.stcc.indexcal.common.enums;

/**
 * 时间粒度的枚举定义（主要程序识别一天内，天，周，月，季度，年几个常用的粒度 可用于控件的选择等
 * 
 * @author win7
 *
 */
public enum EIntervalType {

	五分钟(1),十五分钟(2),一小时(3),日(4),周(5),月(6), 季度(7),年(8);
	private EIntervalType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}

	public static EIntervalType getEIntervalTypeEnumByCode(Integer value){
		for(EIntervalType intervalId : EIntervalType.values()){
			if(value == intervalId.value()){
				return intervalId;
			}
		}
		return null;
	}
}
