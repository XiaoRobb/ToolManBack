package com.whu.toolman.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author ：qx.w
 * @description：处理图片上传下载的工具类
 * @modified By：
 * @since ：Created in 2020/5/2 15:07
 */
@Component
public class PictureUtil {
    public static String filePathDocument;
    public static String filePathUser;
    public static String filePathAudio;
    public static String filePathPicture;

    public static String url;
    @Value("${url}")
    public void setUrl(String surl) {
        url = surl;
    }

    @Value("${filePath}")
    public void setFilePathDocument(String path) {filePathDocument = path + "document/";}

    @Value("${filePath}")
    public void setFilePathAudio(String path) {filePathAudio = path + "audio/";}

    @Value("${filePath}")
    public void setFilePathUser(String path) {filePathUser = path + "user/";}

    @Value("${filePath}")
    public void setFilePathPicture(String path) {filePathPicture = path + "picture/";}

    /**
     * 将文件上传至服务器
     * @param image
     * @param UUID
     * @param filePath
     * @return 直接可以放到数据库里面的路径
     */
    public static String uploadImage(MultipartFile image, String UUID, String filePath){
        String photoName = image.getOriginalFilename();  //获取照片文件名
        String suffixName = photoName.substring(photoName.lastIndexOf(".")).toLowerCase();  //获取文件后缀
        if((!suffixName.equals(".bmp"))
                &&(!suffixName.equals(".jpg"))
                &&(!suffixName.equals(".jpeg"))
                &&(!suffixName.equals(".png"))
                &&(!suffixName.equals(".gif"))){
            return "上传失败，请上传bmp、jpg、jpeg、png、gif文件！";
        }

        String photoUrl = filePath + UUID + suffixName;  //生成新的存储地址

        File dest = new File(photoUrl);
        try {
            image.transferTo(dest);
        } catch (IOException e) {
            //存储图片至服务器失败
            return "图片上传至服务器失败！";
        }
        return url + UUID + suffixName;
    }


    /**
     * 删除服务器上的文件
     * @param path 需要数据库存的地址
     * @param filePath “user”“book”填写的时候调用静态变量
     */
    public static boolean deletePhoto(String path, String filePath){
//        objectTable obj = objRepository.findById(UUID).get();
//        String photoName = obj.getPhotoUrl();
//        String suffixName = photoName.substring(photoName.lastIndexOf(".")).toLowerCase();
//
//        File file = new File(filePath + UUID + suffixName);
//        file.delete();
        int lastIndexOf = path.lastIndexOf("/");
        String img_path = path.substring(lastIndexOf + 1, path.length());
        try {
            if(!img_path.equals("default.jpg"))
            {
                img_path = filePath + img_path;
                File file = new File(img_path);
                file.delete();
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}
