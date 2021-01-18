/**
 * @filename:ItemsComments 2019年10月16日
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
 * @Description:TODO(商品表实体类)
 * 
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ItemsComments extends Model<ItemsComments> {

	private static final long serialVersionUID = 1610950371588L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "")
	private String id;
    
	@ApiModelProperty(name = "userId" , value = "用户id")
	private String userId;
    
	@ApiModelProperty(name = "itemId" , value = "商品id")
	private String itemId;
    
	@ApiModelProperty(name = "itemName" , value = "")
	private String itemName;
    
	@ApiModelProperty(name = "itemSpecId" , value = "")
	private String itemSpecId;
    
	@ApiModelProperty(name = "sepcName" , value = "")
	private String sepcName;
    
	@ApiModelProperty(name = "commentLevel" , value = "1：好评 2：中评 3：差评")
	private String commentLevel;
    
	@ApiModelProperty(name = "content" , value = "")
	private String content;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "")
	private Date createTime;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "update" , value = "")
	private Date update;
    

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
