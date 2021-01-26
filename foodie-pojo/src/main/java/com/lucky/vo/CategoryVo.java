package com.lucky.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lank
 * @date 2020/1/16 14:36
 * @desc 二级分类vo (view)
 */
@Data
public class CategoryVo {

    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;

    private List<SubCategoryVo> subCatList;


}
