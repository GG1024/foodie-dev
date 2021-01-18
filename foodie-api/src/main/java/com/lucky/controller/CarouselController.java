/**
 * @filename:CarouselController 2019年10月16日
 * @project   
 * Copyright(c) 2020 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.controller;

import com.lucky.core.AbstractController;
import com.lucky.pojo.Carousel;
import com.lucky.service.CarouselService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 轮播图API接口层</P>
 * @version: 
 * @author: 欧阳小广
 * @time    2019年10月16日
 *
 */
@Api(description = "轮播图",value="轮播图" )
@RestController
@RequestMapping("/carousel")
public class CarouselController extends AbstractController<CarouselService,Carousel> {
	
}