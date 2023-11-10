package com.tp_user.controller;

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


}

