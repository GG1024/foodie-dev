package com.lucky.vo;

import com.lucky.bo.ShopcartBo;

import java.util.List;

/**
 * @author lank
 * @date 2020/1/30 10:32
 * @desc 商户订单vo
 */
public class OrderVo {
    private String orderId;
    private MerchantOrdersVo merchantOrdersVo;

    //等待被redis删除的bo
    private List<ShopcartBo> waitRemoveShopCartBo;

    public List<ShopcartBo> getWaitRemoveShopCartBo() {
        return waitRemoveShopCartBo;
    }

    public void setWaitRemoveShopCartBo(List<ShopcartBo> waitRemoveShopCartBo) {
        this.waitRemoveShopCartBo = waitRemoveShopCartBo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVo getMerchantOrdersVo() {
        return merchantOrdersVo;
    }

    public void setMerchantOrdersVo(MerchantOrdersVo merchantOrdersVo) {
        this.merchantOrdersVo = merchantOrdersVo;
    }
}
