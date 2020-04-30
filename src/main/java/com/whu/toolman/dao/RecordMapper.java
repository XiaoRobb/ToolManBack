package com.whu.toolman.dao;

import com.whu.toolman.entity.Record;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：qx.w
 * @description：历史记录
 * @modified By：
 * @since ：Created in 2020/4/30 11:12
 */
public interface RecordMapper {
    /**
     * 通过用户名查找
     * @param userId
     * @return
     */
    List<Record> selectByUser(@Param("userId") String userId);

    /**
     * 新增一本书在购物车中
     * @param record
     * @return
     */
    int insertRecord(Record record);

    /**
     * 删除购物车中的一本书
     * @param uuid
     * @return
     */
    int deleteRecord(@Param("uuid") String uuid);

    /**
     * 清空购物车
     * @param userId
     * @return
     */
    int deleteAllRecord(@Param("userId") String userId);
}
