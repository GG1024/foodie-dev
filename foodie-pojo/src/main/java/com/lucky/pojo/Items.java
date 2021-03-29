/**
 * @filename:Items 2019年10月16日
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
 * @Description:TODO(商品表实体类)
 * 
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Items extends Model<Items> {

	private static final long serialVersionUID = 1610950351041L;
	
	@ApiModelProperty(name = "id" , value = "")
	private String id;
    
	@ApiModelProperty(name = "itemName" , value = "商品名称")
	private String itemName;
    
	@ApiModelProperty(name = "catId" , value = "分类外键id")
	private Long catId;
    
	@ApiModelProperty(name = "rootCatId" , value = "一级分类外键id")
	private Long rootCatId;
    
	@ApiModelProperty(name = "sellCounts" , value = "累计销售")
	private BigDecimal sellCounts;
    
	@ApiModelProperty(name = "onOffStatus" , value = "上下架状态,1:上架 2:下架")
	private String onOffStatus;
    
	@ApiModelProperty(name = "content" , value = "商品内容")
	private String content;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "创建时间")
	private Date createdTime;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "更新时间")
	private Date updatedTime;
    

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
