package com.whu.toolman.picHandle;
import  com.jhlabs.image.*;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.util.StringUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
public class PicRenderingHandle {
    //图片滤镜添加调用javaImageFilter图像处理库
    public static void ImageRendering(int style , String srcPath, String dstPath) throws IOException {
        try{
            WaterFilter a = new WaterFilter();


            File file = new File(srcPath);//本地图片
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



           // d.filter(srcimage,dstimage);
            //e.filter(dstimage,dstimage);
            ImageIO.write(dstimage, "png", output);

        }catch (IOException e){
            System.out.print(e.getMessage());
        }

    }
}
