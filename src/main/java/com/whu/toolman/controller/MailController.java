package com.whu.toolman.controller;

import com.whu.toolman.common.Result;
import com.whu.toolman.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class MailController {
    @Autowired
    private MailService mailService;
    private static String checkCode;

    @GetMapping("/getCheckCode")
    public Result getCheckCode(@RequestParam("email")String email){
        Result result = new Result();
        checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(email, "注册验证码", message);
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("验证码发送失败");
            result.setData(checkCode);
            return result;
        }
        result.setCode(200);
        result.setMsg("验证码发送成功");
        result.setData(checkCode);
        return result;
    }

    @GetMapping("/Check")
    public Result Check(@RequestParam("checkcode")String checkcode){
        Result result = new Result();
        System.out.print(checkCode);
        if(!checkCode.equalsIgnoreCase(checkcode)){
            result.setCode(500);
            result.setMsg("验证失败");

            return result;
        }
        else {
            result.setCode(200);
            result.setMsg("验证成功");

            return result;
        }
    }

}
