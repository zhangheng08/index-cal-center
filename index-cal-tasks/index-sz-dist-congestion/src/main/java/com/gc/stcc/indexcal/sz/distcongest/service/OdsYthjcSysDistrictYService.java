package com.gc.stcc.indexcal.sz.distcongest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.sz.distcongest.entiy.model.OdsYthjcSysDistrictY;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: OdsYthjcSysDistrictYService
 * @Author: ZhangHeng
 * @Date: 2021/7/26 9:46
 * @Description:
 * @Version:
 */
public interface OdsYthjcSysDistrictYService extends IService<OdsYthjcSysDistrictY> {
    /**
     * @desc
     * @author zhangheng
     * @date 2021/7/26
     * @param distId:
     * @return: java.lang.String
     */
    String getDistNameById(String distId);
}
