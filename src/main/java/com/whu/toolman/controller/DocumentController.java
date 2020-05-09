package com.whu.toolman.controller;

import com.aspose.cells.DateTime;
import com.aspose.words.SaveFormat;
import com.whu.toolman.common.Result;
import com.whu.toolman.service.RecordService;
import com.whu.toolman.service.UserService;
import com.whu.toolman.util.DocumentConvertUtils;
import com.whu.toolman.util.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author 周宏俊
 * @description 文档处理控制器
 * @since 2020/4/27
 */
@RestController
@RequestMapping("/document")
public class DocumentController {
    @Resource
    RecordService recordService;

    @PostMapping("/word2pdf")
    public Result word2pdf(@RequestParam("source")MultipartFile document, @RequestParam("format")String format, @RequestParam("username") String username){
        Result result = new Result();
        int fileFormat;
        String filename = document.getName();
        switch (format.toLowerCase()){
            case  "html":
                fileFormat = SaveFormat.HTML;
                filename +=".html";
                break;
            case  "png":
                fileFormat = SaveFormat.PNG;
                filename +=".png";
                break;
            case "jpeg":
                fileFormat = SaveFormat.JPEG;
                filename +=".jpeg";
                break;
            case "doc":
                fileFormat = SaveFormat.DOC;
                filename +=".doc";
                break;
            case "docx":
                fileFormat = SaveFormat.DOCX;
                filename += ".docx";
                break;
            default:
                fileFormat = SaveFormat.PDF;
                filename +=".pdf";
        }
        try{
            File file = FileUtils.multipartFileToFile(document);
            String location = DocumentConvertUtils.changeFormat(file,  filename, fileFormat);
            result.setCode(200);
            result.setMsg("转换成功");
            result.setData(location);
            if(username != "default"){  //插入记录
                //获取当前时间
                Date date = new Date();
                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
                recordService.insertRecord(username, "文档", "把" + document.getName() + "文件转为" + format+"格式");
            }
            return result;
        }catch (Exception e){
            result.setCode(205);
            result.setMsg("转换失败");
            return result;
        }
    }
}
