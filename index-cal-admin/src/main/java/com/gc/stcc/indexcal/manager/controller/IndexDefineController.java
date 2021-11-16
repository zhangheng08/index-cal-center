package com.gc.stcc.indexcal.manager.controller;


import com.alibaba.fastjson.JSONObject;
import com.gc.stcc.indexcal.common.model.IndexManageIndexCalConfigEntity;
import com.gc.stcc.indexcal.common.model.IndexManageIndexEntity;
import com.gc.stcc.indexcal.common.model.IndexManageIndexForBuildEntity;
import com.gc.stcc.indexcal.common.vo.IndexCalDefineVO;
import com.gc.stcc.indexcal.common.vo.IndexDefineVO;
import com.gc.stcc.indexcal.common.vo.Result;
import com.gc.stcc.indexcal.core.exception.ServiceException;
import com.gc.stcc.indexcal.manager.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author hsd
 * @date 2020-07-09 14:19:41
 */
@Api(value = "IndexDefineController")
@RestController
@RequestMapping("index/manage/index")
public class IndexDefineController {

    @Autowired
    private IndexManageDimensionService indexManageDimensionService;
    @Autowired
    private IndexManageIndexDimensionService indexManageIndexDimensionService;
    @Autowired
    private IndexManageIndexService indexManageIndexService;
    @Autowired
    private IndexManageIndexForBuildService indexManageIndexForBuildService;
    @Autowired
    private IndexManageIndexForCalService indexManageIndexForCalService;
    @Autowired
    private IndexManageIndexCalConfigService indexManageIndexCalConfigService;

    /**
     * 指标管理 新增指标
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 新增指标")
    @PostMapping("/save")
    public Result save(@RequestBody IndexDefineVO indexDefineVO) throws Exception {
        try {
            Date now = new Date();
            indexDefineVO.setCreateDate(now);
            indexDefineVO.setUpdateDate(now);
            indexDefineVO.setOwnerId("noid");
            return  indexManageIndexService.saveIndexDefine(indexDefineVO);
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    /**
     * 指标管理 查询指标
     * @param traceId
     * @return
     * @throws Exception
     */
    @ApiOperation(value="指标管理 查询指标")
    @PostMapping("/findIndexs")
    public Result findIndexs(@RequestHeader(name = "trace_id", required = false) String traceId) throws Exception {
        try {
            Result result = Result.succeed("查询成功");
            List<IndexDefineVO> list = indexManageIndexService.selectIndexDefineVO();
            result.setData(list);
            return result;
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    /**
     * 指标管理 查询创建指标表的sql语句
     * @param traceId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 查询创建指标表的sql语句")
    @PostMapping("/createSql")
    public Result createSql(@RequestHeader(name = "trace_id", required = false) String traceId,
                            @RequestParam(name = "indexId", required = false) String indexId) throws Exception {
        try {
            Result result = Result.succeed("查询成功");
            StringBuilder sb = indexManageIndexForBuildService.getCreateTableSql(indexId);
            System.out.println(sb.toString());
            result.setData(sb);
            return result;
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    /**
     * 指标管理 修改指标
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 修改指标")
    @PostMapping("/update")
    public Result update(@RequestBody IndexDefineVO indexDefineVO) throws Exception {
        try {
            Date now = new Date();
            indexDefineVO.setUpdateDate(now);
            indexDefineVO.setOwnerId("noid");
            IndexManageIndexForBuildEntity indexBuild = indexManageIndexForBuildService.getById(indexDefineVO.getBuildId());
            String resultTableName = indexBuild.getResultTableName();
            int tableNum = indexManageIndexForBuildService.checkTableIsExist(resultTableName);
            if(tableNum > 0){
                return Result.failed("指标结果表已经创建，不可进行修改!");
            }
            return indexManageIndexService.updateIndexDefineVO(indexDefineVO);
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    /**
     * 指标管理 删除指标
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 删除指标")
    @PostMapping("/delete")
    public Result delete(@RequestBody IndexDefineVO indexDefineVO) throws Exception {
        try {
            Date now = new Date();
            indexDefineVO.setUpdateDate(now);
            indexDefineVO.setOwnerId("noid");

            IndexManageIndexForBuildEntity indexBuild = indexManageIndexForBuildService.getById(indexDefineVO.getBuildId());
            String resultTableName = indexBuild.getResultTableName();
            int tableNum = indexManageIndexForBuildService.checkTableIsExist(resultTableName);
            if(tableNum > 0){
                return Result.failed("指标结果表已经创建，不可删除!");
            }
            return indexManageIndexService.deleteIndexDefineVO(indexDefineVO);
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    /**
     * 指标管理 根据code查询计算指标定义信息
     * @param indexCode
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 根据code查询计算指标定义信息")
    @PostMapping("/findIndexCalDefineByCode")
    @Deprecated
    public List<IndexCalDefineVO> findIndexCalDefineByCode(@RequestParam(name = "indexCode") String indexCode) throws Exception {
        List<IndexCalDefineVO> list = new ArrayList<>();
        try {
            list = indexManageIndexForCalService.selectIndexCalDefineVOByCode(indexCode);
        }catch (ServiceException e){
            throw new Exception(e);
        }
        return list;
    }

    /**
     * 指标管理  指标计算配置表
     * @param index_id
     * @param json_config
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 指标配置保存")
    @PostMapping("/indexCalConfig/save")
    public Result indexCalConfigSave(@RequestParam(name = "index_id") String index_id,
                                                     @RequestParam(name = "json_config") String json_config) throws Exception {
        try {
            IndexManageIndexEntity index = indexManageIndexService.getById(index_id);
            if(index ==null){
                return Result.failed("未查询到该指标");
            }
            IndexManageIndexCalConfigEntity indexManageIndexCalConfig = new IndexManageIndexCalConfigEntity();
            indexManageIndexCalConfig.setIndexId(index_id);
            indexManageIndexCalConfig.setJsonConfig(json_config);
            indexManageIndexCalConfigService.save(indexManageIndexCalConfig);
            return Result.succeed("保存成功");
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    /**
     * 指标管理  指标计算配置表加载
     * @param index_id
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "指标管理 指标配置加载")
    @PostMapping("/indexCalConfig/load")
    public Result findIndexCalConfig(@RequestParam(name = "index_id") String index_id) throws Exception {
        try {
            Result result = Result.succeed("查询成功");
            IndexManageIndexCalConfigEntity indexManageIndexCalConfig = indexManageIndexCalConfigService.getIndexCalConfig(index_id);
            result.setData(indexManageIndexCalConfig);
            return result;
        }catch (ServiceException e){
            throw new Exception(e);
        }
    }

    @ApiOperation(value = "保存指标计算配置信息")
    @PostMapping(value = "/indexCalConfig/saveV2")
    public Result indexCalConfigSave(@RequestBody String configJson) throws Exception {

        Result result = new Result();

        final JSONObject jsonObject = JSONObject.parseObject(configJson);

        final String indexCode = jsonObject.getString("indexCode");
        final String handlerName = jsonObject.getString("handlerName");
        final String configDefinition = jsonObject.getString("dimensionDefinition");



        return result;

    }


}
