package com.gc.stcc.indexcal.common.enums;

/**
 * 时间粒度的枚举定义（主要程序识别一天内，天，周，月，季度，年几个常用的粒度 可用于控件的选择等
 * 
 * @author win7
 *
 */
public enum EClusterFactor {

	工作日(7), 
	周末(8),
	节假日(1), 
	寒假(6), 
	暑假(5), 
	限行(4),
	天气状况(2),
	重大活动(3);
	
	private EClusterFactor(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
}
