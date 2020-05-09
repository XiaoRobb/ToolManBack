package com.whu.toolman.util;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.SaveFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;

/**
 * @author 周宏俊
 * @description 文档处理控制器
 * @since 2020/4/27
 */
public class DocumentConvertUtils {

    public static String changeFormat(File file, String filename, int format) throws Exception{
        String location = "D:/ToolManResources/Document/" + filename;
        Document doc = new Document(new FileInputStream(file));
        File outPutFile = new File(location);
        FileOutputStream outputStream = new FileOutputStream(outPutFile);
        doc.save(outputStream, format);
        return "http://localhost:9010/ToolManResources/Document/" + filename;
    }

    public static String docToImage(File file, String filename, int format) throws Exception{
        if(format != SaveFormat.PNG && format != SaveFormat.JPEG){
            return "";
        }
        String fileNameReal = filename.substring(0, filename.length()-4);
        String path = "D:/ToolManResources/Document/" + fileNameReal + "/";
        File f = new File(path);
        if(f.exists()){
            f.delete();
        }
        f.mkdir();
        Document doc = new Document(new FileInputStream(file));
        ImageSaveOptions iso = new ImageSaveOptions(format);
        iso.setPrettyFormat(true);
        iso.setUseAntiAliasing(true);
        iso.setJpegQuality(80);
        for (int i = 0; i < doc.getPageCount(); i++)
        {
            iso.setPageIndex(i);
            String suffix = format == SaveFormat.JPEG ? ".jpeg" : ".png";
            doc.save(path+i+ suffix, iso);
        }
        String zipPath = "D:/ToolManResources/Document/" + fileNameReal +".zip";
        ZipUtIls zipUtIls = new ZipUtIls(zipPath);
        zipUtIls.compress(path);
        return zipPath;
    }
}
