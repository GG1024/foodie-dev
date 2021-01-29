package com.lucky.mapper;

import com.lucky.vo.ItemsCommentsVo;
import com.lucky.vo.SearchItemsVo;
import com.lucky.vo.ShopcartVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @FileName: ItemsMapperCustom.java
 * @description:
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 15:04
 **/
@Mapper
public interface ItemsMapperCustom {

    List<ItemsCommentsVo> queryItemComments(@Param("map") Map<String, Object> map);

    List<SearchItemsVo> searchItems(@Param("map") Map<String, Object> map);

    List<SearchItemsVo> searchItemsByThirdCat(@Param("map") Map<String, Object> map);

    List<ShopcartVo> refreshShopCartWithSpeIds(@Param("itemSpecsList") String[] split);

   int decreaseItemSpecStock(@Param("specId") String itemSpecId, @Param("buyCount") int buyCount);
}
