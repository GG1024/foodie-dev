/**
 * @filename:ItemsParam 2019年10月16日
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
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Description:TODO(商品参数实体类)
 * 
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ItemsParam extends Model<ItemsParam> {

	private static final long serialVersionUID = 1610950408421L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "")
	private String id;
    
	@ApiModelProperty(name = "itemId" , value = "商品外键id")
	private String itemId;
    
	@ApiModelProperty(name = "producPlace" , value = "产地，例：中国江苏")
	private String producPlace;
    
	@ApiModelProperty(name = "footPeriod" , value = "保质期，例：180天")
	private String footPeriod;
    
	@ApiModelProperty(name = "brand" , value = "品牌名，例：三只大灰狼")
	private String brand;
    
	@ApiModelProperty(name = "factoryName" , value = "生产厂名，例：大灰狼工厂")
	private String factoryName;
    
	@ApiModelProperty(name = "factoryAddress" , value = "生产厂址，例：大灰狼生产基地")
	private String factoryAddress;
    
	@ApiModelProperty(name = "packagingMethod" , value = "包装方式，例：袋装")
	private String packagingMethod;
    
	@ApiModelProperty(name = "weight" , value = "规格重量，例：35g")
	private BigDecimal weight;
    
	@ApiModelProperty(name = "storageMethod" , value = "存储方法，例：常温5~25°")
	private String storageMethod;
    
	@ApiModelProperty(name = "eatMethod" , value = "食用方式，例：开袋即食")
	private String eatMethod;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "")
	private Date createTime;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "")
	private Date updateTime;
    

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
