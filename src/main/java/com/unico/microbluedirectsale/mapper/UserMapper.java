package com.unico.microbluedirectsale.mapper;

import com.unico.microbluedirectsale.po.User;

import java.util.List;

/**
 * @author zhangxuan
 * @version 1.0
 * @date 2021/8/7 10:14
 */
public interface UserMapper {
    /**
     * 根据用户名查询用户信息
     *
     * @param userName 用户名
     * @return
     */
    List<User> queryUserInfoByUserName(String userName);

    /**
     * 查询所有用户信息
     *
     * @return
     */
    List<User> queryUserInfo();
}
