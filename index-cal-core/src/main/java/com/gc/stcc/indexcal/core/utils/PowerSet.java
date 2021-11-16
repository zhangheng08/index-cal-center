package com.gc.stcc.indexcal.core.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * @author hsd
 * @date 2020/8/11 9:24
 */

public class PowerSet {

    /**
     * 用于sql查询元数据表时 的 group by 查询维度的幂集
     * @param dimensions 维度值集合
     * @return
     * @example
     * param: sectiongroup_id, grade_id, industry_id
     * return:
     * sectiongroup_id, grade_id, industry_id
     * sectiongroup_id, grade_id
     * sectiongroup_id, industry_id
     * sectiongroup_id
     * grade_id, industry_id
     * grade_id
     * industry_id
     */
    public static List<String> getDimensionList(List<String> dimensions){
        List<String> result = new ArrayList<>();
        PowerSet(0, dimensions, new ArrayList<>(), result);
        return result;
    }

    /**
     * 回溯法求幂集
     * 用于sql查询元数据表时 的 group by 查询维度的幂集
     * @param i
     * @param list
     * @param li
     * @param result 返回结果 用于group by
     */
    public static void PowerSet(int i, List<String> list, List<String> li, List<String> result) {
        if (i > list.size() - 1) {
            if(li.size() != 0) {
                result.add(li.toString().replace("[", "").replace("]",""));
            } else {
                /* 空集 */
                result.add("");
            }
        } else {
            /* 左加*/
            li.add(list.get(i));
            /* 递归方法*/
            PowerSet(i + 1, list, li, result);
            /* 右去*/
            li.remove(list.get(i));
            PowerSet(i + 1, list, li, result);
        }
    }

}
