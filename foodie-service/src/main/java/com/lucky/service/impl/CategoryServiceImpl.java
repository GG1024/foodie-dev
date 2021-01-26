/**
 * @filename:CategoryServiceImpl 2019年10月16日
 * @project Copyright(c) 2018 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lucky.mapper.CategoryMapperCustom;
import com.lucky.pojo.Category;
import com.lucky.mapper.CategoryMapper;
import com.lucky.service.CategoryService;
import com.lucky.vo.CategoryVo;
import com.lucky.vo.NewItemsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description:TODO(商品分类服务实现)
 * @version:
 * @author: 欧阳小广
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Override
    public List<Category> queryAllRootCat() {
        QueryWrapper<Category> where = new QueryWrapper<>();
        where.lambda().eq(Category::getType, "1");
        return baseMapper.selectList(where);
    }

    @Override
    public List<CategoryVo> querySubCatList(Integer rootCatId) {
        return categoryMapperCustom.getSubCatList(rootCatId);
    }

    @Override
    public List<NewItemsVo> getSixNewItemsList(Integer rootCatId) {
        return categoryMapperCustom.getSixNewItemList(rootCatId);
    }
}