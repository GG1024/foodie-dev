package com.lucky.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lank
 * @date 2020/1/16 17:07
 * @desc 首页推荐商品vo
 */
@Data
public class NewItemsVo {

    private Integer rootCatId;
    private String rootCatName;
    private String bgColor;
    private String slogan;
    private String catImage;
    private List<SimpleItemVo> simpleItemList;


}
