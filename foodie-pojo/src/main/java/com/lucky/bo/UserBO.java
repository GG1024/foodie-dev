package com.lucky.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lank
 * @date 2020/1/14 16:20
 * @desc 前端传来对象  业务打交道的实体类
 */
@ApiModel(value = "用户对象BO", description = "从客户端用户传入的数据封装于次")
@Data
public class UserBO {

    @ApiModelProperty(value = "用户名", name = "username", required = true, example = "lank")
    private String username;
    @ApiModelProperty(value = "密码", name = "password", required = true, example = "123456")
    private String password;
    @ApiModelProperty(value = "确认密码", name = "username", required = false, example = "123456")
    private String confirmPassword;


}
