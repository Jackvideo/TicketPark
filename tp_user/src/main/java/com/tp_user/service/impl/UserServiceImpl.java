package com.tp_user.service.impl;

import com.tp_common.dto.Result;
import com.tp_user.model.entity.User;
import com.tp_user.mapper.UserMapper;
import com.tp_user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result login(User user) {
        //TODO
        return Result.ok();
    }

    @Override
    public Result modify(User user) {
        //TODO
        return Result.ok();
    }
}
