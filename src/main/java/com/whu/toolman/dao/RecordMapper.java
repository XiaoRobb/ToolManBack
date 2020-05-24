package com.whu.toolman.dao;

import com.whu.toolman.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：qx.w
 * @description：历史记录
 * @modified By：
 * @since ：Created in 2020/4/30 11:12
 */
@Mapper
public interface RecordMapper {
    /**
     * 通过用户名查找
     * @param userId
     * @return
     */
    List<Record> selectByUser(@Param("userId") String userId);

    /**
     * 新增一个记录
     * @param record
     * @return
     */
    int insertRecord(Record record);

    /**
     * 删除一条记录
     * @param uuid
     * @return
     */
    int deleteRecord(@Param("uuid") String uuid);

    /**
     * 清空清空记录
     * @param userId
     * @return
     */
    int deleteAllRecord(@Param("userId") String userId);
}
