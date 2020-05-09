package com.whu.toolman.controller;

import com.whu.toolman.common.Result;
import com.whu.toolman.entity.User;
import com.whu.toolman.service.RecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ：qx.w
 * @description：历史记录
 * @modified By：
 * @since ：Created in 2020/5/9 15:50
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Resource
    RecordService recordService;


    @GetMapping("/{username}")
    public Result getRecord(@PathVariable("username") String username) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("成功返回" + username + "记录");
        try {
            result.setData(recordService.getByUser(username));
        }
        catch (Exception e) {
            result.setCode(500);
            result.setMsg("查询失败");
            result.setData(null);
        }
        return result;
    }

    @DeleteMapping("/{uuid}")
    public Result deleteRecord(@PathVariable("uuid") String uuid) {
        Result result = new Result();
        try {
            int count = recordService.deleteRecord(uuid);
            result.setData(count);
            result.setMsg("成功，删除条数为data");
            result.setCode(200);
        }
        catch (Exception e) {
            result.setMsg("失败");
            result.setCode(500);
        }
        return result;
    }

    @DeleteMapping(value = "/allRecord/{username}")
    public Result deleteAllBook(@PathVariable("username") String username) {
        Result result = new Result();
        try {
            int count = recordService.deleteAllRecord(username);
            result.setData(count);
            result.setMsg("成功，删除条数为data");
            result.setCode(200);
        }
        catch (Exception e) {
            result.setMsg("失败");
            result.setCode(500);
        }
        return result;
    }

}
