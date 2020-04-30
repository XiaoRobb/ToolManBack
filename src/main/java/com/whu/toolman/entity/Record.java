package com.whu.toolman.entity;

import java.time.LocalDateTime;

/**
 * @author ：qx.w
 * @description：操作的历史记录
 * @modified By：
 * @since ：Created in 2020/4/30 11:07
 */
public class Record {
    /**
     * 主键
     */
    private String uuid;

    /**
     * 绑定的用户
     */
    private String userId;

    /**
     * 创建时间
     */
    private LocalDateTime creatTime;

    /**
     * 操作描述
     */
    private String descreption;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(LocalDateTime creatTime) {
        this.creatTime = creatTime;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }
}

