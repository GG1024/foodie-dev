/**
 * @filename:OrderStatusServiceImpl 2019年10月16日
 * @project   
 * Copyright(c) 2018 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.service.impl;

import com.lucky.pojo.OrderStatus;
import com.lucky.mapper.OrderStatusDao;
import com.lucky.service.OrderStatusService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * @Description:TODO(订单状态表服务实现)
 *
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Service
public class OrderStatusServiceImpl  extends ServiceImpl<OrderStatusDao, OrderStatus> implements OrderStatusService  {
	
}