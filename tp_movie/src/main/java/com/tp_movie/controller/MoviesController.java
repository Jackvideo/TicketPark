package com.tp_movie.controller;

import cn.hutool.core.util.StrUtil;
import com.tp_common.constant.SystemConstants;
import com.tp_common.util.ResultUtil;
import com.tp_movie.model.entity.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;


import com.tp_movie.service.MoviesService;
import com.tp_movie.model.entity.Movies;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:26
 */
@RestController
@RequestMapping("/tp_movie")
public class MoviesController {

    @Autowired
    private MoviesService moviesService;

    //根据id查询
    @GetMapping("/{id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(moviesService.queryById(id));
    }
    //根据id删除
    @DeleteMapping("/{id}")
    public ResultUtil delete(@PathVariable Integer id) {
        return ResultUtil.success(moviesService.removeById(id));
    }
    //保存
    @PostMapping("/save")
    public ResultUtil save(@RequestBody Movies movies) {
        //write into db
        moviesService.save(movies);
        //return movie id
        return ResultUtil.success(movies.getMovieid());
    }
    //更新
    @PostMapping("/update")
    public ResultUtil update(@RequestBody Movies movies) {
        //write into db
        moviesService.updateById(movies);
        return ResultUtil.success("更新成功");
    }

    @GetMapping("/list")
    public List<Movies> getMoviesList(){

        return moviesService.list();
    }


    /**
     * 根据电影类型分页查询电影信息
     * @param type 电影类型
     * @param currentPage 页码
     * @return 电影列表
     */
    @GetMapping("/queryByType")
    public ResultUtil queryMoviesByType(
            @RequestParam("type") Integer type,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage
    ) {
        // 根据类型分页查询
        Page<Movies> page = moviesService.query()
                .eq("type", type)
                .page(new Page<>(currentPage, SystemConstants.DEFAULT_PAGE_SIZE));
        // 返回数据
        return ResultUtil.success(page.getRecords());
    }

    /**
     * 根据电影名称关键字分页查询商铺信息
     * @param name 电影名称关键字
     * @param currentPage 页码
     * @return 电影列表
     */
    @GetMapping("/queryByname")
    public ResultUtil queryMoviesByName(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage
    ) {
        // 根据类型分页查询
        Page<Movies> page = moviesService.query()
                .like(StrUtil.isNotBlank(name), "name", name)
                .page(new Page<>(currentPage, SystemConstants.MAX_PAGE_SIZE));
        // 返回数据
        return ResultUtil.success(page.getRecords());
    }

    /**
     * 电影评论点赞功能
    */

    @PostMapping
    public Comments createComment(@RequestBody Comments comments){

    }

}

