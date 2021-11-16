package com.gc.stcc.indexcal.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域类型枚举的定义
 * 
 * @author win7
 *
 */
public enum EIndexDictionaryType {

	城市路网事件状态(1),
	普通公路事件类型(2),
	城市路网事件类型(3), 
	普通公路事件等级(4),
	普通公路事件状态(5),
	高速公路事件原因(6),
	高速公路事件状态(7),
	城市路网事件原因(8),
	地面公交事件类型(9),
	班线客运事件类型(10),
	出租汽车事件类型(11),
	旅游包车事件类型(12),
	危险品运输事件类型(13),
	校车事件类型(14),
	应急指挥车事件类型(15),
	执法车辆事件类型(16),
	铁路客运事件类型(17);
	private EIndexDictionaryType(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	private int value = 0;

	public int value() {
		return this.value;
	}
	
	  // 普通方法
    public static String getName(int value) {
        for (EIndexDictionaryType c : EIndexDictionaryType.values()) {
            if (c.value == value) {
                return c.name();
            }
        }
        return null;
    }
    
}
