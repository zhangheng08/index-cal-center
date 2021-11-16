package com.gc.stcc.indexcal.manager.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gc.stcc.indexcal.common.model.IndexManageCalJobConfig;
import com.gc.stcc.indexcal.common.vo.IndexCalculateConfigVo;
import com.gc.stcc.indexcal.manager.model.mapper.IndexManageCalJobConfigMapper;
import com.gc.stcc.indexcal.manager.service.IndexManageCalJobConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: IndexManageCalJobConfigServiceImpl
 * Author:  Payne08
 * Date: 2021/6/1 9:32
 * Description: 获取配置信息
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 * @author Payne08
 */
@Service
public class IndexManageCalJobConfigServiceImpl extends ServiceImpl<IndexManageCalJobConfigMapper, IndexManageCalJobConfig> implements IndexManageCalJobConfigService {

    @Autowired
    private IndexManageCalJobConfigMapper indexManageCalJobConfigMapper;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public IndexCalculateConfigVo fetchById(Integer jobId) {
        IndexManageCalJobConfig indexManageCalJobConfig = indexManageCalJobConfigMapper.fetchById(jobId);
        IndexCalculateConfigVo indexCalculateConfigVo = new IndexCalculateConfigVo();
        indexCalculateConfigVo.setTenantId(indexManageCalJobConfig.getTenantId());
        indexCalculateConfigVo.setTimestamp(SDF.format(new Date()));
        List<IndexCalculateConfigVo.DimConfig> list = new ArrayList<>();
        String jsonString = indexManageCalJobConfig.getDimConfig();
        JSONArray jsonArray = JSONObject.parseArray(jsonString);
        for(Object obj : jsonArray) {
            IndexCalculateConfigVo.DimConfig dimConfig = new IndexCalculateConfigVo.DimConfig();
            String objString = JSONObject.toJSONString(obj);
            JSONObject jsonObject = JSONObject.parseObject(objString);
            Integer dimId = jsonObject.getInteger("dim_id");
            String dimTable = jsonObject.getString("dim_table");
            String dimColumn = jsonObject.getString("dim_column");
            String[] attach = jsonObject.getJSONArray("attach").toArray(new String[] {});
            Integer[] logic = jsonObject.getJSONArray("logic") == null ? null : jsonObject.getJSONArray("logic").toArray(new Integer[] {});
            dimConfig.setDimId(dimId);
            dimConfig.setDimTable(dimTable);
            dimConfig.setDimColumn(dimColumn);
            dimConfig.setAttach(attach);
            dimConfig.setLogic(logic);
            list.add(dimConfig);
        }
        indexCalculateConfigVo.setConfigList(list);
        indexCalculateConfigVo.setStatus(true);
        indexCalculateConfigVo.setResultMessage("success");
        return indexCalculateConfigVo;
    }

}
