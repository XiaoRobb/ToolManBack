package com.whu.toolman.controller;

import com.aspose.cells.DateTime;
import com.aspose.words.*;
import com.whu.toolman.common.Result;
import com.whu.toolman.service.RecordService;
import com.whu.toolman.service.UserService;
import com.whu.toolman.service.impl.RecordServiceImpl;
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
import java.nio.charset.Charset;
import java.sql.SQLException;
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
    RecordServiceImpl recordService;

    @PostMapping("/documentChange")
    public Result changeDocument(@RequestParam("source")MultipartFile document, @RequestParam("format")String format, @RequestParam("username")String username){
        //新建返回结果
        Result result = new Result();
        //文件名称
        String filename = document.getOriginalFilename().substring(0, document.getOriginalFilename().indexOf("."));
        //目标格式
        int fileFormat;
        switch (format.toLowerCase()){
            case  "html":
                fileFormat = SaveFormat.HTML;
                //filename +=".html";
                break;
            case  "png":
                fileFormat = SaveFormat.PNG;
                //filename +=".png";
                break;
            case "jpeg":
                fileFormat = SaveFormat.JPEG;
                //filename +=".jpeg";
                break;
            case "doc":
                fileFormat = SaveFormat.DOC;
                //filename +=".doc";
                break;
            case "docx":
                fileFormat = SaveFormat.DOCX;
                //filename += ".docx";
                break;
            default:
                fileFormat = SaveFormat.PDF;
                //filename +=".pdf";
        }
        //转换
        try{
            //将流转换为Document
            Document doc = new Document(document.getInputStream());
            //目标文件地址
            String location = DocumentConvertUtils.changeFormat(doc,  filename, fileFormat);
            if(location == "" || location == null){
                result.setCode(500);
                result.setMsg("转换失败");
                return result;
            }
            result.setCode(200);
            result.setMsg("转换成功");
            result.setData(location);
            if(username != "default"){  //插入记录
                recordService.insertRecord(username, "文档", "把文件转为" + format +"格式");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            result.setCode(250);
            result.setMsg("转换成功，记录填写失败");
        }catch (UnsupportedFileFormatException e){
            System.out.println(e.getMessage());
            result.setCode(500);
            result.setMsg("转换失败");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            result.setCode(500);
            result.setMsg("转换失败");
        }
        return result;
    }
}
