package com.whu.toolman.service;

import com.whu.toolman.entity.User;

import java.util.List;

/**
 * @author ：qx.w
 * @description：
 * @modified By：
 * @since ：Created in 2020/4/23 17:57
 */
public interface UserService {
    User getUser(String username);


    int createUser(String username, String password, String name, String email);
}

