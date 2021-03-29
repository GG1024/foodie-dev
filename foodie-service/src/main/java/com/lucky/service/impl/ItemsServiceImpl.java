/**
 * @filename:ItemsServiceImpl 2019年10月16日
 * @project Copyright(c) 2018 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lucky.enums.Level;
import com.lucky.enums.YesOrNo;
import com.lucky.mapper.*;
import com.lucky.pojo.*;
import com.lucky.service.ItemsService;
import com.lucky.utils.DesensitizationUtil;
import com.lucky.vo.CountsVo;
import com.lucky.vo.ItemsCommentsVo;
import com.lucky.vo.SearchItemsVo;
import com.lucky.vo.ShopcartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:TODO(商品表服务实现)
 * @version:
 * @author: 欧阳小广
 */
@Service
@Slf4j
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items> implements ItemsService {

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        return baseMapper.selectById(itemId);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemsImgById(String itemId) {
        QueryWrapper<ItemsImg> where = new QueryWrapper<>();
        where.lambda().eq(ItemsImg::getItemId, itemId);
        return itemsImgMapper.selectList(where);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemsParam(String itemId) {
        QueryWrapper<ItemsParam> where = new QueryWrapper<>();
        where.lambda().eq(ItemsParam::getItemId, itemId);
        return itemsParamMapper.selectOne(where);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemsSpec(String itemId) {
        QueryWrapper<ItemsSpec> where = new QueryWrapper<>();
        where.lambda().eq(ItemsSpec::getItemId, itemId);
        return itemsSpecMapper.selectList(where);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CountsVo queryItemsCommentCount(String itemId) {

        Integer goodCounts = getCommentsCount(itemId, Level.GOOD.code);
        Integer normalCounts = getCommentsCount(itemId, Level.NORMAL.code);
        Integer badCounts = getCommentsCount(itemId, Level.BAD.code);
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        CountsVo countsVo = new CountsVo();
        countsVo.setGoodCounts(goodCounts);
        countsVo.setNormalCounts(normalCounts);
        countsVo.setBadCounts(badCounts);
        countsVo.setTotalCounts(totalCounts);
        return countsVo;
    }


    /**
     * 获取商品对应等级评价数
     **/
    private Integer getCommentsCount(String itemId, Integer level) {
        QueryWrapper<ItemsComments> where = new QueryWrapper<>();
        where.lambda().eq(ItemsComments::getItemId, itemId);
        where.lambda().eq(ItemsComments::getCommentLevel, level);
        return itemsCommentsMapper.selectCount(where);

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsCommentsVo> queryItemsComment(String itemId, Integer level) {

        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);
        List<ItemsCommentsVo> itemsCommentsVos = itemsMapperCustom.queryItemComments(map);
        //用户昵称脱敏
        itemsCommentsVos.stream().forEach(itemsCommentsVo -> itemsCommentsVo.setNickname(DesensitizationUtil.commonDisplay(itemsCommentsVo.getNickname())));
        return itemsCommentsVos;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsSpec queryItemSpecById(String specId) {
        return itemsSpecMapper.selectById(specId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsImg queryItemMainImg(String itemId) {
        QueryWrapper<ItemsImg> where = new QueryWrapper<>();
        where.lambda().eq(ItemsImg::getItemId, itemId);
        where.lambda().eq(ItemsImg::getIsMain, YesOrNo.YES.code);
        return itemsImgMapper.selectOne(where);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<SearchItemsVo> searchItems(String keywords, String sort) {

        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);

        List<SearchItemsVo> searchItemsVos = itemsMapperCustom.searchItems(map);
        return searchItemsVos;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<SearchItemsVo> searchItemsByThirdCat(String catId, String sort) {
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);

        List<SearchItemsVo> searchItemsVos = itemsMapperCustom.searchItemsByThirdCat(map);
        return searchItemsVos;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ShopcartVo> refreshShopCartWithSpeIds(String itemSpecIds) {
        String[] split = itemSpecIds.split(",");
        return itemsMapperCustom.refreshShopCartWithSpeIds(split);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void decreaseItemSpecStock(String itemSpecId, int buyCount) {
        int count = itemsMapperCustom.decreaseItemSpecStock(itemSpecId, buyCount);
        if (count != 1) {
            throw new RuntimeException("库存扣减失败，库存不足");
        }
    }
}