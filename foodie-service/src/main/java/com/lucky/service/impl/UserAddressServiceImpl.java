/**
 * @filename:UserAddressServiceImpl 2019年10月16日
 * @project   
 * Copyright(c) 2018 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.service.impl;

import com.lucky.pojo.UserAddress;
import com.lucky.mapper.UserAddressDao;
import com.lucky.service.UserAddressService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * @Description:TODO(用户地址表服务实现)
 *
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Service
public class UserAddressServiceImpl  extends ServiceImpl<UserAddressDao, UserAddress> implements UserAddressService  {
	
}