package com.whu.toolman.service;

import com.whu.toolman.entity.Record;

import java.util.List;

/**
 * @author ：qx.w
 * @description：
 * @modified By：
 * @since ：Created in 2020/4/30 11:19
 */
public interface RecordService {
    /**
     * 通过用户名得到记录
     * @param userId
     * @return
     */
    List<Record> getByUser(String userId);

    /**
     * 新增记录
     * @param userId
     * @param descreption
     * @return
     */
    int insertRecord(String userId, String type, String descreption);

    /**
     * 从删除一条记录
     * @param uuid
     * @return
     */
    int deleteRecord(String uuid);

    /**
     * 清空购物车
     * @param userId
     * @return
     */
    int deleteAllRecord(String userId);
}
