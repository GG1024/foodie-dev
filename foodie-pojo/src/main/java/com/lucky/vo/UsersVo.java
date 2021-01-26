package com.lucky.vo;


import lombok.Data;

/**
 * @author lank
 * @date 2020/2/26 15:57
 * @desc
 */
@Data
public class UsersVo {

    /**
     * 主键id 用户id
     */
    private String id;

    /**
     * 用户名 用户名
     */
    private String username;

    /**
     * 密码 密码
     */
    private String password;

    /**
     * 昵称 昵称
     */
    private String nickname;

    /**
     * 头像 头像
     */
    private String face;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;

    /**
     * 用户会话token
     */
    private String userUniqueToken;


}
