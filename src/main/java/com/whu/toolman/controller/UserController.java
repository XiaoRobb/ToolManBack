package com.whu.toolman.controller;

import com.whu.toolman.common.Result;
import com.whu.toolman.entity.User;
import com.whu.toolman.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ：qx.w
 * @description：用户登录注册用
 * @modified By：
 * @since ：Created in 2020/4/23 17:44
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/{username}")
    public Result login(@PathVariable("username") String username
            , @RequestParam("password") String password) {
        Result result = new Result();
        User user = userService.getUser(username);
        if (user == null) {
            result.setCode(201);
            result.setMsg("没有该用户");
            return result;
        }
        if (!user.getPassword().equals(password)) {
            result.setCode(201);
            result.setMsg("密码错误");
            return result;
        }
        result.setCode(200);
        result.setMsg("登陆成功");
        result.setData(user);
        return result;
    }

}
