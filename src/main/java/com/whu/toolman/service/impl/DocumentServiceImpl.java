package com.whu.toolman.service.impl;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.whu.toolman.service.DocumentService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author 周宏俊
 * @description 文档处理服务实现
 * @since 2020/4/27
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    private static String filepath = System.getProperty("user.dir");
    @Override
    public String changeFormat(Document doc, String filename, int format) {
        String location = filepath + "/src/main/resources/document/" + filename;
        File file = new File(location);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            doc.save(outputStream, format);
        }catch (Exception e){
            location = "";
        }
        return location;
    }
}
