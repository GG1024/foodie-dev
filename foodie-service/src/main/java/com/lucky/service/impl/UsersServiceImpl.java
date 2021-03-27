/**
 * @filename:UsersServiceImpl 2019年10月16日
 * @project Copyright(c) 2018 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucky.bo.UserBO;
import com.lucky.enums.Sex;
import com.lucky.mapper.UsersMapper;
import com.lucky.pojo.Users;
import com.lucky.service.UsersService;
import com.lucky.utils.DateUtil;
import com.lucky.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description:TODO(用户表服务实现)
 * @version:
 * @author: 欧阳小广
 */
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Autowired
    private Sid sid;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String username) {
        QueryWrapper<Users> where = new QueryWrapper<>();
        where.lambda().eq(Users::getUsername, username);
        Users users = baseMapper.selectOne(where);
        return users == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users register(UserBO userBO) {
        Users users = new Users();
        users.setId(sid.nextShort());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        users.setUsername(userBO.getUsername());
        users.setNickname(userBO.getUsername());
        users.setFace(USER_FACE);
        users.setBirthday(DateUtil.stringToDate("1900-01-01"));
        users.setSex(Sex.secret.code);
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());

        baseMapper.insert(users);

        return users;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users login(String username, String md5Str) {
        QueryWrapper<Users> where = new QueryWrapper<>();
        where.lambda().eq(Users::getUsername, username);
        where.lambda().eq(Users::getPassword, md5Str);
        return baseMapper.selectOne(where);
    }
}