package com.whu.toolman.controller;

import com.whu.toolman.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：qx.w
 * @description 这是示例
 * @modified By：
 * @since ：Created in 2020/3/4 14:11
 */
@RestController
public class DemoController {

    @GetMapping("/demo")
    public Result demo(@RequestParam("name") String name,
                       @RequestParam("picture")MultipartFile file) {
        Result result = new Result();
        result.setCode(200);
        List<String> cjs = new ArrayList<>();
        cjs.add("cj1");
        cjs.add("陈家2");
        result.setData(file);
        result.setMsg("你好," + name);
        return result;
    }


}
