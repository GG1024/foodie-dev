/**
 * @filename:UsersServiceImpl 2019年10月16日
 * @project   
 * Copyright(c) 2018 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.service.impl;

import com.lucky.pojo.Users;
import com.lucky.mapper.UsersDao;
import com.lucky.service.UsersService;
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
public class UsersServiceImpl  extends ServiceImpl<UsersDao, Users> implements UsersService  {
	
}