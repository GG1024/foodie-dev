package com.lucky.controller;

import com.lucky.bo.ShopcartBo;
import com.lucky.core.JsonResult;
import com.lucky.utils.JsonUtils;
import com.lucky.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 购物车商品相关api
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 11:34
 **/
@Api(value = "购物车接口", tags = "购物车商品相关api")
@Slf4j
@RestController
@RequestMapping("/shopcart")
public class ShopCatController extends BaseController {


    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation(value = "添加购物车商品到redis", notes = "添加购物车商品到redis", httpMethod = "POST")
    @PostMapping("/add")
    public JsonResult add(@RequestParam String userId,
                          @RequestBody ShopcartBo shopcartBo,
                          HttpServletRequest request, HttpServletResponse response
    ) {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.error("");
        }
        log.info(shopcartBo.toString());
        //前端用户在登录得情况下，用户添加购物车会同步到redis缓存
        String shopCart = redisUtil.get(FOODIE_SHOPCART + ":" + userId);
        log.info("redis购物车中商品信息：{}", shopCart);
        List<ShopcartBo> shopcartBoList = null;
        if (StringUtils.isNotBlank(shopCart)) {
            shopcartBoList = JsonUtils.jsonToList(shopCart, ShopcartBo.class);
            boolean isHave = false;
            for (ShopcartBo bo : shopcartBoList) {
                //判断购物车中是否有该商品
                if (bo.getSpecId().equals(shopcartBo.getSpecId())) {
                    bo.setBuyCounts(bo.getBuyCounts() + shopcartBo.getBuyCounts());
                    isHave = true;
                }
                if (!isHave) {
                    shopcartBoList.add(shopcartBo);
                }
            }

        } else {
            shopcartBoList = new ArrayList<>();
            shopcartBoList.add(shopcartBo);
        }
        redisUtil.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartBoList));
        return JsonResult.success();
    }


    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public JsonResult del(@RequestParam String userId,
                          @RequestParam String itemSpecId,
                          HttpServletRequest request, HttpServletResponse response
    ) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return JsonResult.error("参数不能为空!");
        }

        log.info("用户删除购物车中商品" + itemSpecId);
        //用户在页面删除购物车中的商品数据，如果用户登录需同步删除redis后端购物车中的商品
        String shopCart = redisUtil.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopcartBo> shopcartBoList = null;
        if (StringUtils.isNotBlank(shopCart)) {
            shopcartBoList = JsonUtils.jsonToList(shopCart, ShopcartBo.class);
            for (ShopcartBo bo : shopcartBoList) {
                if (itemSpecId.equals(bo.getSpecId())) {
                    shopcartBoList.remove(bo);
                    break;
                }
            }
            log.info("shopcartBoList:{}", JsonUtils.objectToJson(shopcartBoList));
            redisUtil.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartBoList));
        }
        return JsonResult.success();
    }

}
