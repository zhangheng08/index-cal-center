package com.gc.stcc.indexcal.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.IndexManageIndexCalConfigEntity;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageIndexCalConfigDao;
import com.gc.stcc.indexcal.manager.service.IndexManageIndexCalConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("indexManageIndexCalConfigService")
public class IndexManageIndexCalConfigServiceImpl extends ServiceImpl<IndexManageIndexCalConfigDao, IndexManageIndexCalConfigEntity> implements IndexManageIndexCalConfigService {

    @Autowired
    private IndexManageIndexCalConfigDao indexManageIndexCalConfigDao;

    @Override
    public IndexManageIndexCalConfigEntity getIndexCalConfig(String indexId) {
        IndexManageIndexCalConfigEntity calConfigParams = new IndexManageIndexCalConfigEntity();
        calConfigParams.setIndexId(indexId);
        QueryWrapper<IndexManageIndexCalConfigEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(calConfigParams);
        queryWrapper.orderByDesc("CREATE_DATE");
        List<IndexManageIndexCalConfigEntity> configList = indexManageIndexCalConfigDao.selectList(queryWrapper);
        return configList.isEmpty()?null:configList.get(0);
    }
}