package com.gc.stcc.indexcal.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: BaseVo
 * Author:  Payne08
 * Date: 2021/6/2 10:40
 * Description: base vo
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 * @author Payne08
 */
@Data
@NoArgsConstructor
public class BaseVo {

    public boolean status;
    public String resultMessage;
    public Object resultData;
    public String timestamp;

}
