package com.tp_order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tp_common.util.ResultUtil;
import com.tp_order.model.entity.Order;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:19
 */
public interface OrdersService extends IService<Order> {

    public ResultUtil createOrder(Order order);

}
