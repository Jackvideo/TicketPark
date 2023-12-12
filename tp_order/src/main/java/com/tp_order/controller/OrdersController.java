package com.tp_order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tp_common.util.ResultUtil;
import com.tp_order.client.UserService;
import com.tp_order.model.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import com.tp_order.service.OrdersService;

import java.util.List;
import java.util.Queue;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:19
 */
@RestController
@RequestMapping("/tp_order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //创建订单
    @PostMapping("/create")
    public ResultUtil createOrder(@RequestBody Order order) {
        //创建订单
        return ordersService.createOrder(order);

    }

    //根据id删除
    @DeleteMapping("/{id}")
    public ResultUtil delete(@PathVariable Integer id) {
        return ResultUtil.success(ordersService.removeById(id));
        }

    //根据用户id获取订单
    @GetMapping("/uid={id}")
    public List<Order> findByUserId(@PathVariable Integer id){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("userid",id);
        return ordersService.list(wrapper);
    }

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(ordersService.list());
        }

    //根据id查询
    @GetMapping("/{id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(ordersService.list());
        }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(ordersService.page(new Page<>(pageNum, pageSize)));
        }

}

