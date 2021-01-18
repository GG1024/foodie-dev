/**
 * @filename:Users 2019年10月16日
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
 * @Description:TODO(用户表实体类)
 * 
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Users extends Model<Users> {

	private static final long serialVersionUID = 1610950486696L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键id")
	private String id;
    
	@ApiModelProperty(name = "username" , value = "用户名")
	private String username;
    
	@ApiModelProperty(name = "password" , value = "密码")
	private String password;
    
	@ApiModelProperty(name = "nickname" , value = "昵称")
	private String nickname;
    
	@ApiModelProperty(name = "realname" , value = "真实姓名")
	private String realname;
    
	@ApiModelProperty(name = "face" , value = "头像")
	private String face;
    
	@ApiModelProperty(name = "mobile" , value = "手机号")
	private String mobile;
    
	@ApiModelProperty(name = "email" , value = "邮箱地址")
	private String email;
    
	@ApiModelProperty(name = "sex" , value = "性别 1:男  0:女  2:保密")
	private String sex;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@ApiModelProperty(name = "birthday" , value = "生日")
	private Date birthday;
    
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
