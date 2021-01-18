/**
 * @filename:OrdersServiceImpl 2019年10月16日
 * @project   
 * Copyright(c) 2018 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.service.impl;

import com.lucky.pojo.Orders;
import com.lucky.mapper.OrdersDao;
import com.lucky.service.OrdersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * @Description:TODO(订单表服务实现)
 *
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Service
public class OrdersServiceImpl  extends ServiceImpl<OrdersDao, Orders> implements OrdersService  {
	
}