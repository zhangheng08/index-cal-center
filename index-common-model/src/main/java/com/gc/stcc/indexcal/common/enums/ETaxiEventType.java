package com.gc.stcc.indexcal.common.enums;



/**
 * 区域类型枚举的定义
 * 
 * @author win7
 *
 */
public enum ETaxiEventType {

	执法案件(-1),私自揽客案件(0),拒绝载客案件(1),与乘客议价案件(2),故意绕行案件(3);
	
	private ETaxiEventType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
	
	  // 普通方法
    public static String getName(int value) {
        for (ETaxiEventType c : ETaxiEventType.values()) {
            if (c.value == value) {
                return c.name();
            }
        }
        return null;
    }
}
