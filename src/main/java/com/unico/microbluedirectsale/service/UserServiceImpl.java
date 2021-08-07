package com.unico.microbluedirectsale.service;

import com.unico.microbluedirectsale.mapper.UserMapper;
import com.unico.microbluedirectsale.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangxuan
 * @version 1.0
 * @date 2021/8/7 10:20
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public List<User> queryUserInfo() {
        return mapper.queryUserInfo();
    }

    @Override
    public List<User> queryUserInfoByUserName(String userName) {

        return mapper.queryUserInfoByUserName(userName);
    }

}
