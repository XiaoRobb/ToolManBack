package com.whu.toolman.service;

import com.aspose.words.Document;
import com.whu.toolman.util.DocumentConvertUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
public class DocumentServiceImptTest {
    @Test
    void changeFormatTest() throws Exception{
        File file = new File("E:/test.doc");
        String temp = DocumentConvertUtils.changeFormat(file, "test.pdf",40);
        System.out.printf(temp);
    }
}
