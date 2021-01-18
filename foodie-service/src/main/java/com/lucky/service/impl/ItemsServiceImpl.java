/**
 * @filename:ItemsServiceImpl 2019年10月16日
 * @project   
 * Copyright(c) 2018 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.service.impl;

import com.lucky.pojo.Items;
import com.lucky.mapper.ItemsDao;
import com.lucky.service.ItemsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * @Description:TODO(商品表服务实现)
 *
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Service
public class ItemsServiceImpl  extends ServiceImpl<ItemsDao, Items> implements ItemsService  {
	
}