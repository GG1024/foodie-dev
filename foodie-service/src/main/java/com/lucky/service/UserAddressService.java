/**
 * @filename:UserAddressService 2019年10月16日
 * @project   
 * Copyright(c) 2020 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.service;

import com.lucky.bo.AddressBo;
import com.lucky.pojo.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description:TODO(用户地址表服务层)
 * @version: 
 * @author: 欧阳小广
 * 
 */
public interface UserAddressService extends IService<UserAddress> {

    List<UserAddress> queryAll(String userId);

    void addAddress(AddressBo addressBo);

    void updateAddress(AddressBo addressBo);

    void updateUserAddressToBeDefault(String userId, String addressId);

    void deleteAddress(String userId, String addressId);

    UserAddress queryUserAddress(String addressId,String userId);

}