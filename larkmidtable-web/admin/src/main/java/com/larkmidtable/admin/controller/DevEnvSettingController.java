package com.larkmidtable.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.larkmidtable.admin.service.DevEnvSettingService;
import com.larkmidtable.admin.entity.DevEnvSetting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @Author: 
 * @Date: 
 * @Description: 
 **/
@RestController
@RequestMapping("/api/devEnvSetting")
@Api(tags = "开发配置模块")
public class DevEnvSettingController extends BaseController {

    @Autowired
    private DevEnvSettingService devEnvSettingService;


    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("分页查询所有数据")
    public R<IPage<DevEnvSetting>> selectAll(@RequestParam(value = "searchVal", required = false) String searchVal,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("pageNo") Integer pageNo) {

        return success(devEnvSettingService.getDevEnvSettingListPaging(pageSize, pageNo, searchVal));
    }

    /**
     * Get all project
     *
     * @return
     */
    @ApiOperation("获取所有数据")
    @GetMapping("/list")
    public R<List<DevEnvSetting>> selectList() {
        QueryWrapper<DevEnvSetting> query = new QueryWrapper();
        query.eq("flag", true);
        return success(devEnvSettingService.list(query));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public R<DevEnvSetting> selectOne(@PathVariable Serializable id) {
        return success(this.devEnvSettingService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param entity 实体对象
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @PostMapping
    public R<Boolean> insert(HttpServletRequest request, @RequestBody DevEnvSetting entity) {
        entity.setUserId(getCurrentUserId(request));
        entity.setCreateTime(new Date());
        return success(this.devEnvSettingService.save(entity));
    }




    /**
     * 修改数据
     *
     * @param entity 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("修改数据")
    public R<Boolean> update(@RequestBody DevEnvSetting entity) {
        DevEnvSetting project = devEnvSettingService.getById(entity.getId());
        project.setName(entity.getName());
        project.setDescription(entity.getDescription());
        project.setUpdateTime(new Date());
        return success(this.devEnvSettingService.updateById(entity));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiOperation("删除数据")
    public R<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return success(this.devEnvSettingService.removeByIds(idList));
    }
}