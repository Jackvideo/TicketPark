package com.tp_order.controller;

import com.ticketpark.tp_common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import com.tp_order.service.OrdersService;
import com.tp_order.model.entity.Orders;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:19
 */
@RestController
@RequestMapping("/tp_order/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    //编辑或更新
    @PostMapping("/saveOrUpdate")
    public ResultUtil save(@RequestBody Orders orders) {
        return ResultUtil.success(ordersService.saveOrUpdate(orders));
        }

    //根据id删除
    @DeleteMapping("/{id}")
    public ResultUtil delete(@PathVariable Integer id) {
        return ResultUtil.success(ordersService.removeById(id));
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

