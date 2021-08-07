package com.unico.microbluedirectsale.service;

import com.unico.microbluedirectsale.po.User;

import java.util.List;

/**
 * @author zhangxuan
 * @version 1.0
 * @date 2021/8/7 10:18
 */
public interface UserService {


    /**
     * 查询所有用户信息
     *
     * @return list
     */
    public List<User> queryUserInfo();

    /**
     * 根据用户名字查询用户
     * @param userName 用户名
     * @return List
     */
    public List<User>queryUserInfoByUserName(String userName);
}
