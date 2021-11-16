package com.gc.stcc.indexcal.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: DimTimeIntervalVo
 * Author:  Payne08
 * Date: 2021/6/2 14:42
 * Description: dim time interval vo
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 * @author Payne08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimTimeIntervalVo extends BaseVo {

    Double intervalId;
    String name;
    Double activate;
    Double value;
    Double type;

}
