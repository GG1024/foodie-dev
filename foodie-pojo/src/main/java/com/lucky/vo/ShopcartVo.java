package com.lucky.vo;

import lombok.Data;

/**
 * @author lank
 * @date 2020/1/19 9:24
 * @desc 购物车类对象VO
 */
@Data
public class ShopcartVo {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;
}
