package com.whu.toolman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import net.coobird.thumbnailator.*;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

@SpringBootApplication
public class ToolmanApplication {

    public static void main(String[] args) {

        compressImage("C:\\Users\\陈嘉\\Desktop\\a.jpg","C:\\Users\\陈嘉\\Desktop\\b.jpg",1024,0.9);
        SpringApplication.run(ToolmanApplication.class, args);


    }

    //图片压缩
    public static String compressImage(String srcPath, String dstPath, long desFileSize, double accuacy){
        if(StringUtils.isEmpty(srcPath) || (!new File(srcPath).exists())){
            System.out.println("文件路径有误！");
            return null;
        }



        try{
            File srcImg = new File(srcPath);
            long srcImgSize = srcImg.length();
            System.out.println("原图："+ srcPath+"大小为："+srcImgSize/1024 + "KB");

            //转化为jpg文件
            Thumbnails.of(srcPath).scale(1f).toFile(dstPath);
            //递归压缩
            compressImageUsage(dstPath, desFileSize,accuacy);
            File dstImg = new File(dstPath);
            long dstImgSize = dstImg.length();
            System.out.println("压缩完成！ 压缩图："+ dstPath+"大小为："+dstImgSize/1024 + "KB");

        }catch (IOException e1){
            e1.printStackTrace();
        }
        return dstPath;
    }

    //图片压缩子步骤
    public static void compressImageUsage(String dstPath, long size, double accuracy){
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

};



