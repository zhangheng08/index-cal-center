package com.gc.stcc.indexcal.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gc.stcc.indexcal.common.model.IndexManageIndexEntity;
import com.gc.stcc.indexcal.common.vo.IndexDefineVO;
import com.gc.stcc.indexcal.common.vo.Result;

import java.util.List;

/**
 * 指标管理 指标
 *
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
public interface IndexManageIndexService extends IService<IndexManageIndexEntity> {
    Result saveIndexDefine(IndexDefineVO indexDefineVO);
    List<IndexDefineVO> selectIndexDefineVO();
    Result updateIndexDefineVO(IndexDefineVO indexDefineVO);
    Result deleteIndexDefineVO(IndexDefineVO indexDefineVO);
    IndexManageIndexEntity selectIndexManageIndexEntityByIndexCode(String indexCode);
}

