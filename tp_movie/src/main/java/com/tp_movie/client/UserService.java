package com.tp_movie.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tp-user",url = "http://localhost:8081/tp_user/")
public interface UserService {

    //根据用户id获取用户名
    @GetMapping("/getName")
    public String getUserNameById(@RequestParam("userid") Integer uid);
}
