package com.tp_movie.service.impl;

import com.tp_common.dto.Result;
import com.tp_common.util.RedisUtils;
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

    @Override
    public Result queryById(Integer id) {
        String key=CACHE_MOVIE_KEY+id;
        //1.check key from redis
        if(redisUtils.hasKey(key)){
            //2.return if exists
            Movies movies=redisUtils.get(key);
            return Result.ok(movies);
        }
        //3.query from db if not exists
        Movies movies=getById(id);
        //5.not exists,return fail
        if(movies==null){
            return Result.fail("movie not exists");
        }
        //6.exists, save to redis
        redisUtils.set(key,movies);
        return Result.ok(movies);
    }

    @Override
    public Result removeById(Integer id) {
        String key=CACHE_MOVIE_KEY+id;
        List<String> keys=new java.util.ArrayList<>();
        keys.add(key);
        redisUtils.delete(keys);
        return Result.ok(super.removeById(id));
    }

}
