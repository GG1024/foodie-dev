package com.lucky.vo;

import lombok.Data;

/**
 * @author lank
 * @date 2020/1/16 14:39
 * @desc 三级分类vo
 */
@Data
public class SubCategoryVo {

    private Integer subId;
    private String subName;
    private String subType;
    private Integer subFatherId;


}
