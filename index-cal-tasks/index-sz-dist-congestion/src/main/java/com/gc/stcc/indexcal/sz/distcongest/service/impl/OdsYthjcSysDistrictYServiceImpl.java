package com.gc.stcc.indexcal.sz.distcongest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.sz.distcongest.entiy.mapper.OdsYthjcSysDistrictYmapper;
import com.gc.stcc.indexcal.sz.distcongest.entiy.model.OdsYthjcSysDistrictY;
import com.gc.stcc.indexcal.sz.distcongest.service.OdsYthjcSysDistrictYService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: OdsYthjcSysDistrictYServiceImpl
 * @Author: ZhangHeng
 * @Date: 2021/7/26 9:47
 * @Description:
 * @Version:
 */
@Service
public class OdsYthjcSysDistrictYServiceImpl extends ServiceImpl<OdsYthjcSysDistrictYmapper, OdsYthjcSysDistrictY> implements OdsYthjcSysDistrictYService {

    @Autowired
    private OdsYthjcSysDistrictYmapper odsYthjcSysDistrictYmapper;

    @Override
    public String getDistNameById(String distId) {
        return odsYthjcSysDistrictYmapper.getDistNameById(distId);
    }
}
