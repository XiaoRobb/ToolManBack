package com.whu.toolman.service;

import com.aspose.words.Document;
import com.whu.toolman.service.impl.DocumentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DocumentServiceImptTest {
    @Test
    void changeFormatTest() throws Exception{
        Document doc = new Document("E:/test.doc");
        DocumentServiceImpl documentService = new DocumentServiceImpl();
        String temp = documentService.changeFormat(doc, "test.pdf",13);
        System.out.printf(temp);
    }
}
