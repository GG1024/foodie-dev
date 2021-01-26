package com.lucky.bo;

import lombok.Data;

/**
 * @author lank
 * @date 2020/1/19 16:37
 * @desc 提交订单bo
 */
@Data
public class SubmitOrderBo {

    private String userId;
    private String itemSpecIds;
    private String addressId;
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
