package com.whu.toolman.controller;

import com.aspose.words.SaveFormat;
import com.whu.toolman.common.Result;
import com.whu.toolman.util.DocumentConvertUtils;
import com.whu.toolman.util.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author 周宏俊
 * @description 文档处理控制器
 * @since 2020/4/27
 */
@RestController
@RequestMapping("/document")
public class DocumentController {
    @PostMapping("/word2pdf")
    public Result word2pdf(@RequestParam("source")MultipartFile document){
        Result result = new Result();
        try{
            File file = FileUtils.multipartFileToFile(document);
            String location = DocumentConvertUtils.changeFormat(file, document.getName() + ".pdf", SaveFormat.PDF);
            result.setCode(200);
            result.setMsg("转换成功");
            result.setData(location);
            return result;
        }catch (Exception e){
            result.setCode(201);
            result.setMsg("转换失败");
            return result;
        }
    }
}
