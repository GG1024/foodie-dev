package com.lucky.controller;

import com.lucky.core.JsonResult;
import com.lucky.enums.YesOrNo;
import com.lucky.pojo.Carousel;
import com.lucky.pojo.Category;
import com.lucky.service.CarouselService;
import com.lucky.service.CategoryService;
import com.lucky.utils.JsonUtils;
import com.lucky.utils.RedisUtil;
import com.lucky.vo.CategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 用于首页的相关接口
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 10:16
 **/
@Api(value = "首页", tags = "用于首页的相关接口")
@Slf4j
@RestController
@RequestMapping(value = "/index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisUtil redisUtil;

    private JsonResult jsonResult = new JsonResult();

    @ApiOperation(value = "首页轮播图", notes = "首页轮播图", httpMethod = "GET")
    @GetMapping("/carousel")
    public JsonResult<Carousel> queryCarousel() {
        //提交轮播图信息到redis缓存
        String carouselStr = redisUtil.get("carousel");
        List<Carousel> list = null;
        if (StringUtils.isBlank(carouselStr)) {
            list = carouselService.queryAll(YesOrNo.YES.code);
            redisUtil.set("carousel", JsonUtils.objectToJson(list));
        } else {
            list = JsonUtils.jsonToList(carouselStr, Carousel.class);
        }
        return jsonResult.success(list);
    }

    @ApiOperation(value = "获取商品分类（一级分类）", notes = "获取商品分类（一级分类）", httpMethod = "GET")
    @GetMapping("/cats")
    public JsonResult<Category> queryCats() {
        String cats = redisUtil.get("cats");
        List<Category> categories = null;
        if (StringUtils.isBlank(cats)) {
            categories = categoryService.queryAllRootCat();
            redisUtil.set("cats", JsonUtils.objectToJson(categories));
        } else {
            categories = JsonUtils.jsonToList(cats, Category.class);
        }
        return jsonResult.success(categories);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JsonResult<CategoryVo> querySubCats(
            @ApiParam(value = "一级分类id", name = "rootCatId")
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return jsonResult.error("一级分类id不能为空");
        }

        List<CategoryVo> categoryVos = null;
        String catsStr = redisUtil.get("subCat:" + rootCatId);
        if (StringUtils.isBlank(catsStr)) {
            categoryVos = categoryService.querySubCatList(rootCatId);
            redisUtil.set("subCat:" + rootCatId, JsonUtils.objectToJson(categoryVos));
        } else {
            categoryVos = JsonUtils.jsonToList(catsStr, CategoryVo.class);
        }
        return jsonResult.success(categoryVos);
    }

    @ApiOperation(value = "获取首页分类推荐商品", notes = "获取首页分类推荐商品", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JsonResult sixNewItems(
            @ApiParam(value = "一级分类id", name = "rootCatId")
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return jsonResult.error("一级分类id不能为空");
        }

        return jsonResult.success(categoryService.getSixNewItemsList(rootCatId));
    }
}
