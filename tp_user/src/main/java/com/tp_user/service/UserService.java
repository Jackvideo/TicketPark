package com.tp_user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tp_common.util.ResultUtil;
import com.tp_user.model.entity.User;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:17
 */
public interface UserService extends IService<User> {

    public ResultUtil register(User user);

    public Map<String,Object> login(User user);

    public Map<String,Object> getUserInfo(String token);

    public User getUser(String token);

    public ResultUtil modifyUser(User user);

    public void logout(String token);

    public ResultUtil tryToPay (Integer userid,Double price);

}
