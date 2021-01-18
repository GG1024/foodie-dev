/**
 * @filename:ItemsController 2019年10月16日
 * @project Copyright(c) 2020 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.controller;

import com.lucky.core.AbstractController;
import com.lucky.pojo.Items;
import com.lucky.service.ItemsService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： 商品表API接口层</P>
 * @version:
 * @author: 欧阳小广
 * @time 2019年10月16日
 *
 */
@Api(description = "商品表", value = "商品表")
@RestController
@RequestMapping("/items")
public class ItemsController extends AbstractController<ItemsService, Items> {

}