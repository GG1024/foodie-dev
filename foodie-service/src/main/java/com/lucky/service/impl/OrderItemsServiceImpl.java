/**
 * @filename:OrderItemsServiceImpl 2019年10月16日
 * @project   
 * Copyright(c) 2018 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.service.impl;

import com.lucky.pojo.OrderItems;
import com.lucky.mapper.OrderItemsDao;
import com.lucky.service.OrderItemsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * @Description:TODO(订单商品关联表服务实现)
 *
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Service
public class OrderItemsServiceImpl  extends ServiceImpl<OrderItemsDao, OrderItems> implements OrderItemsService  {
	
}