/**
 * @filename:ItemsServiceImpl 2019年10月16日
 * @project Copyright(c) 2018 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lucky.enums.Level;
import com.lucky.mapper.*;
import com.lucky.pojo.*;
import com.lucky.service.ItemsService;
import com.lucky.vo.CountsVo;
import com.lucky.vo.ShopcartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public IPage queryItemsComment(String itemId, Integer level, Integer page, Integer pageSize) {
        QueryWrapper<ItemsComments> where = new QueryWrapper<>();
        where.lambda().eq(ItemsComments::getItemId, itemId);
        where.lambda().eq(ItemsComments::getCommentLevel, level);
        IPage<ItemsComments> itemsCommentsIPage = itemsCommentsMapper.selectPage(new Page<>(page, pageSize), where);
        //用户昵称脱敏
//        itemsCommentsIPage.getRecords().stream().forEach(itemsComments -> itemsComments.set);
        return itemsCommentsIPage;
    }

    @Override
    public IPage searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public IPage searchItemsByThirdCat(String catId, String sort, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public List<ShopcartVo> refreshShopCartWithSpeIds(String itemSpecIds) {
        return null;
    }
}