package com.gc.stcc.indexcal.core.utils;


import com.gc.stcc.indexcal.common.model.DimensionValue;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author hsd
 * @date 2020/8/10 17:12
 */
public class Descartes {

    public final static String DEFAULT_DIMENSION_VALUE = "-3";


    /**
     * 根据指标维度 的合集 生成 各个维度值（包含默认值的） 指标各个维度笛卡尔积计算基础数据
     * [
     *   [纬度名1:纬度值, 纬度名1:纬度值,纬度名1:纬度值],
     *   [纬度名2:纬度值, 纬度名2:纬度值],
     *   [纬度名3:纬度值, 纬度名3:纬度值,纬度名3:纬度值]
     * ]
     *
     * @param dimensionValeMap
     * @param calColumnNames
     * @return
     */
    public static List<List<String>> getDimvalue(Map<String, List<DimensionValue>> dimensionValeMap, String calColumnNames) {
        List<List<String>> dimValue = new ArrayList<List<String>>();
        List<String> calColumnNameList = Arrays.asList(calColumnNames.replaceAll(" ", "").split(","));
        for (String calColumnName : calColumnNameList) {
            List<String> dimValueTmp = new ArrayList<>();
            // 维度列表增加默认无关值 -3
            dimValueTmp.add(calColumnName + ":" + DEFAULT_DIMENSION_VALUE);
            if(dimensionValeMap.containsKey(calColumnName)) {
                List<DimensionValue> dimensionValueList = dimensionValeMap.get(calColumnName);
                for (DimensionValue dimensionValue : dimensionValueList) {
                    dimValueTmp.add(calColumnName + ":" + dimensionValue.getValue());
                }
            }
            dimValue.add(dimValueTmp);
        }
        return dimValue;
    }


    public static List<IndexResultKey> getDescartes(List<List<String>> dimvalue){
        List<IndexResultKey> indexResultKeyList = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        descartes(dimvalue, result, 0, new ArrayList<>());
        for (List<String> stringList : result) {
            indexResultKeyList.add(new IndexResultKey(stringList.toString().replaceAll(" ", "")));
        }
        return indexResultKeyList;
    }

    /**
     * 求笛卡尔积合
     * @param dimvalue
     * @param result
     * @param layer
     * @param curList
     */
    private static void descartes(List<List<String>> dimvalue, List<List<String>> result, int layer, List<String> curList) {
        if (layer < dimvalue.size() - 1) {
            if (dimvalue.get(layer).size() == 0) {
                descartes(dimvalue, result, layer + 1, curList);
            } else {
                for (int i = 0; i < dimvalue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimvalue.get(layer).get(i));
                    descartes(dimvalue, result, layer + 1, list);
                }
            }
        } else if (layer == dimvalue.size() - 1) {
            if (dimvalue.get(layer).size() == 0) {
                result.add(curList);
            } else {
                for (int i = 0; i < dimvalue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimvalue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }

    private static class Prefix<T> {
        final T value;
        final Prefix<T> parent;

        Prefix(Prefix<T> parent, T value) {
            this.parent = parent;
            this.value = value;
        }

        // put the whole prefix into given collection
        <C extends Collection<T>> C addTo(C collection) {
            if (parent != null) {
                parent.addTo(collection);
            }
            collection.add(value);
            return collection;
        }
    }

    private static <T, C extends Collection<T>> Stream<C> comb(List<? extends Collection<T>> values, int offset, Prefix<T> prefix, Supplier<C> supplier) {
        if (offset == values.size() - 1) {
            return values.get(offset).stream().map(e -> new Prefix<>(prefix, e).addTo(supplier.get()));
        }
        return values.get(offset).stream().flatMap(e -> comb(values, offset + 1, new Prefix<>(prefix, e), supplier));
    }


    /**
     * 计算笛卡尔积
     * @param values
     * @param supplier
     * @param <T>
     * @param <C>
     * @return
     */
    public static <T, C extends Collection<T>> Stream<C> ofCombinations(Collection<? extends Collection<T>> values, Supplier<C> supplier) {
        if (values.isEmpty()) {
            return Stream.empty();
        }
        return comb(new ArrayList<>(values), 0, null, supplier);
    }
}
