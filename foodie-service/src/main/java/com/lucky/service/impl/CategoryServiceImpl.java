/**
 * @filename:CategoryServiceImpl 2019年10月16日
 * @project   
 * Copyright(c) 2018 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.service.impl;

import com.lucky.pojo.Category;
import com.lucky.mapper.CategoryDao;
import com.lucky.service.CategoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * @Description:TODO(商品分类服务实现)
 *
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Service
public class CategoryServiceImpl  extends ServiceImpl<CategoryDao, Category> implements CategoryService  {
	
}