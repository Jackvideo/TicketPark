package com.tp_movie.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tp_common.constant.SystemConstants;
import com.tp_common.util.ResultUtil;
import com.tp_movie.model.entity.Comments;
import com.tp_movie.service.CommentsService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;


import com.tp_movie.service.MoviesService;
import com.tp_movie.model.entity.Movies;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private CommentsService commentsService;
    //根据id查询
    @GetMapping("/{id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(moviesService.getById(id));
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

    //获取正在热映的电影
    @GetMapping("/hot")
    public List<Movies> getHotMovies(){
        //获取本地当前日期
        LocalDate today=LocalDate.now();
        LocalDate base=LocalDate.now().minusMonths(6);
        QueryWrapper<Movies> wrapper=new QueryWrapper<>();
        //半年前到今天上映的都算正在热映
        wrapper.le("start_time",today);
        wrapper.gt("start_time",base);
        return moviesService.list(wrapper);
    }

    //获取即将上映电影
    @GetMapping("/future")
    public List<Movies> getFutureMovies(){
        //获取本地当前日期
        LocalDate today=LocalDate.now();
        QueryWrapper<Movies> wrapper=new QueryWrapper<>();
        //今天之后的都算即将上映电影
        wrapper.gt("start_time",today);
        return moviesService.list(wrapper);
    }

    //获取经典电影
    @GetMapping("/epic")
    public List<Movies> getEpicMovies(){
        LocalDate base=LocalDate.now().minusYears(3);
        QueryWrapper<Movies> wrapper=new QueryWrapper<>();
        //三年之前的都算经典电影
        wrapper.le("start_time",base);
        return moviesService.list(wrapper);
    }


    /**
     * 根据电影类型分页查询电影信息
     * @param type 电影类型
     * @param currentPage 页码
     * @return 电影列表
     */
    @GetMapping("/queryByType")
    public ResultUtil queryMoviesByType(
            @RequestParam("type") String type,
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
    @GetMapping("/queryByName")
    public ResultUtil queryMoviesByName(
            @RequestParam(value = "moviename", required = false) String name,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage
    ) {
        // 根据类型分页查询
        Page<Movies> page = moviesService.query()
                .like(StringUtil.isNotBlank(name), "moviename", name)
                .page(new Page<>(currentPage, SystemConstants.MAX_PAGE_SIZE));
        // 返回数据
        return ResultUtil.success(page.getRecords());
    }

    /**
     * 电影评论点赞功能
    */

    //点赞
    @PostMapping("/like{id}")
    public ResultUtil like(@PathVariable("id") Integer movieid){
        Movies movies= moviesService.getById(movieid);
        movies.setLikeCount(movies.getLikeCount()+1);
        moviesService.updateById(movies);
        return ResultUtil.success("点赞成功");
    }

    //取消点赞
    @PostMapping("/unlike{id}")
    public ResultUtil unlike(@PathVariable("id") Integer movieid){
        Movies movies= moviesService.getById(movieid);
        Integer likes=movies.getLikeCount();
        if(likes<1)
            return ResultUtil.fail("没有点赞");
        movies.setLikeCount(movies.getLikeCount()-1);
        moviesService.updateById(movies);
        return ResultUtil.success("取消点赞成功");
    }



    //创建新评论
    @PostMapping("/createComment")
    public ResultUtil createComment(@RequestBody Comments comments){
        commentsService.save(comments);
        //更新电影评论数
        Movies movie= moviesService.getById(comments.getMovieid());
        movie.setCommentCount(movie.getCommentCount()+1);
        moviesService.updateById(movie);
        return ResultUtil.success("评论发布成功");

    }

    @GetMapping("/findCommentsByMovieId{id}")
    public List<Comments> findCommentsByMovieId(@PathVariable("id") Integer movieid){
        QueryWrapper<Comments> wrapper=new QueryWrapper<>();
        wrapper.eq("movieid",movieid);
        return commentsService.list(wrapper);
    }

    //删除评论
    @DeleteMapping("/deleteComment{id}")
    public ResultUtil deleteComment(@PathVariable("id") Integer userid,@RequestBody Comments comments){
        if(!comments.getUserid().equals(userid))
            return ResultUtil.fail("非该用户评论，无法删除！");
        commentsService.removeById(comments);
        //更新电影评论数
        Movies movie= moviesService.getById(comments.getMovieid());
        movie.setCommentCount(movie.getCommentCount()-1);
        moviesService.updateById(movie);
        return ResultUtil.success("成功删除评论");
    }

}

