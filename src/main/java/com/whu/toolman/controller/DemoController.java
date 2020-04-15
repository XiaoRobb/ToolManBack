package com.whu.toolman.controller;

import com.whu.toolman.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：qx.w
 * @description：
 * @modified By：
 * @since ：Created in 2020/3/4 14:11
 */
@RestController
public class DemoController {

    @GetMapping("/demo")
    public Result demo(@RequestParam("name") String name) {
        Result result = new Result();
        result.setCode(200);
        result.setData(name);
        result.setMsg("你好," + name);
        return result;
    }


}
