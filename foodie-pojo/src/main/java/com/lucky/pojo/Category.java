/**
 * @filename:Category 2019年10月16日
 * @project   
 * Copyright(c) 2020 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**   
 * @Description:TODO(商品分类实体类)
 * 
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Category extends Model<Category> {

	private static final long serialVersionUID = 1610950330127L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "分类id主键")
	private Long id;
    
	@ApiModelProperty(name = "name" , value = "分类名称")
	private String name;
    
	@ApiModelProperty(name = "type" , value = "分类得类型，1:一级大分类2:二级分类3:三级小分类")
	private String type;
    
	@ApiModelProperty(name = "fatherId" , value = "父id 上一级依赖的id，1级分类则为0，二级三级分别依赖上一级")
	private Long fatherId;
    
	@ApiModelProperty(name = "logo" , value = "logo")
	private String logo;
    
	@ApiModelProperty(name = "slogan" , value = "口号")
	private String slogan;
    
	@ApiModelProperty(name = "catImage" , value = "分类图")
	private String catImage;
    
	@ApiModelProperty(name = "bgColor" , value = "背景颜色")
	private String bgColor;
    

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
