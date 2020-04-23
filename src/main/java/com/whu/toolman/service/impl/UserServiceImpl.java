package com.whu.toolman.service.impl;

import com.whu.toolman.dao.UserMapper;
import com.whu.toolman.entity.User;
import com.whu.toolman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：qx.w
 * @description：
 * @modified By：
 * @since ：Created in 2020/4/23 17:58
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    /**
     * 没找到用户返回空
     * @param username
     * @return
     */
    @Override
    public User getUser(String username) {
        return userMapper.selectByUsername(username) != null ? userMapper.selectByUsername(username).get(0) : null;
    }
}
