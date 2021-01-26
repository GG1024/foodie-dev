package com.lucky.controller;

import com.lucky.bo.ShopcartBo;
import com.lucky.bo.UserBO;
import com.lucky.core.JsonResult;
import com.lucky.pojo.Users;
import com.lucky.service.UsersService;
import com.lucky.utils.CookieUtils;
import com.lucky.utils.JsonUtils;
import com.lucky.utils.MD5Utils;
import com.lucky.utils.RedisUtil;
import com.lucky.vo.UsersVo;
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
 * @description: 用于注册登录的相关接口
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 11:09
 **/
@Api(value = "注册登录", tags = "用于注册登录的相关接口")
@Slf4j
@RestController
@RequestMapping(value = "/passport")
public class PassportController extends BaseController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RedisUtil redisUtil;

    private JsonResult jsonResult = new JsonResult();

    @ApiOperation(value = "检查用户名是否重复", notes = "检查用户名是否重复", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JsonResult checkUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return jsonResult.error("用户名不能为空");
        }
        boolean isExist = usersService.queryUsernameIsExist(username);
        if (isExist) {
            return jsonResult.error("用户名已经存在");
        }
        return jsonResult.success();
    }


    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public JsonResult register(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
        //判断用户名密码不为空
        if (StringUtils.isBlank(userBO.getUsername())
                || StringUtils.isBlank(userBO.getPassword())
                || StringUtils.isBlank(userBO.getConfirmPassword())

        ) {
            return jsonResult.error("用户名或密码不能为空!");
        }
        //判断用户名是否存在
        boolean isExist = usersService.queryUsernameIsExist(userBO.getUsername());
        if (isExist) {
            return jsonResult.error("用户名已经存在");
        }
        //判断两次密码是否一致
        if (!userBO.getPassword().equals(userBO.getConfirmPassword())) {
            return jsonResult.error("两次密码不一致");
        }
        Users users = usersService.register(userBO);
        //生成用户token，存入redis会话
        UsersVo usersVo = userToken(users);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVo), true);
        //同步购物车数据
        synShopCartData(users.getId(), request, response);
        return jsonResult.success(users);
    }


    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JsonResult login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //判断用户名密码不为空
        if (StringUtils.isBlank(userBO.getUsername())
                || StringUtils.isBlank(userBO.getPassword())
        ) {
            return jsonResult.error("用户名或密码不能为空!");
        }

        Users users = usersService.login(userBO.getUsername(), MD5Utils.getMD5Str(userBO.getPassword()));
        if (users == null) {
            return jsonResult.error("用户名或密码不正确!");
        }

        //生成用户token，存入redis会话
        UsersVo usersVo = userToken(users);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVo), true);

        //同步购物车数据
        synShopCartData(users.getId(), request, response);
        return jsonResult.success(users);
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public JsonResult logout(String userId, HttpServletRequest request, HttpServletResponse response) {
        //清除cookie
        CookieUtils.deleteCookie(request, response, "user");

        //用户退出登录，需清除购物车数
        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);

        // 分布式会话中，需清除用户数据
        redisUtil.delete(REDIS_USER_TOKEN + ":" + userId);

        return jsonResult.success();
    }

    /**
     * 同步购物车数据
     **/
    private void synShopCartData(String userId, HttpServletRequest request, HttpServletResponse response) {
        String shopCartStrRedis = redisUtil.get(FOODIE_SHOPCART + ":" + userId);
        String shopCartStrCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);

        log.info("shopCartStrRedis:{}", shopCartStrRedis);
        log.info("shopCartStrCookie:{}", shopCartStrCookie);

        //1、redis中无数据，cookie有数据，将cookie中数据放入redis中

        if (StringUtils.isBlank(shopCartStrRedis)) {
            if (StringUtils.isNotBlank(shopCartStrCookie)) {
                redisUtil.set(FOODIE_SHOPCART + ":" + userId, shopCartStrCookie);
            }
        } else {
            //2、redis中有数据，cookie无数据，将redis中数据放入cookie中
            if (StringUtils.isBlank(shopCartStrCookie)) {
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopCartStrRedis, true);
            } else {
                //3、redis中有数据，cookie有数据，将redis中数据放入cookie中
                // 如果cookie中的某个商品存在于redis中，以cookie中为主
                List<ShopcartBo> redisShopCartList = JsonUtils.jsonToList(shopCartStrRedis, ShopcartBo.class);
                List<ShopcartBo> cookieShopCartList = JsonUtils.jsonToList(shopCartStrCookie, ShopcartBo.class);
                //cookie中等待被删除的list
                List<ShopcartBo> pendingDeleteCookieList = new ArrayList<>();
                for (ShopcartBo redisBo : redisShopCartList) {
                    String redisBoSpecId = redisBo.getSpecId();
                    for (ShopcartBo cookieBo : cookieShopCartList) {
                        String cookieBoSpecId = cookieBo.getSpecId();
                        //取cookie中的商品数量代替redis
                        if (redisBoSpecId.equals(cookieBoSpecId)) {
                            redisBo.setBuyCounts(cookieBo.getBuyCounts());
                        }
                        pendingDeleteCookieList.add(cookieBo);
                    }
                }
                //删除cookieList中重复的商品
                cookieShopCartList.removeAll(pendingDeleteCookieList);
                //合并两个list
                redisShopCartList.addAll(cookieShopCartList);
                //最后，同步到redis后，覆盖本地cookie中的数据
                redisUtil.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(redisShopCartList));
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(redisShopCartList), true);
            }
        }
    }


    private Users setNullProperty(Users users) {
        users.setRealname("");
        users.setPassword("");
        return users;
    }


}
