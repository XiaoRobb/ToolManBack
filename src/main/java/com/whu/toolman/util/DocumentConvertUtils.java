package com.whu.toolman.util;

import com.aspose.words.Document;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
}
