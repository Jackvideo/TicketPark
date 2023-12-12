package com.tp_user.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tp_user.model.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="tp-order",url = "http://localhost:8082/tp_order")
public interface OrdersService {

    @GetMapping("/uid={id}")
    public List<Order> findByUserId(@PathVariable Integer id);

}
