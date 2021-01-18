/**
 * @filename:CategoryController 2019年10月16日
 * @project   
 * Copyright(c) 2020 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.controller;

import com.lucky.core.AbstractController;
import com.lucky.pojo.Category;
import com.lucky.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**   
 *
 * <p>说明： 商品分类API接口层</P>
 * @version: 
 * @author: 欧阳小广
 * @time    2019年10月16日
 *
 */
@Api(description = "商品分类",value="商品分类" )
@RestController
@RequestMapping("/category")
public class CategoryController extends AbstractController<CategoryService,Category> {
	
}