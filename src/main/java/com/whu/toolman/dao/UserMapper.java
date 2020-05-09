package com.whu.toolman.dao;

import com.whu.toolman.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：qx.w
 * @description：user dao层
 * @modified By：
 * @since ：Created in 2020/4/23 17:55
 */
@Mapper
public interface UserMapper {
    List<User> selectByUsername(@Param("username") String username);


    int insertUser(User user);

    int updateUser(User user);

}
