package com.lucky.controller;

import com.lucky.pojo.Users;
import com.lucky.utils.RedisUtil;
import com.lucky.vo.UsersVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @description:
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 10:45
 **/
public class BaseController {

    @Autowired
    private RedisUtil redisUtil;

    //微信支付成功--->支付中心--->天天吃货 回调通知url
    String returnUrl = "http://47.106.207.223:8088/orders/notifyMerchantOrderPaid";

    // 支付中心的调用地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";        // produce

    //用户购物车
    public static final String FOODIE_SHOPCART = "shopcart";

    public static final String REDIS_USER_TOKEN = "redis_user_token";


    /**
     * 将用户token放入userVo中
     *
     * @param users
     * @return
     */
    public UsersVo userToken(Users users) {
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(users, usersVo);
        //生成用户token，存入redis会话
        String userToken = UUID.randomUUID().toString().trim();
        usersVo.setUserUniqueToken(userToken);
        redisUtil.set(REDIS_USER_TOKEN + ":" + usersVo.getId(), userToken);
        return usersVo;
    }
}
