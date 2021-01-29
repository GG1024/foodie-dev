/**
 * @filename:Orders 2019年10月16日
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
 * @Description:TODO(订单表实体类)
 * 
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Orders extends Model<Orders> {

	private static final long serialVersionUID = 1610950462230L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "同时也是订单编号")
	private String id;
    
	@ApiModelProperty(name = "userId" , value = "")
	private String userId;
    
	@ApiModelProperty(name = "receiverName" , value = "")
	private String receiverName;
    
	@ApiModelProperty(name = "receiverMobile" , value = "")
	private String receiverMobile;
    
	@ApiModelProperty(name = "receiverAddress" , value = "")
	private String receiverAddress;
    
	@ApiModelProperty(name = "totalAmount" , value = "")
	private BigDecimal totalAmount;
    
	@ApiModelProperty(name = "realPayAmount" , value = "")
	private BigDecimal realPayAmount;
    
	@ApiModelProperty(name = "postAmount" , value = "默认可以为零，代表包邮")
	private int postAmount;
    
	@ApiModelProperty(name = "payMethod" , value = "1:微信 2:支付宝")
	private int payMethod;
    
	@ApiModelProperty(name = "leftMsg" , value = "")
	private String leftMsg;
    
	@ApiModelProperty(name = "extand" , value = "")
	private String extand;
    
	@ApiModelProperty(name = "isComment" , value = "1：已评价，0：未评价")
	private int isComment;
    
	@ApiModelProperty(name = "isDelete" , value = "1: 删除 0:未删除")
	private int isDelete;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createdTime" , value = "")
	private Date createdTime;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "")
	private Date updateTime;
    

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
