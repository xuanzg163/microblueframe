package com.unico.microbluedirectsale.po;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangxuan
 * @version 1.0
 * @date 2021/8/7 10:02
 */
@Data
public class User {

    private Integer id;

    private String userName;

    private String userPwd;

    private Integer isValid;

    private Date createDate;

    private Date updateDate;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", isValid=" + isValid +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
