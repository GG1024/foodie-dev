/**
 * @filename:ItemsService 2019年10月16日
 * @project Copyright(c) 2020 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lucky.pojo.Items;
import com.lucky.pojo.ItemsImg;
import com.lucky.pojo.ItemsParam;
import com.lucky.pojo.ItemsSpec;
import com.lucky.vo.CountsVo;
import com.lucky.vo.ItemsCommentsVo;
import com.lucky.vo.SearchItemsVo;
import com.lucky.vo.ShopcartVo;

import java.util.List;

/**
 * @Description:TODO(商品表服务层)
 * @version:
 * @author: 欧阳小广
 */
public interface ItemsService extends IService<Items> {

    Items queryItemById(String itemId);

    List<ItemsImg> queryItemsImgById(String itemId);

    ItemsParam queryItemsParam(String itemId);

    List<ItemsSpec> queryItemsSpec(String itemId);

    CountsVo queryItemsCommentCount(String itemId);

    List<ItemsCommentsVo> queryItemsComment(String itemId, Integer level);

    List<SearchItemsVo> searchItems(String keywords, String sort);

    List<SearchItemsVo> searchItemsByThirdCat(String catId, String sort);

    List<ShopcartVo> refreshShopCartWithSpeIds(String itemSpecIds);

    ItemsSpec queryItemSpecById(String specId);


    ItemsImg queryItemMainImg(String itemId);


    void decreaseItemSpecStock(String itemSpecId, int buyCount);

}