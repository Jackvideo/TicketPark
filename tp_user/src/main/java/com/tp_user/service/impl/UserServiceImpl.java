package com.tp_user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tp_common.util.ResultUtil;
import com.tp_user.model.entity.User;
import com.tp_user.mapper.UserMapper;
import com.tp_user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResultUtil register(User user) {
        //用户名是否存在
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username",user.getUsername());
        if(userMapper.exists(wrapper))
        return ResultUtil.fail("用户名已存在");
        wrapper.clear();
        //获取最后一个元素，得到主键值
        wrapper.last("limit 1");
        User lastUser=userMapper.selectOne(wrapper);
        user.setUserid(lastUser==null?1: lastUser.getUserid()+1);
        userMapper.insert(user);
        return ResultUtil.success("用户注册成功！");
    }

    @Override
    public Map<String, Object> login(User user) {
        // 查询数据库
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        wrapper.eq(User::getPassword, user.getPassword());
        User loginUser = (User) userMapper.selectOne(wrapper);
        // 结果不为空，生成token，将用户信息存入redis
        if (loginUser != null) {
            // 用UUID，终极方案是jwt
            String key = "user:" + UUID.randomUUID();
            // 存入redis
            loginUser.setPassword(null);    // 设置密码为空，密码没必要放入
            redisTemplate.opsForValue().set(key, loginUser);   // timeout为登录时间
            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }
        // 结果不为空，生成token，前后端分离，前端无法使用session，可以使用token
        // 并将用户信息存入redis
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        // 之前已将对象进行序列化处理存入redis，现在从redis中取出需要反序列化处理
        Object obj = redisTemplate.opsForValue().get(token);    // 此对象是map类型,稍后需要序列化为Json字符串
        if (obj!= null) {
            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
            Map<String,Object> data = new HashMap<>();
            data.put("id",loginUser.getUserid());
            data.put("name",loginUser.getUsername());
            data.put("password",loginUser.getPassword());
            data.put("balance",loginUser.getBalance());
            // 先在xml里写SQL语句id=getRoleNameByUserId，然后去UserMapper里实现接口
            return data;
        }

        return null;
    }

    @Override
    public User getUser(String token) {
        Object obj = redisTemplate.opsForValue().get(token);    // 此对象是map类型,稍后需要序列化为Json字符串
        if (obj!= null) {
            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
            return loginUser;
        }
        return null;
    }

    @Override
    public ResultUtil modifyUser(User user) {
     QueryWrapper<User> wrapper=new QueryWrapper<>();
     wrapper.eq("userid",user.getUserid());
     //如果存在该用户id，可以执行修改
     if(userMapper.exists(wrapper)){
        return ResultUtil.fail("用户名已存在");
     }
        //直接根据主键更新，即userid
        userMapper.updateById(user);
        return ResultUtil.success("用户信息修改成功");
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);    // 从redis中删除token
    }

    //检查余额并尝试支付，供订单监听者调用
    @Override
    public ResultUtil tryToPay(Integer userid,Double price){
        User user= userMapper.selectById(userid);
        Double balance =user.getBalance();
        if(balance<price)
            return ResultUtil.fail("用户余额不足！ 支付失败！");
        balance-=price;
        user.setBalance(balance);
        userMapper.updateById(user);
        return ResultUtil.success("支付成功！");
    }
}
