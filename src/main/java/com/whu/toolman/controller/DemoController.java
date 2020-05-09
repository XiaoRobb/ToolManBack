package com.whu.toolman.controller;

import com.whu.toolman.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
                       @RequestParam("picture") MultipartFile file) {
        Result result = new Result();
        result.setCode(200);
        List<String> cjs = new ArrayList<>();
        cjs.add("cj1");
        cjs.add("陈家2");
        System.out.println(file.getOriginalFilename());
        result.setMsg("你好," + name);
        return result;
    }

    @GetMapping("/pic")
    public Result demdo(@RequestParam("name") String name,
                       @RequestParam("picture") MultipartFile file) {
        Result result = new Result();
        result.setCode(200);

        File dest = new File("D:\\ToolManResources\\" + file.getOriginalFilename());
        try {
            file.transferTo(dest);
        }
        catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }

        List<String> cjs = new ArrayList<>();
        cjs.add("cj1");
        cjs.add("陈家2");
        System.out.println(file.getOriginalFilename());
        result.setMsg("你好," + name + "///" + "http://localhost:9010/ToolManResources/" + file.getOriginalFilename());
        return result;
    }

}
