package com.gc.stcc.indexcal.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionBuildConfigEntity;
import com.gc.stcc.indexcal.common.model.IndexManageIndexForBuildEntity;
import com.gc.stcc.indexcal.core.utils.StringUtil;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageDimensionBuildConfigDao;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageIndexForBuildDao;
import com.gc.stcc.indexcal.manager.service.IndexManageIndexForBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 指标管理指标创建表
 *
 * @author hsd
 * @date 2020-07-21 10:53:17
 */
@Service("indexManageIndexForBuildService")
public class IndexManageIndexForBuildServiceImpl extends ServiceImpl<IndexManageIndexForBuildDao, IndexManageIndexForBuildEntity> implements IndexManageIndexForBuildService {

    private static final String DEFAULT_EXTRASQL = " num INT COMMENT '总数',";

    @Autowired
    private IndexManageIndexForBuildDao indexManageIndexForBuildDao;
    @Autowired
    private IndexManageDimensionBuildConfigDao indexManageDimensionBuildConfigDao;

    @Override
    public int checkTableIsExist(String tableName){
        return indexManageIndexForBuildDao.checkTableIsExist(tableName);
    }

    /**
     * 根据 指标 生成指标表的建表sql语句
     * @param indexId
     * @return
     */
    @Override
    public StringBuilder getCreateTableSql(String indexId){
        StringBuilder sb = new StringBuilder();
        IndexManageIndexForBuildEntity dimensionParams = new IndexManageIndexForBuildEntity();
        dimensionParams.setIndexId(indexId);
        QueryWrapper<IndexManageIndexForBuildEntity> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(dimensionParams);
        IndexManageIndexForBuildEntity indexForBuildEntity = indexManageIndexForBuildDao.selectOne(queryWrapper);
        String dimensionIds = indexForBuildEntity.getDimensionIds();
        List<String> idList = Arrays.asList(dimensionIds.split("\\,"));
        List<IndexManageDimensionBuildConfigEntity> dimensionConfiglist = indexManageDimensionBuildConfigDao.selectBatchDimensionIds(idList);
        buildHeadSql(sb, indexForBuildEntity.getResultTableName());
        buildBodySql(sb, dimensionConfiglist);
        buildFootSql(sb, indexForBuildEntity.getExtraSql());
        return sb;
    }

    private void buildFootSql(StringBuilder sb, String extraSql) {
        if (StringUtil.isEmpty(extraSql)){
            sb.append(System.getProperty("line.separator")).append("        "+DEFAULT_EXTRASQL+" ");
        }else {
            sb.append(System.getProperty("line.separator")).append("        "+extraSql+" ");
        }
        sb.append(System.getProperty("line.separator")).append("        PRIMARY KEY (id)");
        sb.append(System.getProperty("line.separator")).append("    )");
        sb.append(System.getProperty("line.separator")).append("    ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
    }

    private void buildBodySql(StringBuilder sb, List<IndexManageDimensionBuildConfigEntity> dimensionConfiglist) {
        for (IndexManageDimensionBuildConfigEntity config :dimensionConfiglist) {
            // sb.append("        dist_id bigint unsigned COMMENT '区域ID',");
            sb.append(System.getProperty("line.separator")).append("        "+config.getColumnField()+" "+config.getColumnType()+" "+config.getColumnNull()+" COMMENT '"+config.getColumnComment()+"', ");
        }
    }

    private void buildHeadSql(StringBuilder sb, String tableName) {
        sb.append(System.getProperty("line.separator")).append("CREATE TABLE");
        sb.append(System.getProperty("line.separator")).append("    "+tableName);
        sb.append(System.getProperty("line.separator")).append("    (");
        sb.append(System.getProperty("line.separator")).append("        id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',");
        sb.append(System.getProperty("line.separator")).append("        create_date DATETIME COMMENT '创建日期时间',");
        sb.append(System.getProperty("line.separator")).append("        update_date DATETIME COMMENT '修改日期时间',");
        sb.append(System.getProperty("line.separator")).append("        owner_id bigint unsigned COMMENT '操作用户id',");
    }


}