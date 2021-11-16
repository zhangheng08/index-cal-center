package com.gc.stcc.indexcal.core.ds2.aop;

import com.gc.stcc.indexcal.common.model.DbConnParam;
import com.gc.stcc.indexcal.core.ds2.config.DynamicDataSource;
import com.gc.stcc.indexcal.core.ds2.context.DynamicDataSourceContextHolder;
import com.gc.stcc.indexcal.core.ds2.context.SpringContextHolder;
import com.gc.stcc.indexcal.core.utils.DataSourceUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DynamicParamDataSourceExecutor
 * @Author: ZhangHeng
 * @Date: 2021/8/3 15:40
 * @Description:
 * @Version:
 */
public class DynamicParamDataSourceExecutor implements InvocationHandler {

    /**
     * 数据源key
     */
    private String dataSourceKey;
    /**
     * 数据库连接信息
     */
    private DbConnParam dbInfo;

    /**
     * 代理目标对象
     */
    private Object targetObject;

    public DynamicParamDataSourceExecutor(Object targetObject, String dataSourceKey, DbConnParam dbInfo) {
        this.targetObject = targetObject;
        this.dataSourceKey = dataSourceKey;
        this.dbInfo = dbInfo;
    }

    /**
     * @desc 每调用一次方法切换一次数据源，执行结果返回后释放数据连接；没有跨库事务；注意方法调用结束后数据源是未指定状态
     * @author zhangheng
     * @date 2021/8/3
     * @param proxy:
     * @param method:
     * @param args:
     * @return: java.lang.Object
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("动态代理:"+method.getName());
        //切换数据源
        DataSourceUtil.addDataSourceToDynamic(dataSourceKey, dbInfo);
        DynamicDataSourceContextHolder.setContextKey(dataSourceKey);
        //调用方法
        Object result = method.invoke(targetObject, args);
        DynamicDataSource dynamicDataSource = SpringContextHolder.getContext().getBean(DynamicDataSource.class);
        dynamicDataSource.del(dataSourceKey);
        DynamicDataSourceContextHolder.removeContextKey();

        return result;
    }

    /**
     * 创建代理 通过代理调用
     * @param targetObject
     * @param dataSourceKey
     * @param dbInfo
     * @return
     * @throws Exception
     */
    public static Object createProxyInstance(Object targetObject, String dataSourceKey, DbConnParam dbInfo) throws Exception {

        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), new DynamicParamDataSourceExecutor(targetObject, dataSourceKey, dbInfo));
    }

}
