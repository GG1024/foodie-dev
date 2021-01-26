/**
 * @filename:UserAddressServiceImpl 2019年10月16日
 * @project Copyright(c) 2018 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lucky.bo.AddressBo;
import com.lucky.enums.YesOrNo;
import com.lucky.pojo.UserAddress;
import com.lucky.mapper.UserAddressMapper;
import com.lucky.service.UserAddressService;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description:TODO(用户地址表服务实现)
 * @version:
 * @author: 欧阳小广
 */
@Service
@Slf4j
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserAddress> queryAll(String userId) {
        QueryWrapper<UserAddress> where = new QueryWrapper<>();
        where.lambda().eq(UserAddress::getUserId, userId);
        return baseMapper.selectList(where);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addAddress(AddressBo addressBo) {
        //判断用户是否有地址信息，没则设为默认
        List<UserAddress> userAddressList = queryAll(addressBo.getUserId());
        int isDefault = 0;
        if (userAddressList == null || userAddressList.size() == 0) {
            isDefault = 1;
        }
        UserAddress userAddress = new UserAddress();
        String addressId = sid.nextShort();
        BeanUtils.copyProperties(addressBo, userAddress);
        userAddress.setId(addressId);
        userAddress.setCreateTime(new Date());
        userAddress.setUpdateTime(new Date());
        userAddress.setIsDefault(String.valueOf(isDefault));
        baseMapper.insert(userAddress);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateAddress(AddressBo addressBo) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBo, userAddress);
        userAddress.setId(addressBo.getAddressId());
        userAddress.setUpdateTime(new Date());
        baseMapper.updateById(userAddress);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserAddressToBeDefault(String userId, String addressId) {

        //1、查找默认地址设为不默认
        QueryWrapper<UserAddress> where = new QueryWrapper<>();
        where.lambda().eq(UserAddress::getUserId, userId);
        where.lambda().eq(UserAddress::getIsDefault, YesOrNo.YES.code);
        List<UserAddress> defaultAddressList = baseMapper.selectList(where);
        defaultAddressList.stream().forEach(defaultAddress -> {
            defaultAddress.setIsDefault(YesOrNo.NO.code.toString());
            baseMapper.updateById(defaultAddress);
        });

        //2、当前地址设为默认
        UserAddress defaultUserAddress = new UserAddress();
        defaultUserAddress.setId(addressId);
        defaultUserAddress.setUserId(userId);
        defaultUserAddress.setIsDefault(YesOrNo.YES.code.toString());
        baseMapper.updateById(defaultUserAddress);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAddress(String userId, String addressId) {
        QueryWrapper<UserAddress> where = new QueryWrapper<>();
        where.lambda().eq(UserAddress::getId, addressId);
        where.lambda().eq(UserAddress::getUserId, userId);
        baseMapper.delete(where);
    }
}