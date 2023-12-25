package com.tp_movie.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tp_common.constant.SystemConstants;
<<<<<<< Updated upstream
import com.tp_common.dto.Result;
=======
import com.tp_common.util.ResultUtil;
import com.tp_movie.model.entity.Comments;
import com.tp_movie.service.CommentsService;
>>>>>>> Stashed changes
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

    @Autowired
    private CommentsService commentsService;

    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.ok(moviesService.queryById(id));
    }
    //根据id删除
    @DeleteMapping("/{id}")
<<<<<<< Updated upstream
    public Result delete(@PathVariable Integer id) {
        return Result.ok(moviesService.removeById(id));
    }
    //保存
    @PostMapping("/save")
    public Result save(@RequestBody Movies movies) {
        //write into db
=======
    public ResultUtil delete(@PathVariable Integer id) {
        moviesService.removeById(id);
        return ResultUtil.success("删除成功");
    }
    //保存
    @PostMapping("/save")
    public ResultUtil save(@RequestBody Movies movies) {
        //write into movies
>>>>>>> Stashed changes
        moviesService.save(movies);
        //write into like_comment_counts
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

<<<<<<< Updated upstream
=======
    @GetMapping("/list")
    public List<Movies> getMoviesList(){
        return moviesService.list();
    }


>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
                .like(StrUtil.isNotBlank(name), "name", name)
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 返回数据
        return Result.ok(page.getRecords());
=======
                .like("name",name)
                .page(new Page<>(currentPage, SystemConstants.MAX_PAGE_SIZE));
        // 返回数据
        return ResultUtil.success(page.getRecords());
    }

    /**
     * 电影评论点赞功能
    */

    @PostMapping("/createComment")
    public ResultUtil createComment(@RequestBody Comments comments){
        commentsService.save(comments);
        moviesService.update(new UpdateWrapper<Movies>()
                .eq("movieid",comments.getMovieid())
                .setSql("comments_counts=comments_counts+1"));
        return ResultUtil.success("评论成功");
>>>>>>> Stashed changes
    }

    @PostMapping("/cancelComment")
    public ResultUtil cancelComment(@RequestBody Comments comments){
        commentsService.removeById(comments.getCommentid());
        moviesService.update(new UpdateWrapper<Movies>()
               .eq("movieid",comments.getMovieid())
               .setSql("comments_counts=comments_counts-1"));
        return ResultUtil.success("取消评论成功");
    }

    @PostMapping("/heart")
    public ResultUtil heart(@RequestBody Integer movieid){
        moviesService.update(new UpdateWrapper<Movies>()
               .eq("movieid",movieid)
               .setSql("likes_counts=likes_counts+1"));
        return ResultUtil.success("点赞成功");
    }

    @PostMapping("/cancelHeart")
    public ResultUtil cancelHeart(@RequestBody Integer movieid){
        moviesService.update(new UpdateWrapper<Movies>()
               .eq("movieid",movieid)
               .setSql("likes_counts=likes_counts-1"));
        return ResultUtil.success("取消点赞成功");
    }
}

