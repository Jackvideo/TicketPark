package com.tp_order.listener;

import com.tp_common.util.ResultUtil;
import com.tp_order.client.UserService;
import com.tp_order.model.entity.Order;
import com.tp_order.service.OrdersService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UpdateOrderListener {

    @Autowired
    private UserService userService;

    @Autowired
    private OrdersService ordersService;

    @Autowired

    //用户尝试支付后发送更新消息，订单状态更新
    @RabbitListener(bindings = @QueueBinding(value = @Queue(name="updateOrder"),
            exchange = @Exchange(name = "pay.direct"),
            key ="order.update" ))
    public void orderListener(ResultUtil data){
            Order order=ordersService.getById((Integer)data.getData());
            if(data.getCode()==-1)//支付失败
                order.setOrderstate(Order.STATE_FAILED);
            else if(data.getCode()==0)//取消支付
                order.setOrderstate(Order.STATE_CANCELED);
            else//支付成功
            order.setOrderstate(Order.STATE_FINISHED);
            ordersService.updateById(order);
    }
}
