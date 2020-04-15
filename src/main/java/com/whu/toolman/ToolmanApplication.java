package com.whu.toolman;

import com.whu.toolman.picHandle.PicHandle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToolmanApplication {

    public static void main(String[] args) {
        PicHandle p = new PicHandle();
        PicHandle.compressImage("C:\\Users\\陈嘉\\Desktop\\a.jpg","C:\\Users\\陈嘉\\Desktop\\c.jpg",1000,0.8);
        SpringApplication.run(ToolmanApplication.class, args);

    }
};



