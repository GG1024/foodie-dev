/**
 * @filename:ItemsController 2019年10月16日
 * @project Copyright(c) 2020 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.controller;

import com.lucky.core.JsonResult;
import com.lucky.pojo.Items;
import com.lucky.pojo.ItemsImg;
import com.lucky.pojo.ItemsParam;
import com.lucky.pojo.ItemsSpec;
import com.lucky.service.ItemsService;
import com.lucky.vo.ItemInfoVo;
import com.lucky.vo.ShopcartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： 商品表API接口层</P>
 *
 * @version:
 * @author: 欧阳小广
 * @time 2019年10月16日
 */
@Api(description = "商品", value = "用于商品的相关接口")
@RestController
@RequestMapping("/items")
public class ItemsController {
    @Autowired
    private ItemsService itemsService;

    private JsonResult jsonResult = new JsonResult();


    @ApiOperation(value = "商品详情页", notes = "商品详情页", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JsonResult<ItemInfoVo> queryCarousel(@PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return jsonResult.error("商品id不能为空!");
        }
        Items items = itemsService.queryItemById(itemId);
        List<ItemsImg> itemsImg = itemsService.queryItemsImgById(itemId);
        ItemsParam itemsParam = itemsService.queryItemsParam(itemId);
        List<ItemsSpec> itemsSpec = itemsService.queryItemsSpec(itemId);
        ItemInfoVo itemInfoVo = new ItemInfoVo();
        itemInfoVo.setItem(items);
        itemInfoVo.setItemImgList(itemsImg);
        itemInfoVo.setItemParams(itemsParam);
        itemInfoVo.setItemSpecList(itemsSpec);
        return jsonResult.success(itemInfoVo);
    }


    @ApiOperation(value = "商品评价数", notes = "商品评价数", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JsonResult commentLevel(@RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return jsonResult.error("商品id不能为空!");
        }
        return jsonResult.success(itemsService.queryItemsCommentCount(itemId));
    }

    @ApiOperation(value = "商品评价列表", notes = "商品评价列表", httpMethod = "GET")
    @GetMapping("/comments")
    public JsonResult commentList(@RequestParam String itemId,
                                  @RequestParam(value = "level", required = false) Integer level,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return jsonResult.error("商品id不能为空!");
        }
        return jsonResult.success(itemsService.queryItemsComment(itemId, level, page, pageSize));
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public JsonResult searchItems(
            @ApiParam(name = "sort", value = "排序规则", required = true)
            @RequestParam(value = "sort") String sort,
            @ApiParam(name = "keywords", value = "排序规则")
            @RequestParam(value = "keywords") String keywords,
            @ApiParam(name = "page", value = "页码")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "每一页显示数")
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return jsonResult.success(itemsService.searchItems(keywords, sort, page, pageSize));
    }

    @ApiOperation(value = "商品三级分类id搜索商品列表", notes = "商品三级分类id搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public JsonResult searchItemsByThirdCat(
            @ApiParam(name = "sort", value = "排序规则", required = true)
            @RequestParam(value = "sort") String sort,
            @ApiParam(name = "catId", value = "三级商品id")
            @RequestParam(value = "catId") String catId,
            @ApiParam(name = "page", value = "页码")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "每一页显示数")
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return jsonResult.success(itemsService.searchItemsByThirdCat(catId, sort, page, pageSize));
    }


    // 用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格），类似京东淘宝
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public JsonResult<ShopcartVo> refresh(
            @ApiParam(name = "itemSpecIds", value = "拼接的规格ids", required = true, example = "1001,1003,1005")
            @RequestParam String itemSpecIds) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return jsonResult.success();
        }
        List<ShopcartVo> list = itemsService.refreshShopCartWithSpeIds(itemSpecIds);
        return jsonResult.success(list);
    }
}