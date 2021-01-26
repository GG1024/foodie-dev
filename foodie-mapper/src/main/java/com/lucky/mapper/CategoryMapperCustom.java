package com.lucky.mapper;

import com.lucky.vo.CategoryVo;
import com.lucky.vo.NewItemsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @FileName: CategoryMapperCustom.java
 * @description:
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 14:55
 **/
@Mapper
public interface CategoryMapperCustom {


    public List<CategoryVo> getSubCatList(Integer rootCatId);

    public List<NewItemsVo> getSixNewItemList(Integer rootCatId);

}
