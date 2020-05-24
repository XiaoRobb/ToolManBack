package com.whu.toolman.controller;

import com.whu.toolman.common.Result;
import com.whu.toolman.entity.User;
import com.whu.toolman.service.UserService;
import com.whu.toolman.util.PictureUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        try {
            User user = userService.getUser(username);
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
        catch (Exception e) {
            result.setCode(201);
            result.setMsg("没有该用户");
            return result;
        }
    }

    @PostMapping("/{username}")
    public Result register(@PathVariable("username") String username
            , @RequestParam("password") String password
            , @RequestParam("email") String email
            , @RequestParam("name") String name) {
        Result result = new Result();
        try {
            User user1 = userService.getUser(username);
            result.setCode(201);
            result.setMsg("用户已存在");
            return result;
        }
        catch (IndexOutOfBoundsException exception) {
            System.out.println("没有老用户");
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            result.setCode(500);
            result.setMsg("查询出现问题");
            return result;
        }
        //这里开始注册用户图片开始
        String image = PictureUtil.url + "user/default.jpg";
        if (userService.createUser(username, password, name, email, image) == 0) {
            result.setCode(202);
            result.setMsg("注册失败");
            return result;
        }
        result.setCode(200);
        result.setMsg("注册成功");
        return result;
    }

    @PutMapping("/{username}")
    public Result updateUser(@PathVariable("username") String username
            , @RequestParam("password") String password
            , @RequestParam("email") String email
            , @RequestParam("name") String name
            , @RequestParam("imageUrl") String imageUrl
            , @RequestParam("image") MultipartFile image) {
        Result result = new Result();
        String url = null;
        String uuid = null;
        if (imageUrl.equals("")) {
            //因为更新了图片所以需要先删除然后再
            try {
                url = userService.getUser(username).getImage();
                uuid = userService.getUser(username).getUuid();
            }
            catch (NullPointerException e) {
                result.setCode(302);
                result.setMsg("没有该uuid");
                return result;
            }
            //首先判断删除老图片是否成功
            if (!PictureUtil.deletePhoto(url, PictureUtil.filePathUser)) {
                result.setCode(500);
                result.setMsg("更新失败（ps:删除）");
            }
            //得到图片url
            url = PictureUtil.uploadImage(image, uuid, PictureUtil.filePathUser);
            if (url.equals("上传失败，请上传bmp、jpg、jpeg、png、gif文件！")){
                result.setCode(500);
                result.setMsg(url);
                return result;
            }else if (url.equals("图片上传至服务器失败！")){
                result.setCode(500);
                result.setMsg(url);
                return result;
            }
        }
        else {
            url = imageUrl;
        }

        int count = 0;
        try {
            count = userService.updateUser(username, password, name, email, url);
        }
        catch (Exception e) {
            result.setCode(500);
            result.setMsg("数据库修改出错");
        }
        result.setCode(200);
        result.setMsg("修改成功");
        result.setData(count);
        return result;
    }



}
