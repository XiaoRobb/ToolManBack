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
    private LocalDateTime createTime;

    /**
     * 操作类型（文档图片音频）
     */
    private String type;

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

    public LocalDateTime getcreateTime() {
        return createTime;
    }

    public void setcreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

