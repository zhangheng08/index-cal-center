package com.gc.stcc.indexcal.common.enums;

/**
 * 公众信息发布的信息类型枚举定义
 * @author win7
 *
 */
public enum EPublishEventType {
	
	其他(-1),
	
	事件(1),
	
	要闻(2),
	
	公告(3);
	
	private EPublishEventType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}
	private int value = 0;
	
	public int value() {
		return this.value;
	}
}
