package com.dayshuai.blogserver.controller;

import com.dayshuai.bloguser.dao.URoleMapper;
import com.dayshuai.bloguser.dto.UUser;
import com.dayshuai.bloguser.service.UserService;
import com.dayshuai.common.entity.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName : UUserController
 * @Description : 用户前端controller
 * @Author : dayshuai
 * @Date: 2021-07-17 20:16
 */
@Controller
@RequestMapping(value = "/blog/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    URoleMapper uRoleMapper;

    @ResponseBody
    @GetMapping(value = "/getuser/{userId}")
    public Object getInfo (@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @ResponseBody
    @PostMapping("/register")
    public AjaxResult register(UUser user, String mailCode, String inviteCode) {
        userService.register(user, mailCode, inviteCode);
        return AjaxResult.success();
    }


}
