package com.tp_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import cppp.common.util.ResultUtil;

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

    //编辑或更新
    @PostMapping("/saveOrUpdate")
    public ResultUtil save(@RequestBody Movies movies) {
        return ResultUtil.success(moviesService.saveOrUpdate(movies));
        }

    //根据id删除
    @DeleteMapping("/{id}")
    public ResultUtil delete(@PathVariable Integer id) {
        return ResultUtil.success(moviesService.removeById(id));
        }

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(moviesService.list());
        }

    //根据id查询
    @GetMapping("/{id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(moviesService.list());
        }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(moviesService.page(new Page<>(pageNum, pageSize)));
        }

}

