package com.whu.toolman.util;

import com.aspose.words.*;

import java.io.*;
import java.util.zip.ZipEntry;

/**
 * @author 周宏俊
 * @description 文档处理控制器
 * @since 2020/4/27
 */
public class DocumentConvertUtils {

    //转为除PNG、JPG的其他格式
    public static String changeFormat(Document document, String filename, int format) throws Exception{
        if(format == SaveFormat.PNG || format == SaveFormat.JPEG){
            //保存为图片
            //创建图片文件夹
            String dirPath = PictureUtil.filePathDocument + filename + "\\";
            File dir = new File(dirPath);
            if(dir.exists()){
                dir.delete();
            }
            dir.mkdir();
            //转为图片格式
            ImageSaveOptions iso = new ImageSaveOptions(format);
            iso.setPrettyFormat(true);
            iso.setUseAntiAliasing(true);
            iso.setJpegQuality(80);
            for (int i = 0; i < document.getPageCount(); i++)
            {
                iso.setPageIndex(i);
                String suffix = format == SaveFormat.JPEG ? ".jpeg" : ".png";
                document.save(dirPath+ i + suffix, iso);
            }
            String zipPath = PictureUtil.filePathDocument + filename +".zip";
            ZipUtIls zipUtIls = new ZipUtIls(zipPath);
            zipUtIls.compress(dirPath);
            return PictureUtil.url + "document/" + filename + ".zip";
        }else{  //其他格式
            //更新文件名
            switch (format){
                case  SaveFormat.HTML:
                    filename +=".html";
                    break;
                case  SaveFormat.PNG:
                    filename +=".png";
                    break;
                case SaveFormat.JPEG:
                    filename +=".jpeg";
                    break;
                case SaveFormat.DOC:
                    filename +=".doc";
                    break;
                case SaveFormat.DOCX:
                    filename += ".docx";
                    break;
                default:
                    filename +=".pdf";
            }
            //设置目标文件的名称
            String location = PictureUtil.filePathDocument  + filename;
            //新建目标文件
            File target = new File(location);
            FileOutputStream outputStream = new FileOutputStream(target);
            if(format == SaveFormat.HTML){ //转HTML
                HtmlSaveOptions saveOptions = new HtmlSaveOptions(format);
                saveOptions.setExportHeadersFootersMode(ExportHeadersFootersMode.NONE); // HtmlSaveOptions的其他设置信息请参考相关API
                //ByteArrayOutputStream htmlStream = new ByteArrayOutputStream();
                document.save(outputStream, saveOptions);
            }else if(format == SaveFormat.PDF){

                DocSaveOptions saveOptions = new DocSaveOptions(format);;
                //保存文件
                document.save(outputStream, saveOptions);
            }else {
                //保存文件
                document.save(outputStream, format);
            }
            outputStream.close();
            return PictureUtil.url + "document/" + filename;
        }
    }
}
