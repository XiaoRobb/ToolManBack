package com.whu.toolman.service;
import com.aspose.words.Document;

/**
 * @author 周宏俊
 * @description 文档处理服务接口
 * @since 2020/4/27
 */
public interface DocumentService {
    String changeFormat(Document doc, String filename, int format);
}
