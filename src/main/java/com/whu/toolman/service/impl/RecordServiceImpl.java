package com.whu.toolman.service.impl;

import com.whu.toolman.dao.RecordMapper;
import com.whu.toolman.entity.Record;
import com.whu.toolman.service.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author ：qx.w
 * @description：
 * @modified By：
 * @since ：Created in 2020/4/30 11:21
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Resource
    RecordMapper recordMapper;


    @Override
    public List<Record> getByUser(String userId) {
        return recordMapper.selectByUser(userId);
    }

    @Override
    public int insertRecord(String userId, String descreption) {
        Record record = new Record();
        String uuid = UUID.randomUUID().toString();
        record.setUuid(uuid);
        record.setUserId(userId);
        record.setDescreption(descreption);
        return recordMapper.insertRecord(record);
    }

    @Override
    public int deleteRecord(String uuid) {
        return recordMapper.deleteRecord(uuid);
    }

    @Override
    public int deleteAllRecord(String userId) {
        return recordMapper.deleteAllRecord(userId);
    }
}
