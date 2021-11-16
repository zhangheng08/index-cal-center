package com.gc.stcc.indexcal.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.IndexManageIndexForCalEntity;
import com.gc.stcc.indexcal.common.vo.IndexCalDefineVO;

import java.util.List;

/**
 * 指标管理 指标-计算表
 *
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
public interface IndexManageIndexForCalService extends IService<IndexManageIndexForCalEntity> {
    List<IndexCalDefineVO> selectIndexCalDefineVOByCode(String indexCode);
}

