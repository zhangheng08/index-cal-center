package com.gc.stcc.indexcal.manager.service;

import com.gc.stcc.indexcal.common.enums.EDimensionType;
import com.gc.stcc.indexcal.common.model.DimensionValue;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionEnmuEntity;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionEntity;
import com.gc.stcc.indexcal.common.model.IndexManageDimensionTableEntity;
import com.gc.stcc.indexcal.core.utils.EnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 维度值 查询服务
 * @author hsd
 * @date 2020/7/20 10:56
 */
@Service
public class DimensionValueService {

    private static final String DIMENSION_PREFIX = "dimension:";

    @Autowired
    private IndexManageDimensionEnmuService indexManageDimensionEnmuService;
    @Autowired
    private IndexManageDimensionTableService indexManageDimensionTableService;


    /**
     * 根据维度 查询维度值List
     * @param dimension
     * @return
     */
    public List<DimensionValue> getDimension(IndexManageDimensionEntity dimension) {
        List<DimensionValue> dimensionValueList = getDimensionValueListFromDb(dimension);
        return dimensionValueList;
    }

    /**
     * 从数据库 读取维度值
     * @param dimension
     * @return
     */
    private List<DimensionValue> getDimensionValueListFromDb(IndexManageDimensionEntity dimension) {
        List<DimensionValue> dimensionValueList = new ArrayList<>();
        String type = dimension.getType();
        if(EDimensionType.Enum.value().equals(type.toLowerCase())){
            IndexManageDimensionEnmuEntity dimensionEnmu = indexManageDimensionEnmuService.findByDimensionId(dimension.getId());
            if(dimensionEnmu == null){
                return dimensionValueList;
            }
            return EnumUtil.getEnmuDimensionValueByClassName(dimensionEnmu.getClassName());
        }else if(EDimensionType.字典.value().equals(type.toLowerCase())){
            // TODO
            return  dimensionValueList;
        }else if(EDimensionType.数据库.value().equals(type.toLowerCase())){
            IndexManageDimensionTableEntity dimensionTable = indexManageDimensionTableService.findByDimensionId(dimension.getId());
            if(dimensionTable == null){
                return dimensionValueList;
            }
            return indexManageDimensionTableService.findDimensionValues(dimensionTable.getColumnDimensionName(), dimensionTable.getColumnDimensionValue(), dimensionTable.getTableName());
        }else{
            return dimensionValueList;
        }
    }

}
