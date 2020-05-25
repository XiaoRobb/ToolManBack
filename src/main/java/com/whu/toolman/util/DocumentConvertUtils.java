package com.whu.toolman.util;

import com.aspose.words.*;

import java.io.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;

/**
 * @author 周宏俊
 * @description 文档处理控制器
 * @since 2020/4/27
 */
public class DocumentConvertUtils {

    //转为除PNG、JPG的其他格式
    public static String changeFormat(Document document, String filename, int format) throws Exception{
        if(format == SaveFormat.PNG || format == SaveFormat.JPEG || format == SaveFormat.HTML){
            //保存为图片、html
            //创建文件夹
            String dirPath = PictureUtil.filePathDocument + filename + "/";
            File dir = new File(dirPath);
            if(dir.exists()){
                for (File file :
                        dir.listFiles()) {
                    file.delete();
                }
                dir.delete();
            }
            dir.mkdir();
            if(format == SaveFormat.HTML){
                HtmlSaveOptions saveOptions = new HtmlSaveOptions(format);
                HtmlSaveOptions options = new HtmlSaveOptions(SaveFormat.HTML);
                options.setExportTextInputFormFieldAsText(true);
                options.setImagesFolder(dirPath);
                document.save(dirPath+ filename + ".html", saveOptions);
            }else{
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
            }
            String zipPath = PictureUtil.filePathDocument + filename +".zip";
            ZipUtIls zipUtIls = new ZipUtIls(zipPath);
            zipUtIls.compress(dirPath);
            return PictureUtil.url + "document/" + filename + ".zip";

        }else{  //其他格式
            //更新文件名
            switch (format){
                case SaveFormat.DOC:
                    filename +=".doc";
                    break;
                case SaveFormat.DOCX:
                    filename += ".docx";
                    break;
                default:
                    filename +=".pdf";
            }
            if(format == SaveFormat.DOC){
                AtomicReference<Document> doc = new AtomicReference<>(new Document());
                DocSaveOptions options = new DocSaveOptions(SaveFormat.DOC);
                options.setSaveRoutingSlip(true);
                doc.get().save(PictureUtil.filePathDocument  + filename, options);
            }
            //设置目标文件的名称
            String location = PictureUtil.filePathDocument  + filename;
            //新建目标文件
            File target = new File(location);
            FileOutputStream outputStream = new FileOutputStream(target);
            //保存文件
            document.save(outputStream, format);
            outputStream.close();
            return PictureUtil.url + "document/" + filename;
        }
    }
}
