package com.gc.stcc.indexcal.core.ds2.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: SpringContextHolder
 * @Author: ZhangHeng
 * @Date: 2021/8/2 9:22
 * @Description:
 * @Version:
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 返回上下文
     * @return
     */
    public static ApplicationContext getContext() {
        return SpringContextHolder.applicationContext;
    }
}
