package com.gc.stcc.manager.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: TestCase01
 * Author:  Payne08
 * Date: 2021/6/7 19:17
 * Description: test
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 */
public class TestCase01 {

    @Test
    public void testJava() {
        String str = "year";
        String[] strings = str.split(",");
        Assert.assertTrue(strings[0].equals(str));
    }

}
