package com.whu.toolman.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
public class JsonUtilTest {
    @Test
    void testJson(){
        String temp = "{\"code\":\"0\",\"data\":{\"block\":[{\"type\":\"text\",\"line\":[{\"confidence\":1,\"word\":[{\"content\":\"2017级-国软-王崎珑\"}]},{\"confidence\":1,\"word\":[{\"content\":\"这是中文\"}]},{\"confidence\":1,\"word\":[{\"content\":\"stay\"},{\"content\":\"with\"},{\"content\":\"you\"}]}]}]},\"desc\":\"success\",\"sid\":\"wcr00342659@gz50bf1216b90b463000\"}";
        temp = JsonUtil.GetContent(temp);
        System.out.printf(temp);
    }
}
