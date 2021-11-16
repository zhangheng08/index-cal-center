package com.gc.stcc.indexcal.manager.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.IndexCalHandlerLogEntity;
import com.gc.stcc.indexcal.core.utils.ParamParseUtil;
import com.gc.stcc.indexcal.manager.model.mapper.IndexCalHandlerLogDao;
import com.gc.stcc.indexcal.manager.service.IndexCalHandlerLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 指标计算 计算处理记录表
 *
 * @author hsd
 * @email ${email}
 * @date 2020-08-07 15:02:16
 */
@Service("indexCalHandlerLogService")
public class IndexCalHandlerLogServiceImpl extends ServiceImpl<IndexCalHandlerLogDao, IndexCalHandlerLogEntity> implements IndexCalHandlerLogService {

    @Autowired
    private IndexCalHandlerLogDao indexCalHandlerLogDao;

    @Override
    public IndexCalHandlerLogEntity getCalLogByCodeAndHandlerNameAndInterval(String indexCode, String handlerName, String intervalId) {

        IndexCalHandlerLogEntity calLogParams = IndexCalHandlerLogEntity.builder()
                .indexCode(indexCode).handlerName(handlerName)
                .intervalId(intervalId).calResult("SUCCESS")
                .calType(ParamParseUtil.CAL_TYPE_SYSTEM).build();

        QueryWrapper<IndexCalHandlerLogEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(calLogParams);
        queryWrapper.orderByDesc("cal_start_date");
        queryWrapper.last("limit 1 ");
        List<IndexCalHandlerLogEntity> list = indexCalHandlerLogDao.selectList(queryWrapper);
        if(list.isEmpty()){
            return null;
        }

        return list.get(0);
    }

    @Override
    public boolean updateCalLogToRecalByDate(String indexCode, String handlerName, String intervalId, Date startDate, Date endDate) {

        IndexCalHandlerLogEntity calLogParams = IndexCalHandlerLogEntity.builder()
                .indexCode(indexCode).handlerName(handlerName)
                .intervalId(intervalId).calResult("SUCCESS")
                .calType(ParamParseUtil.CAL_TYPE_SYSTEM).build();

        UpdateWrapper<IndexCalHandlerLogEntity> updateWrapper = new UpdateWrapper();
        updateWrapper.setEntity(calLogParams);
        updateWrapper.between("cal_start_date", startDate, endDate);
        IndexCalHandlerLogEntity indexCalHandlerLogEntity = new IndexCalHandlerLogEntity();
        indexCalHandlerLogEntity.setCalResult(ParamParseUtil.CAL_TYPE_RECAL);

        return update(indexCalHandlerLogEntity, updateWrapper);
    }
}