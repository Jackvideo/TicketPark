package com.tp_movie.controller;

import cn.hutool.core.util.StrUtil;
import com.tp_common.constant.SystemConstants;
import com.tp_common.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;


import com.tp_movie.service.MoviesService;
import com.tp_movie.model.entity.Movies;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:26
 */
@RestController
@RequestMapping("/tp_movie/movies")
public class MoviesController {

    @Autowired
    private MoviesService moviesService;

    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.ok(moviesService.queryById(id));
    }
    //根据id删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.ok(moviesService.removeById(id));
    }
    //保存
    @PostMapping("/save")
    public Result save(@RequestBody Movies movies) {
        //write into db
        moviesService.save(movies);
        //return movie id
        return Result.ok(movies.getMovieid());
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Movies movies) {
        //write into db
        moviesService.updateById(movies);
        return Result.ok();
    }

    /**
     * 根据电影类型分页查询电影信息
     * @param type 电影类型
     * @param current 页码
     * @return 电影列表
     */
    @GetMapping("/of/type")
    public Result queryMoviesByType(
            @RequestParam("type") Integer type,
            @RequestParam(value = "current", defaultValue = "1") Integer current
    ) {
        // 根据类型分页查询
        Page<Movies> page = moviesService.query()
                .eq("type", type)
                .page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE));
        // 返回数据
        return Result.ok(page.getRecords());
    }

    /**
     * 根据电影名称关键字分页查询商铺信息
     * @param name 电影名称关键字
     * @param current 页码
     * @return 电影列表
     */
    @GetMapping("/of/name")
    public Result queryMoviesByName(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "current", defaultValue = "1") Integer current
    ) {
        // 根据类型分页查询
        Page<Movies> page = moviesService.query()
                .like(StrUtil.isNotBlank(name), "name", name)
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 返回数据
        return Result.ok(page.getRecords());
    }

}

