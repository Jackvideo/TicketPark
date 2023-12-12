package com.tp_order.service.impl;

import com.tp_common.util.ResultUtil;
import com.tp_order.mapper.OrdersMapper;
import com.tp_order.model.entity.Order;
import com.tp_order.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:19
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Order> implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;


    @Override
    public ResultUtil createOrder(Order order) {
        order.setOrderstate(Order.STATE_UNPAID);
        order.setOrdertime(LocalDateTime.now());
        ordersMapper.insert(order);
        return ResultUtil.success("创建订单成功！");
    }

}
