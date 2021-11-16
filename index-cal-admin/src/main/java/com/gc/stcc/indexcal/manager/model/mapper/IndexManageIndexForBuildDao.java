package com.gc.stcc.indexcal.manager.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gc.stcc.indexcal.common.model.IndexManageIndexForBuildEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 指标管理指标创建表
 * 
 * @author hsd
 * @date 2020-07-21 10:53:17
 */
@Mapper
public interface IndexManageIndexForBuildDao extends BaseMapper<IndexManageIndexForBuildEntity> {

    @Select("select count(*) from information_schema.tables where table_name = #{tableName}")
	int checkTableIsExist(String tableName);
}
