package com.lucky.vo;

import com.lucky.pojo.Items;
import com.lucky.pojo.ItemsImg;
import com.lucky.pojo.ItemsParam;
import com.lucky.pojo.ItemsSpec;
import lombok.Data;

import java.util.List;

/**
 * @author lank
 * @date 2020/1/16 22:39
 * @desc 商品详情vo
 */
@Data
public class ItemInfoVo {

    private Items item;
    private List<ItemsImg> itemImgList;
    private ItemsParam itemParams;
    private List<ItemsSpec> itemSpecList;


}
