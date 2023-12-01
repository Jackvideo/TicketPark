package com.tp_user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tp_common.dto.Result;
import com.tp_common.util.ResultUtil;
import com.tp_user.model.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:17
 */
public interface UserService extends IService<User> {

    public Result login(User user);

    Result modify(User user);
}
