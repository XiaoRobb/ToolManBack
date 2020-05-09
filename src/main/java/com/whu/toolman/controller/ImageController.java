package com.whu.toolman.controller;

import ch.qos.logback.core.encoder.EchoEncoder;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.core.util.FileUtil;
import com.jhlabs.image.*;
import com.whu.toolman.common.Result;
import com.whu.toolman.util.AudioConvertUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.whu.toolman.picHandle.*;
@Controller
@RequestMapping("/image")
public class ImageController {
    /*
    **para
    * dstPath 为本地路径
    * size为图片需要压缩到的最大大小
    * style为选择滤镜风格
    * accuacy为图片压缩的初始准确度
    */

    @GetMapping("/imagerendering")
    //图片滤镜添加
    public Result ImageRendering(@RequestParam("style")int style , @RequestParam("picture") MultipartFile source, @RequestParam("dstPath")String dstPath) throws IOException{
        Result result = new Result();
        File file = null;
        try {

             file=File.createTempFile("tmp", null);
             source.transferTo(file);
             file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
            result.setCode(200);
        }
        try {
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
            result.setData(dstimage);
            ImageIO.write(dstimage, "png", output);
            result.setMsg("Success！");

        }catch (IOException e){
            System.out.print(e.getMessage());
        }

        return result;
    }

    @GetMapping("/imagecompress")
    //图片压缩
    public Result compressImage(@RequestParam("picture") MultipartFile srcPath, @RequestParam("dstPath") String dstPath, @RequestParam("size")long desFileSize){
        double accuacy = 0.9;
        Result result = compressImage(srcPath , dstPath , desFileSize , accuacy);
        return result;
    }


    public Result compressImage(@RequestParam("picture") MultipartFile srcPath, @RequestParam("dstPath") String dstPath, @RequestParam("size")long desFileSize, @RequestParam("accuacy")double accuacy){
        Result result = new Result();
        File file = null;
        try {

            file=File.createTempFile("tmp", null);
            srcPath.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{

            long srcImgSize = file.length();
            System.out.println("原图："+ srcPath+"大小为："+srcImgSize/1024 + "KB");
            BufferedImage srcimage= ImageIO.read(file);
            //转化为jpg文件
            Thumbnails.of(srcimage).scale(1f).toFile(dstPath);
            //递归压缩
            compressImageUsage(dstPath, desFileSize,accuacy);
            File dstImg = new File(dstPath);
            long dstImgSize = dstImg.length();
            System.out.println("压缩完成！ 压缩图："+ dstPath+"大小为："+dstImgSize/1024 + "KB");

        }catch (IOException e1){
            e1.printStackTrace();
        }
        result.setMsg("OK");
        result.setData(dstPath);
        return result;
    }

    //图片压缩子步骤
    public void compressImageUsage(String dstPath, long size, double accuracy){
        File srcImage = new File(dstPath);
        long srcImageSize = srcImage.length();
        //判断大小是否达标
        if(srcImageSize <= size * 1024){
            return;
        }
        try{
            BufferedImage bim = ImageIO.read(srcImage);
            int srcImageWidth = bim.getWidth();
            int srcImageHeight = bim.getHeight();
            int dstImageWith = new BigDecimal(srcImageWidth).multiply(new BigDecimal(accuracy)).intValue();
            int dstImageHeight = new BigDecimal(srcImageHeight).multiply(new BigDecimal(accuracy)).intValue();

            Thumbnails.of(dstPath).size(dstImageWith,dstImageHeight).outputQuality(accuracy).toFile(dstPath);
            compressImageUsage(dstPath,size,accuracy);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
