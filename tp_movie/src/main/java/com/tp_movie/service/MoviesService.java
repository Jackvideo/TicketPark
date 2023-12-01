package com.tp_movie.service;

import com.tp_common.dto.Result;
import com.tp_movie.model.entity.Movies;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:26
 */
public interface MoviesService extends IService<Movies> {

    Result queryById(Integer id);

    Result removeById(Integer id);

}
