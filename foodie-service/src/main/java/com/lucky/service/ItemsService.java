/**
 * @filename:ItemsService 2019年10月16日
 * @project   
 * Copyright(c) 2020 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.service;

import com.lucky.pojo.Items;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lucky.pojo.ItemsImg;
import com.lucky.pojo.ItemsParam;
import com.lucky.pojo.ItemsSpec;
import com.lucky.vo.ShopcartVo;

import java.util.List;

/**   
 * @Description:TODO(商品表服务层)
 * @version: 
 * @author: 欧阳小广
 * 
 */
public interface ItemsService extends IService<Items> {

    Items queryItemById(String itemId);

    List<ItemsImg> queryItemsImgById(String itemId);

    ItemsParam queryItemsParam(String itemId);

    List<ItemsSpec> queryItemsSpec(String itemId);

    String queryItemsCommentCount(String itemId);

    String queryItemsComment(String itemId, Integer level, Integer page, Integer pageSize);

    String searchItems(String keywords, String sort, Integer page, Integer pageSize);

    String searchItemsByThirdCat(String catId, String sort, Integer page, Integer pageSize);

    List<ShopcartVo> refreshShopCartWithSpeIds(String itemSpecIds);
}