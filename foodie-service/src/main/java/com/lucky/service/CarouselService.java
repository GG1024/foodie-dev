/**
 * @filename:CarouselService 2019年10月16日
 * @project   
 * Copyright(c) 2020 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.service;

import com.lucky.pojo.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description:TODO(轮播图服务层)
 * @version: 
 * @author: 欧阳小广
 * 
 */
public interface CarouselService extends IService<Carousel> {

    List<Carousel> queryAll(Integer code);
}