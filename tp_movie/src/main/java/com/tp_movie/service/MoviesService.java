package com.tp_movie.service;

import com.tp_common.util.ResultUtil;
import com.tp_movie.model.entity.Movies;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:26
 */
public interface MoviesService extends IService<Movies> {
    ResultUtil queryById(Integer id);

    ResultUtil removeById(Integer id);
}
