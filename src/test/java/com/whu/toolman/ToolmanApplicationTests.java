package com.whu.toolman;
import com.whu.toolman.picHandle.PicHandle;
import com.whu.toolman.picHandle.PicRenderingHandle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ToolmanApplicationTests {

    @Test
    void contextLoads() throws IOException {
        PicRenderingHandle.ImageRendering(7,"C:\\Users\\陈嘉\\Desktop\\c.jpg","C:\\Users\\陈嘉\\Desktop\\d.png");
    }

}
