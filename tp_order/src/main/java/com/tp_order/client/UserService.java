package com.tp_order.client;

import com.tp_common.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tp-user", url = "http://localhost:8081/tp_user/")
public interface UserService {

//    @GetMapping("/checkBalance")
//    public ResultUtil checkBalance(@RequestParam Integer userid,Double price);
}
