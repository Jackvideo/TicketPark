package com.tp_movie.service.impl;

import com.tp_movie.model.entity.Comments;
import com.tp_movie.mapper.CommentsMapper;
import com.tp_movie.service.CommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-12-12 11:27
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

}
