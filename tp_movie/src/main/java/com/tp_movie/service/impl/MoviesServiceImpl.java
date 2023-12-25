package com.tp_movie.service.impl;


import com.tp_common.util.RedisUtils;
import com.tp_common.util.ResultUtil;
import com.tp_movie.model.entity.Movies;
import com.tp_movie.mapper.MoviesMapper;
import com.tp_movie.service.MoviesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tp_common.constant.RedisConstants.CACHE_MOVIE_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:26
 */
@Service
public class MoviesServiceImpl extends ServiceImpl<MoviesMapper, Movies> implements MoviesService {

    @Autowired
    private RedisUtils redisUtils;



}
