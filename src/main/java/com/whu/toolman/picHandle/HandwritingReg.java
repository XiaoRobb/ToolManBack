package com.whu.toolman.picHandle;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.whu.toolman.util.FileUtils;
import com.whu.toolman.util.HttpUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class HandwritingReg {
    // 手写文字识别webapi接口地址
    private static final String WEBOCR_URL = "http://webapi.xfyun.cn/v1/service/v1/ocr/handwriting";
    // 应用APPID(必须为webapi类型应用,并开通手写文字识别服务,参考帖子如何创建一个webapi应用：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=36481)
    private static final String TEST_APPID = "5eba1748";
    // 接口密钥(webapi类型应用开通手写文字识别后，控制台--我的应用---手写文字识别---相应服务的apikey)
    private static final String TEST_API_KEY = "2e8f7f5d2b60f07bc937d651a7f06e14";
    // 测试图片文件存放位置
    private static final String IMAGE_FILE_PATH = "D://c.jpg";

    /**
     * 组装http请求头
     *
     */
    public static String RecognizeHandWritinf(File srcPath) throws IOException, ParseException {
        String language = "cn|en";
        String location = "false";
        // 系统当前时间戳
        String X_CurTime = System.currentTimeMillis() / 1000L + "";
        // 业务参数
        String param = "{\"language\":\""+language+"\""+",\"location\":\"" + location + "\"}";
        String X_Param = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
        // 接口密钥
        String apiKey = TEST_API_KEY;
        // 讯飞开放平台应用ID
        String X_Appid = TEST_APPID;
        // 生成令牌
        String X_CheckSum = DigestUtils.md5Hex(apiKey + X_CurTime + X_Param);
        // 组装请求头
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", X_Param);
        header.put("X-CurTime", X_CurTime);
        header.put("X-CheckSum", X_CheckSum);
        header.put("X-Appid", X_Appid);
        byte[] imageByteArray = FileUtils.File2byte(srcPath);

        String imageBase64 = new String(Base64.encodeBase64(imageByteArray), "UTF-8");
        String bodyParam = "image=" + imageBase64;
        String result = HttpUtil.doPost(WEBOCR_URL, header, bodyParam);

        System.out.println(result);
        return result;
    }
}
