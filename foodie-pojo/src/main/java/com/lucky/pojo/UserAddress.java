/**
 * @filename:UserAddress 2019年10月16日
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
 * @Description:TODO(用户地址表实体类)
 * 
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserAddress extends Model<UserAddress> {

	private static final long serialVersionUID = 1610950474363L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "")
	private String id;
    
	@ApiModelProperty(name = "userId" , value = "关联用户id")
	private String userId;
    
	@ApiModelProperty(name = "receiver" , value = "收件人姓名")
	private String receiver;
    
	@ApiModelProperty(name = "mobile" , value = "收件人手机号")
	private String mobile;
    
	@ApiModelProperty(name = "province" , value = "省份")
	private String province;
    
	@ApiModelProperty(name = "city" , value = "城市")
	private String city;
    
	@ApiModelProperty(name = "district" , value = "区县")
	private String district;
    
	@ApiModelProperty(name = "detail" , value = "详细地址")
	private String detail;
    
	@ApiModelProperty(name = "extand" , value = "拓展字段")
	private String extand;
    
	@ApiModelProperty(name = "isDefault" , value = "是否默认地址1:是  0:否")
	private String isDefault;
    
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
