/**
 * @filename:Carousel 2019年10月16日
 * @project   
 * Copyright(c) 2020 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

/**   
 * @Description:TODO(轮播图实体类)
 * 
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Carousel extends Model<Carousel> {

	private static final long serialVersionUID = 1610941929486L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键ID")
	private String id;
    
	@ApiModelProperty(name = "imageUrl" , value = "图片")
	private String imageUrl;
    
	@ApiModelProperty(name = "backgroundColor" , value = "背景色")
	private String backgroundColor;
    
	@ApiModelProperty(name = "itemId" , value = "商品id")
	private String itemId;
    
	@ApiModelProperty(name = "catId" , value = "商品分类id")
	private Long catId;
    
	@ApiModelProperty(name = "type" , value = "轮播图类型，用于判断，可以根据商品id或者分类进行页面跳转，1：商品 2：分类")
	private String type;
    
	@ApiModelProperty(name = "sort" , value = "轮播图展示顺序，从小到大")
	private Long sort;
    
	@ApiModelProperty(name = "isShow" , value = "是否展示，1：展示    0：不展示")
	private String isShow;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "创建时间")
	private Date createTime;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "更新时间")
	private Date updateTime;
    

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
