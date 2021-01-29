package com.lucky.bo;

import lombok.Data;

/**
 * @author lank
 * @date 2020/1/19 16:37
 * @desc 提交订单bo
 */
@Data
public class SubmitOrderBo {
    //用户ID
    private String userId;
    //商品规格ID
    private String itemSpecIds;
    //收货地址ID
    private String addressId;
    //支付方式
    private Integer payMethod;

    private String leftMsg;


    @Override
    public String toString() {
        return "SubmitOrderBo{" +
                "userId='" + userId + '\'' +
                ", itemSpecIds='" + itemSpecIds + '\'' +
                ", addressId='" + addressId + '\'' +
                ", payMethod=" + payMethod +
                ", leftMsg='" + leftMsg + '\'' +
                '}';
    }
}
