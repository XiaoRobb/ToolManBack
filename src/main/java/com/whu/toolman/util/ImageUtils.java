package com.whu.toolman.util;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class ImageUtils {
    //图片压缩子步骤
    public static void compressImageUsage(String dstPath, long size, double accuracy) throws Exception{
        File srcImage = new File(dstPath);
        long srcImageSize = srcImage.length();
        //判断大小是否达标
        if(srcImageSize <= size * 1024){
            return;
        }
        BufferedImage bim = ImageIO.read(srcImage);
        int srcImageWidth = bim.getWidth();
        int srcImageHeight = bim.getHeight();
        int dstImageWith = new BigDecimal(srcImageWidth).multiply(new BigDecimal(accuracy)).intValue();
        int dstImageHeight = new BigDecimal(srcImageHeight).multiply(new BigDecimal(accuracy)).intValue();
        Thumbnails.of(dstPath).size(dstImageWith,dstImageHeight).outputQuality(accuracy).toFile(dstPath);
        compressImageUsage(dstPath,size,accuracy);
    }
}
