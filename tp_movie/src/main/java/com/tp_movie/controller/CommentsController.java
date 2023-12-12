package com.tp_movie.controller;

import com.tp_common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;


import com.tp_movie.service.CommentsService;
import com.tp_movie.model.entity.Comments;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-12-12 11:27
 */
@RestController
@RequestMapping("/com.tp_movie/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    //编辑或更新
    @PostMapping("/saveOrUpdate")
    public ResultUtil save(@RequestBody Comments comments) {
        return ResultUtil.success(commentsService.saveOrUpdate(comments));
        }

    //根据id删除
    @DeleteMapping("/{id}")
    public ResultUtil delete(@PathVariable Integer id) {
        return ResultUtil.success(commentsService.removeById(id));
        }

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(commentsService.list());
        }

    //根据id查询
    @GetMapping("/{id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(commentsService.list());
        }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(commentsService.page(new Page<>(pageNum, pageSize)));
        }

}

