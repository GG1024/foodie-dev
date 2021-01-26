/**
 * @filename:CarouselServiceImpl 2019年10月16日
 * @project Copyright(c) 2018 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lucky.pojo.Carousel;
import com.lucky.mapper.CarouselMapper;
import com.lucky.service.CarouselService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description:TODO(轮播图服务实现)
 * @version:
 * @author: 欧阳小广
 */
@Service
@Slf4j
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Override
    public List<Carousel> queryAll(Integer code) {
        QueryWrapper<Carousel> where = new QueryWrapper<>();
        where.lambda().eq(Carousel::getIsShow, code);
        where.lambda().orderByDesc(Carousel::getSort);
        return baseMapper.selectList(where);
    }
}