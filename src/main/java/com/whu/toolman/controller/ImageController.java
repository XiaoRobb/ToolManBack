package com.whu.toolman.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.aspose.words.SaveFormat;
import com.whu.toolman.picHandle.*;
import ch.qos.logback.core.util.FileUtil;
import com.jhlabs.image.*;
import com.whu.toolman.common.Result;
import com.whu.toolman.service.impl.RecordServiceImpl;
import com.whu.toolman.util.*;
import com.whu.toolman.util.ImageUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.whu.toolman.picHandle.*;
@RestController
@RequestMapping("/image")
public class ImageController {

    @Resource
    RecordServiceImpl recordService;
    /*
    **para
    * dstPath 为本地路径
    * size为图片需要压缩到的最大大小
    * style为选择滤镜风格
    * accuacy为图片压缩的初始准确度
    */

    @PostMapping("/imagerendering")
    //图片滤镜添加
    public Result ImageRendering(@RequestParam("style")int style , @RequestParam("picture") MultipartFile source, @RequestParam("username") String username){
        int fileFormat;
        String filename = source.getName();

        String dstPath = PictureUtil.filePathPicture + filename + ".png";
        Result result = new Result();
        File file = null;
        try {
            file=File.createTempFile("tmp", null);
            source.transferTo(file);
            file.deleteOnExit();
            WaterFilter a = new WaterFilter();
            //File file = new File(srcPath);//本地图片
            BufferedImage srcimage= ImageIO.read(file);
            int srcImageWidth = srcimage.getWidth();
            int srcImageHeight = srcimage.getHeight();

            File output = new File(dstPath);//本地图片
            BufferedImage dstimage= new BufferedImage(srcImageWidth, srcImageHeight,
                    BufferedImage.TYPE_INT_ARGB);

            switch(style){
                case 0:
                    DisplaceFilter displaceFilter = new DisplaceFilter();//玻璃效果
                    displaceFilter.filter(srcimage,dstimage);
                    break;
                case 1:
                    KaleidoscopeFilter kaleidoscopeFilter = new KaleidoscopeFilter();//万花筒效果
                    kaleidoscopeFilter.filter(srcimage,dstimage);
                    break;
                case 2:
                    MarbleFilter marbleFilter = new MarbleFilter();
                    marbleFilter.filter(srcimage,dstimage);
                    break;
                case 3:
                    MirrorFilter mirrorFilter = new MirrorFilter();//镜像效果
                    mirrorFilter.filter(srcimage,dstimage);
                    break;
                case 4:
                    SwimFilter swimFilter = new SwimFilter();//水下效果
                    swimFilter.filter(srcimage,dstimage);
                    break;
                case 5:
                    WaterFilter waterFilter = new WaterFilter();//模拟水面波纹效果
                    waterFilter.filter(srcimage,dstimage);
                    break;
                case 6:
                    GrayscaleFilter grayscaleFilter = new GrayscaleFilter();//灰度图
                    grayscaleFilter.filter(srcimage,dstimage);
                    break;
                case 7:
                    InvertFilter invertFilter = new InvertFilter();//反转色彩
                    invertFilter.filter(srcimage,dstimage);
                    break;
                default:
                    a.filter(srcimage,dstimage);
                    break;
            }
            ImageIO.write(dstimage, "png", output);

            result.setCode(200);
            result.setMsg("添加成功");
            result.setData(PictureUtil.url + "picture/" + filename + ".png");
            if(username != "default"){  //插入记录
                recordService.insertRecord(username, "图片", "滤镜添加");
            }
        } catch (Exception e){
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("添加失败");
        }
        return result;
    }

    //图片压缩
    @PostMapping("/imagecompress")
    public Result compressImage(@RequestParam("picture") MultipartFile srcPath, @RequestParam("size")long desFileSize, @RequestParam("username") String username){
        double accuacy = 0.9;
        Result result = new Result();

        String filename = srcPath.getName();

        String dstPath = PictureUtil.filePathPicture + filename + ".jpg";


        File file = null;
        try {

            file=File.createTempFile("tmp", null);
            srcPath.transferTo(file);
            file.deleteOnExit();
            long srcImgSize = file.length();
            System.out.println("原图："+ srcPath+"大小为："+srcImgSize/1024 + "KB");
            BufferedImage srcimage= ImageIO.read(file);
            //转化为jpg文件
            Thumbnails.of(srcimage).scale(1f).toFile(dstPath);
            //递归压缩
            ImageUtils.compressImageUsage(dstPath, desFileSize,accuacy);
            File dstImg = new File(dstPath);
            long dstImgSize = dstImg.length();
            System.out.println("压缩完成！ 压缩图："+ dstPath+"大小为："+dstImgSize/1024 + "KB");
            if(username != "default"){  //插入记录
                recordService.insertRecord(username, "图片", "图片压缩");
            }
            result.setMsg("OK");
            result.setData(PictureUtil.url + "picture/" + filename + ".jpg");
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("压缩失败");
        }
        return result;
    }


    @PostMapping("/handwriteingREC")
    public Result  constructHeader(@RequestParam("picture") MultipartFile srcPath, @RequestParam("username") String username) {
        Result result = new Result();
        File file = null;
        try {

            file=File.createTempFile("tmp", null);
            srcPath.transferTo(file);
            file.deleteOnExit();
            if(username != "default"){  //插入记录
                recordService.insertRecord(username, "图片", "识别手写字体");
            }
            String src = HandwritingReg.RecognizeHandWritinf(file);
            String data = JsonUtil.GetContent(src);
            result.setCode(200);
            result.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("识别失败");
        }
        return result;
    }
}
