package com.tp_user.controller;

import com.tp_common.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tp_user.service.UserService;
import com.tp_user.model.entity.User;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:17
 */
@RestController
@RequestMapping("/tp_user/user")
public class UserController {

    @Autowired
    private UserService userService;

    //TODO loginAndRegister
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return userService.login(user);
    }

    //TODO logout
    @PostMapping("/logout")
    public Result logout() {
        return Result.ok();
    }

    //TODO me

    //TODO modify
    @PutMapping("/modify")
    public Result modify(@RequestBody User user) {
        return userService.modify(user);
    }
}

