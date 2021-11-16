package com.gc.stcc.indexcal.core.utils;

import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author hsd
 * @date 2020/8/13 9:32
 */
public class ClassUtil {

    public static final String INDEX_COLUMN_NAME_CREATE_DATE = "CREATEDATE";
    public static final String INDEX_COLUMN_NAME_OWNER_ID = "OWNERID";
    public static final String INDEX_COLUMN_NAME_INTERVAL_ID = "INTERVALID";

    public static Method getMethodIgnoreCase(Class clazz, String methodName){
        for (Method method : clazz.getMethods()) {
            if(method.getName().toUpperCase().equals(methodName.toUpperCase())){
                return method;
            }
        }
        return null;
    }

    public static String getValue(Class clazz, Object o, String column){
        String methodName = "get"+ StringUtil.underlineToCamel(column);
        String value = "";
        Method method = ClassUtil.getMethodIgnoreCase(clazz, methodName);
        if(method!=null){
            try {
                value = String.valueOf(method.invoke(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 赋值
     * @param clazz
     * @param o
     * @param column
     * @param value
     */
    public static void setValue(Class clazz, Object o, String column, Object value){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //强制注入,越过访问修饰符
            field.setAccessible(true);
            if(field.getName().equalsIgnoreCase(column)){
                /**
                 * 1. 利用Apache Commons Beanutils类库中的  ConvertUtils 工具类进行对应类型的转换
                 * 2. field.getType()获取属性的类型
                 * 3. 注意在转化之前,要把原值变成String类型 , 虽然不换也可以,还有重载方法 , 但尽量还是转一下
                 * 4. ConvertUtils.convert 还可以强转数组等
                 * 5. 返回值为了兼容所以是Object,但其实内部已经强转了
                 */
                Object convert = ConvertUtils.convert(value.toString(), field.getType());
                try {
                    field.set(o, convert);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 赋值
     * @param clazz
     * @param o
     */
    public static void setValue(Class clazz, Object o, Map<String, Object> sqlResult){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //强制注入,越过访问修饰符
            field.setAccessible(true);
            for (String key : sqlResult.keySet()) {
                if(field.getName().equalsIgnoreCase(StringUtil.underlineToCamel(key))){
                    /**
                     * 1. 利用Apache Commons Beanutils类库中的  ConvertUtils 工具类进行对应类型的转换
                     * 2. field.getType()获取属性的类型
                     * 3. 注意在转化之前,要把原值变成String类型 , 虽然不换也可以,还有重载方法 , 但尽量还是转一下
                     * 4. ConvertUtils.convert 还可以强转数组等
                     * 5. 返回值为了兼容所以是Object,但其实内部已经强转了
                     */
                    Object convert = ConvertUtils.convert(sqlResult.get(key).toString(), field.getType());
                    try {
                        if(field.getType() == Date.class) {
                            convert = DateUtil.str2Date(String.valueOf(convert), DateUtil.DEFAULT_DATE_TIME_FORMAT);
                        }
                        field.set(o, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 指标默认实例化
     * @param clazz
     * @param createDate
     * @param ownerId
     * @param columns
     * @param defaultValue
     */
    public static Object defaultIndexInstance(Class clazz, Date createDate, String ownerId, String intervalId, String columns, String defaultValue) {

        Object instance = null;

        List<String> columnsNames = Arrays.asList(columns.replaceAll(" ", "").replace("_", "").split(","));

        try {

            instance = clazz.newInstance();

            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                //强制注入,越过访问修饰符
                field.setAccessible(true);
                // Index 默认会有intervalId createDate 和 ownerId 3个值
                if (field.getName().equalsIgnoreCase(INDEX_COLUMN_NAME_CREATE_DATE)) {
                    field.set(instance, createDate);
                }
                if (field.getName().equalsIgnoreCase(INDEX_COLUMN_NAME_INTERVAL_ID)) {
                    Object convert = ConvertUtils.convert(intervalId, field.getType());
                    field.set(instance, convert);
                }
                if (field.getName().equalsIgnoreCase(INDEX_COLUMN_NAME_OWNER_ID)) {
                    Object convert = ConvertUtils.convert(ownerId, field.getType());
                    field.set(instance, convert);
                }
                // columns
                for (String columnsName : columnsNames) {
                    if (field.getName().equalsIgnoreCase(columnsName)) {
                        Object convert = ConvertUtils.convert(defaultValue, field.getType());
                        field.set(instance, convert);
                    }
                }
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return instance;

    }

    /**
     * 数据返回数据 动态生成为默认的指标数据集合
     * @param clazz
     * @param sqlResultMapList
     * @param resultDate
     * @param ownerId
     * @param calColumnNames
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> List<T> getIndexResultListBySqlResult(Class<T> clazz, List<Map<String, Object>> sqlResultMapList, Date resultDate, String ownerId, String intervalId, String calColumnNames, String defaultValue) {
        List<T> sqlResultList = new ArrayList<>();
        for (Map<String, Object> stringObjectLinkedHashMap : sqlResultMapList) {
            T t = (T) ClassUtil.defaultIndexInstance(clazz, resultDate, ownerId, intervalId, calColumnNames, defaultValue);
            ClassUtil.setValue(clazz, t, stringObjectLinkedHashMap);
            sqlResultList.add(t);
        }
        return sqlResultList;
    }
}
