/**
 * @filename:OrdersController 2019年10月16日
 * @project Copyright(c) 2020 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.controller;

import com.lucky.bo.ShopcartBo;
import com.lucky.bo.SubmitOrderBo;
import com.lucky.core.JsonResult;
import com.lucky.enums.OrderStatusEnum;
import com.lucky.enums.PayMethod;
import com.lucky.service.OrdersService;
import com.lucky.utils.CookieUtils;
import com.lucky.utils.JsonUtils;
import com.lucky.utils.RedisUtil;
import com.lucky.vo.MerchantOrdersVo;
import com.lucky.vo.OrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： 订单表API接口层</P>
 *
 * @version:
 * @author: 欧阳小广
 * @time 2019年10月16日
 */
@Api(description = "订单表", value = "订单表")
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController extends BaseController {

    private static final String FOODIE_SHOP = "shopcart";

    @Autowired
    private OrdersService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation(value = "创建订单", notes = "创建订单", httpMethod = "POST")
    @PostMapping("/create")
    public JsonResult add(
            @RequestBody SubmitOrderBo submitOrderBo, HttpServletRequest request, HttpServletResponse response) {
        log.info("订单信息:{}", submitOrderBo);
        //判断支付方式是否正确
        if (submitOrderBo.getPayMethod() != PayMethod.WEIXIN.type && submitOrderBo.getPayMethod() != PayMethod.ALIPAY.type) {
            return JsonResult.error("支付方式不正确!");
        }
        //从redis中获取购物车信息
        String shopCartStr = redisUtil.get(FOODIE_SHOPCART + ":" + submitOrderBo.getUserId());
        if (StringUtils.isBlank(shopCartStr)) {
            return JsonResult.error("购物车信息有误!");
        }

        List<ShopcartBo> shopcartBoList = JsonUtils.jsonToList(shopCartStr, ShopcartBo.class);
        //1、创建订单
        OrderVo orderVo = orderService.createOrder(submitOrderBo, shopcartBoList);
        String orderId = orderVo.getOrderId();
        MerchantOrdersVo merchantOrdersVo = orderVo.getMerchantOrdersVo();
        merchantOrdersVo.setReturnUrl(returnUrl);
        //2、创建订单后，移除购物车中已结算商品
        // 整合redis后，完善购物车的结算清除，并同步到前端cookie
        shopcartBoList.removeAll(orderVo.getWaitRemoveShopCartBo());
        redisUtil.set(FOODIE_SHOPCART + ":" + submitOrderBo.getUserId(), JsonUtils.objectToJson(shopcartBoList));
        CookieUtils.setCookie(request, response, FOODIE_SHOP, JsonUtils.objectToJson(shopcartBoList));

        //3、向支付中心发送当前订单，用于保存支付中心的订单数据

        /**
         * 构建rest请求
         * http
         * spring rest
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");
        HttpEntity<MerchantOrdersVo> httpEntity = new HttpEntity<>(merchantOrdersVo, headers);
        ResponseEntity<JsonResult> jsonResultResponseEntity = restTemplate.postForEntity(paymentUrl, httpEntity, JsonResult.class);
        JsonResult jsonResult = jsonResultResponseEntity.getBody();
        log.info("调用结果：{}", jsonResult.getMessage());
        System.out.println(jsonResult);
        if (jsonResult.getCode() != 200) {
            return jsonResult.error("支付中心创建订单失败，请联系管理员!");
        }
        return jsonResult.success(orderId);
    }


    @PostMapping("/notifyMerchantOrderPaid")
    @ApiOperation(value = "支付中心支付成功通知方法", notes = "支付中心支付成功通知方法", httpMethod = "POST")
    //支付中心支付成功通知方法
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }
}