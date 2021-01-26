/**
 * @filename:CategoryService 2019年10月16日
 * @project Copyright(c) 2020 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.service;

import com.lucky.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lucky.vo.CategoryVo;
import com.lucky.vo.NewItemsVo;

import java.util.List;

/**
 * @Description:TODO(商品分类服务层)
 * @version:
 * @author: 欧阳小广
 */
public interface CategoryService extends IService<Category> {

    List<Category> queryAllRootCat();

    List<CategoryVo> querySubCatList(Integer rootCatId);

    List<NewItemsVo> getSixNewItemsList(Integer rootCatId);
}