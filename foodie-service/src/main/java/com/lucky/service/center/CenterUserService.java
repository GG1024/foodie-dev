package com.lucky.service.center;

import com.lucky.bo.CenterUserBo;
import com.lucky.pojo.Users;

/**
 * @FileName: CenterUserService.java
 * @description:
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 13:36
 **/
public interface CenterUserService {


    String queryUserInfoById(String userId);

    Users updateUserInfo(String userId, CenterUserBo centerUserBo);

    Users updateUserFace(String userId, String serverImageUrl);
}
