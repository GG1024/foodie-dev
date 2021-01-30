/**
 * @filename:OrderItems 2019年10月16日
 * @project   
 * Copyright(c) 2020 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.pojo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**   
 * @Description:TODO(订单商品关联表实体类)
 * 
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderItems extends Model<OrderItems> {

	private static final long serialVersionUID = 1610950435753L;
	
	@ApiModelProperty(name = "id" , value = "")
	private String id;
    
	@ApiModelProperty(name = "orderId" , value = "归属订单id")
	private String orderId;
    
	@ApiModelProperty(name = "itemId" , value = "商品id")
	private String itemId;
    
	@ApiModelProperty(name = "itemImg" , value = "商品图片")
	private String itemImg;
    
	@ApiModelProperty(name = "itemName" , value = "商品名称")
	private String itemName;
    
	@ApiModelProperty(name = "itemSpecId" , value = "规格id")
	private String itemSpecId;
    
	@ApiModelProperty(name = "itemSpecName" , value = "规格名称")
	private String itemSpecName;
    
	@ApiModelProperty(name = "price" , value = "成交价格")
	private BigDecimal price;
    
	@ApiModelProperty(name = "buyCounts" , value = "购买数量")
	private Integer buyCounts;

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
