/**
 * @filename:UsersServiceImpl 2019年10月16日
 * @project   
 * Copyright(c) 2018 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.service.impl;

import com.lucky.bo.UserBO;
import com.lucky.pojo.Users;
import com.lucky.mapper.UsersMapper;
import com.lucky.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * @Description:TODO(用户表服务实现)
 *
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Service
@Slf4j
public class UsersServiceImpl  extends ServiceImpl<UsersMapper, Users> implements UsersService  {
    @Override
    public boolean queryUsernameIsExist(String username) {
        return false;
    }

    @Override
    public Users register(UserBO userBO) {
        return null;
    }

    @Override
    public Users login(String username, String md5Str) {
        return null;
    }
}