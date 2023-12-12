package com.tp_user.controller;

import com.tp_common.util.ResultUtil;
import com.tp_user.client.OrdersService;
import com.tp_user.model.entity.Order;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tp_user.service.UserService;
import com.tp_user.model.entity.User;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:17
 */
@RestController
@RequestMapping("/tp_user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/list")
    public List<User> getUserList(){
        return  userService.list();
    }

    //注册用户
    @PostMapping("/register")
    public ResultUtil register (@RequestBody User user){
        return userService.register(user);
  }
    //登录用户
    @PostMapping("/login")
    public ResultUtil<Map<String,Object>> login(@RequestBody User user){
        // 因为 user传过来为json字符串，所以用@RequestBody 进行实体转换

        // 业务代码在userService里完成
        Map<String,Object> data = userService.login(user);

        if(data != null){
            return ResultUtil.success(data);
        }
        return ResultUtil.fail("登陆失败：用户名或密码错误");
    }

    //获取用户信息
    @GetMapping("/info")
    public ResultUtil<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
        // @RequestParam("token") 是从url中获取值
        // 根据token获取用户信息，信息存进了redis中
        Map<String,Object> data = userService.getUserInfo(token);
        if(data == null){
            return ResultUtil.fail("登录信息错误");
        }
        return ResultUtil.success(data,"用户信息获取成功");

    }

    //修改用户信息
    @PostMapping("/modify")
    public ResultUtil<Map<String,Object>> modifyUser(@RequestBody User user){
        return userService.modifyUser(user);
    }

    //退出登录
    @PostMapping("/logout")
    public ResultUtil<?> logout(@RequestHeader("token") String token){
        userService.logout(token);
        return ResultUtil.success("用户注销成功");
    }

    //支付订单，发送结果消息
    @PostMapping("/pay")
    public ResultUtil payOrder(@RequestBody Order order){
        Integer userid= order.getUserid();
        Double balance= order.getTicketprice();
        ResultUtil resultUtil = userService.tryToPay(userid, balance);
        resultUtil.setData(order.getOrderid());
        rabbitTemplate.convertAndSend("pay.direct","order.update",resultUtil);
        return resultUtil;
    }
    //取消订单，发送取消消息
    @PostMapping("/cancel")
    public ResultUtil cancelOrder(@RequestBody Order order){
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(0);
        resultUtil.setData(order.getOrderid());
        rabbitTemplate.convertAndSend("pay.direct","order.update",resultUtil);
        resultUtil.setMessage("取消订单！");
        return resultUtil;
    }

    //根据用户id获取用户名
    @GetMapping("/getName")
    public String getUserNameById(@RequestParam("userid") Integer uid){
        return userService.getById(uid).getUsername();
    }

    //根据用户id获取订单(远程调用订单业务）
    @GetMapping("/orders")
    public List<Order> getOrderList(@RequestParam("userid") Integer uid){
        return ordersService.findByUserId(uid);
    }

}

