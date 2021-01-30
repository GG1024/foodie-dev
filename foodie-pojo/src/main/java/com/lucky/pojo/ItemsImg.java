/**
 * @filename:ItemsImg 2019年10月16日
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
 * @Description:TODO(商品图片实体类)
 * 
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ItemsImg extends Model<ItemsImg> {

	private static final long serialVersionUID = 1610950395256L;
	
	@ApiModelProperty(name = "id" , value = "图片主键")
	private String id;
    
	@ApiModelProperty(name = "itemId" , value = "商品外键id")
	private String itemId;
    
	@ApiModelProperty(name = "url" , value = "图片地址")
	private String url;
    
	@ApiModelProperty(name = "sort" , value = "图片顺序，从小到大")
	private Long sort;
    
	@ApiModelProperty(name = "isMain" , value = "是否主图，1：是，0：否")
	private String isMain;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "")
	private Date createdTime;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "")
	private Date updatedTime;
    

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
