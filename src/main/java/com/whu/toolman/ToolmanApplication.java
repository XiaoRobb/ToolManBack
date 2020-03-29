package com.whu.toolman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import net.coobird.thumbnailator.*;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

@SpringBootApplication
public class ToolmanApplication {

    public static void main(String[] args) {
        PicHandle p = new PicHandle();
        p.compressImage("C:\\Users\\陈嘉\\Desktop\\a.jpg","C:\\Users\\陈嘉\\Desktop\\c.jpg",1000,0.8);
        SpringApplication.run(ToolmanApplication.class, args);

    }
};



