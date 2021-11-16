package com.gc.stcc.indexcal.sz.distcongest.entiy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.sz.distcongest.entiy.model.OdsYthjcSysDistrictY;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: OdsYthjcSysDistrictYmapper
 * @Author: ZhangHeng
 * @Date: 2021/7/26 9:45
 * @Description:
 * @Version:
 */
@Component
public interface OdsYthjcSysDistrictYmapper extends BaseMapper<OdsYthjcSysDistrictY> {

    /**
     * @desc
     * @author zhangheng
     * @date 2021/7/26
     * @param distId:
     * @return java.lang.String
     */
    String getDistNameById(@Param("distId") String distId);

}
