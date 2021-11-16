package com.gc.stcc.indexcal.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.IndexManageIndexForCalEntity;
import com.gc.stcc.indexcal.common.vo.IndexCalDefineVO;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageIndexForCalDao;
import com.gc.stcc.indexcal.manager.service.IndexManageIndexForCalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("indexManageIndexForCalService")
public class IndexManageIndexForCalServiceImpl extends ServiceImpl<com.gc.stcc.indexcal.manager.model.mapper.IndexManageIndexForCalDao, IndexManageIndexForCalEntity> implements IndexManageIndexForCalService {

    @Autowired
    private IndexManageIndexForCalDao IndexManageIndexForCalDao;

    @Override
    public List<IndexCalDefineVO> selectIndexCalDefineVOByCode(String indexCode) {
        return IndexManageIndexForCalDao.selectIndexCalDefineVOByCode(indexCode);
    }
}