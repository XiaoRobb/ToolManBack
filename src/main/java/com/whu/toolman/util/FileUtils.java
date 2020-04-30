package com.whu.toolman.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ：qx.w
 * @description：文件处理工具类
 * @modified By：
 * @since ：Created in 2020/4/24 14:07
 */
public class FileUtils {
    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

    /**
     * 根据文件url获取文件名（包含后缀名）
     *
     * @param url 文件url
     * @return 文件名（包含后缀名）
     */
    public static String getOriginalFileName(String url) {
        String[] sarry = url.split("/");
        return sarry[sarry.length - 1];
    }

    /**
     * 根据文件url获取文件名（不包含后缀名）
     *
     * @param url 文件url
     * @return 文件名（包含后缀名）
     */
    public static String getNameWithoutSuffix(String url) {
        String originalFileName = getOriginalFileName(url);
        return originalFileName.substring(0, originalFileName.indexOf("."));
    }

    /**
     * 根据文件获取文件名（不包含后缀名）
     *
     * @param file 文件
     * @return 文件名（不包含后缀名）
     */
    public static String getNameWithoutSuffix(File file) {
        String originalFileName = file.getName();
        return originalFileName.substring(0, originalFileName.indexOf("."));
    }

    /**
     * 根据文件获取文件后缀
     *
     * @param file 文件
     * @return 文件后缀名
     */
    public static String getSuffix(File file) {
        String originalFileName = file.getName();
        return originalFileName.substring(originalFileName.indexOf(".") + 1);
    }
}
