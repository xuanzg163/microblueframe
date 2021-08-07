package com.unico.microbluedirectsale.controller;

import com.unico.microbluedirectsale.po.User;
import com.unico.microbluedirectsale.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangxuan
 * @version 1.0
 * @date 2021/8/7 9:33
 */
@RestController
@Api(value = "测试接口", tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取所有用户列表", notes = "直接拉取当前数据库里所有的数据")
    @RequestMapping(value = "rest/userinfo", method = RequestMethod.GET)
    public List<User> userinfo() {

        List<User> userList = userService.queryUserInfo();
        return userList;
    }

    @ApiOperation(value = "根据用户名字查询用户", notes = "用户名查询用户")
    @RequestMapping(value = "rest/getUser/{userName}", method = RequestMethod.POST)
    @ResponseBody
    public List<User> queryUserInfoByUserName(@PathVariable String userName) {

        List<User> userList = userService.queryUserInfoByUserName(userName);
        return userList;
    }
}
