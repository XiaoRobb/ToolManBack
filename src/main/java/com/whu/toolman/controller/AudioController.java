package com.whu.toolman.controller;

import com.whu.toolman.common.Result;
import com.whu.toolman.picHandle.HandwritingReg;
import com.whu.toolman.service.RecordService;
import com.whu.toolman.service.impl.RecordServiceImpl;
import com.whu.toolman.util.AudioConvertUtils;
import com.whu.toolman.util.FileUtils;
import com.whu.toolman.util.PictureUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaObject;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.sql.SQLException;

/**
 * @author ：qx.w
 * @description：音频处理
 * @modified By：
 * @since ：Created in 2020/4/23 18:24
 */
@CrossOrigin
@RestController
@RequestMapping("/audio")
public class AudioController {
    @Resource
    RecordServiceImpl recordService;

    @GetMapping("/wav2Mp3")
    public Result wav2Mp3(@RequestParam("source")MultipartFile source, @RequestParam("username")String username) {
        Result result = new Result();
        String url = PictureUtil.filePathAudio;
        try {
            File target = AudioConvertUtils.wave2Mp3(source);
            System.out.println(target.getAbsolutePath());
            System.out.println(target.getName());
            url += "/" + target.getName();
            result.setCode(200);
            result.setMsg("处理成功");
            result.setData(url);
            if(username != "default"){  //插入记录
                recordService.insertRecord(username, "音频", "把文件转为mp3格式");
            }
        } catch (Exception e) {
            result.setCode(500);
            result.setMsg("处理失败");
            System.out.println(e.getMessage());
        }
        return result;
    }

    @GetMapping("/mp32Wave")
    public Result mp32Wave(@RequestParam("source")MultipartFile source, @RequestParam("username") String username) {
        Result result = new Result();
        String url = PictureUtil.filePathAudio;
        try {
            String fileName = AudioConvertUtils.mp32Wave(source);
            if (fileName == null) {
                result.setCode(500);
                result.setMsg("处理失败");
                return result;
            }
            url += "/" + fileName;
            result.setCode(200);
            result.setMsg("处理成功");
            result.setData(url);
            if(username != "default"){  //插入记录
                recordService.insertRecord(username, "音频", "把文件转为wav格式");
            }
        } catch (Exception e) {
            result.setCode(500);
            result.setMsg("处理失败");
            System.out.println(e.getMessage());
        }
        return result;
    }


    @GetMapping("/audiofromavi")
    public Result picAudioFromVideo(@RequestParam("source")MultipartFile sourceFile, @RequestParam("username") String username) throws Exception {
        String filename = sourceFile.getName();
        Result result = new Result();
        String dstPath = PictureUtil.filePathAudio +filename + ".wav";
        File source = FileUtils.multipartFileToFile(sourceFile);

        File target = new File(dstPath);
        try{
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("pcm_s16le");
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("wav");
            attrs.setAudioAttributes(audio);
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);
            if(username != "default"){  //插入记录
                recordService.insertRecord(username, "音频", "提取音频");
            }
        } catch (Exception e){
            result.setMsg(e.getMessage());
            result.setCode(500);
            result.setMsg("提取失败");
            return result;
        }
        result.setData(dstPath);
        result.setMsg("提取成功");
        result.setCode(200);
        return result;
    }
}
