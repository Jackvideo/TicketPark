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

    @PostMapping("/register")
  public ResultUtil register (@RequestBody User user){
        return userService.register(user);
  }

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

    public ResultUtil<Map<String,Object>> modifyUser(@RequestBody User user){
        return userService.modifyUser(user);
    }

    @PostMapping("/logout")
    public ResultUtil<?> logout(@RequestHeader("token") String token){
        userService.logout(token);
        return ResultUtil.success("用户注销成功");
    }

    @PostMapping("/pay")
    public ResultUtil payOrder(@RequestBody Order order){
        Integer userid= order.getUserid();
        Double balance= order.getTicketprice();
        ResultUtil resultUtil = userService.tryToPay(userid, balance);
        resultUtil.setData(order.getOrderid());
        rabbitTemplate.convertAndSend("pay.direct","order.update",resultUtil);
        return resultUtil;
    }

    //根据用户id获取订单
    @GetMapping("/orders")
    public List<Order> getOrderList(@RequestParam("userid") Integer uid){
        return ordersService.findByUserId(uid);
    }

}

