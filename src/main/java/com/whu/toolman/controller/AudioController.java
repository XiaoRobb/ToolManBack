package com.whu.toolman.controller;

import com.whu.toolman.common.Result;
import com.whu.toolman.util.AudioConvertUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

/**
 * @author ：qx.w
 * @description：音频处理
 * @modified By：
 * @since ：Created in 2020/4/23 18:24
 */
@Controller
@RequestMapping("/audio")
public class AudioController {

    @GetMapping("/wav2Mp3")
    public void wav2Mp3(@RequestParam("source")MultipartFile source, HttpServletResponse response) {
        Result result = new Result();
        File target = AudioConvertUtils.wave2Mp3(source);
        System.out.println(target.getAbsolutePath());
        System.out.println(target.getName());
        result.setData(target.getAbsolutePath());

        try {
            //通过路径得到一个输入流
            FileInputStream fis = new FileInputStream(target);
            //创建字节输出流
            ServletOutputStream outputStream = response.getOutputStream();
            //执行输出操作
            int len = 1;
            byte[] b = new byte[1024];
            while((len = fis.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
            /*方法内可以不关流*/
            outputStream.close();
            fis.close();
            //得到要下载的文件名
            String filename = target.getName();

            //设置文件名的编码
            filename = URLEncoder.encode(filename, "UTF-8");

            //告知客户端要下载的文件
            response.setHeader("content-disposition", "attachment;filename=" + filename);
            response.setHeader("content-type", "audio/mp3");
        }
        catch (Exception e) {

        }

    }

}
