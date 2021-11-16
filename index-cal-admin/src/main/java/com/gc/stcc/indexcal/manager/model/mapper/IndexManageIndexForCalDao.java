package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexManageIndexForCalEntity;
import com.gc.stcc.indexcal.common.vo.IndexCalDefineVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 指标管理 指标-计算表
 * 
 * @author hsd
 * @date 2020-07-16 09:48:01
 */
@Mapper
public interface IndexManageIndexForCalDao extends BaseMapper<IndexManageIndexForCalEntity> {
    List<IndexCalDefineVO> selectIndexCalDefineVOByCode(String indexCode);
}
