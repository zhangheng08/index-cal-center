package com.gc.stcc.indexcal.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.IndexCalHandlerDefineEntity;
import com.gc.stcc.indexcal.manager.model.mapper.IndexCalHandlerDefineDao;
import com.gc.stcc.indexcal.manager.service.IndexCalHandlerDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 指标计算 处理类定义表
 *
 * @author hsd
 * @email ${email}
 * @date 2020-08-07 15:02:15
 */
@Service("indexCalHandlerDefineService")
public class IndexCalHandlerDefineServiceImpl extends ServiceImpl<IndexCalHandlerDefineDao, IndexCalHandlerDefineEntity> implements IndexCalHandlerDefineService {

    @Autowired
    private IndexCalHandlerDefineDao indexCalHandlerDefineDao;

    @Override
    public IndexCalHandlerDefineEntity getCalDefineByCodeAndHandlerNameAndDimensionDef(String indexCode, String handlerName) {

        IndexCalHandlerDefineEntity calDefineParams = IndexCalHandlerDefineEntity.builder().indexCode(indexCode).handlerName(handlerName).build();
        QueryWrapper<IndexCalHandlerDefineEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(calDefineParams);
        List<IndexCalHandlerDefineEntity> list = indexCalHandlerDefineDao.selectList(queryWrapper);
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}