package com.gc.stcc.indexcal.core.utils;

import lombok.Data;
import org.apache.commons.beanutils.ConvertUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author hsd
 * @date 2020/8/12 9:45
 */

/**
 * 指标数据的key值
 */
@Data
public class IndexResultKey implements Serializable {

    public static final char UNDERLINE = '_';
    // [维度名1:维度值，维度名2:纬度值, 维度名3:维度值]
    private String key;

    public IndexResultKey(String key){
        this.key = key;
    }


    public IndexResultKey(String calColumnNames, Object o, Class clazz) {
        StringBuilder key = new StringBuilder("[");
        String[] columnStr = calColumnNames.replaceAll(" ", "").split(",");
        for (String column : columnStr) {
            String value = ClassUtil.getValue(clazz, o, column);
            if(StringUtil.isNotEmpty(value)){
                key.append(column+":"+value+",");
            }
        }
        key.deleteCharAt(key.length()-1).append("]");
        this.key = key.toString();
    }


    public <T> T setValueByKey(T t, Class<T> clazz, String fixedColmun, String fixedValue){
        fixedValue = StringUtil.isEmpty(fixedValue)?"0":fixedValue;
        // 解析key值 并赋值给对象T
        String key = this.key;
        // [维度名1:维度值,维度名2:纬度值,维度名3:维度值]
        List<String> dimAndValue = Arrays.asList(key.replaceAll(" ", "")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "").split(","));
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //强制注入,越过访问修饰符
            field.setAccessible(true);
            if(field.getName().equalsIgnoreCase(StringUtil.underlineToCamel(fixedColmun))){
                Object convert = ConvertUtils.convert(fixedValue, field.getType());
                try {
                    field.set(t, convert);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            for (String dv : dimAndValue) {
                String dim = dv.split(":")[0];
                String value = dv.split(":")[1];
                if(field.getName().equalsIgnoreCase(StringUtil.underlineToCamel(dim))){
                    Object convert = ConvertUtils.convert(value, field.getType());
                    try {
                        field.set(t, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) { return false;}
        IndexResultKey that = (IndexResultKey) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }


}
