package com.lucky.service.impl.center;

import com.lucky.bo.CenterUserBo;
import com.lucky.mapper.UsersMapper;
import com.lucky.pojo.Users;
import com.lucky.service.center.CenterUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @FileName: CenterUserServiceImpl.java
 * @description:
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-29 14:52
 **/
@Slf4j
@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    private UsersMapper usersMapper;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserInfoById(String userId) {
        Users users = usersMapper.selectById(userId);
        users.setPassword(null);
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users updateUserInfo(String userId, CenterUserBo centerUserBo) {
        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUserBo, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateById(updateUser);
        return queryUserInfoById(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users updateUserFace(String userId, String faceUrl) {
        Users updateUser = new Users();
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        updateUser.setFace(faceUrl);
        usersMapper.updateById(updateUser);
        return queryUserInfoById(userId);
    }
}
