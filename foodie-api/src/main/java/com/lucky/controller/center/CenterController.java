package com.lucky.controller.center;

import com.lucky.core.JsonResult;
import com.lucky.service.center.CenterUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 用户中心展示相关接口
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 11:59
 **/
@Api(value = "用户中心", tags = "用户中心展示相关接口")
@Slf4j
@RestController
@RequestMapping(value = "/center")
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;


    private JsonResult jsonResult  = new JsonResult();

    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",httpMethod = "GET")
    @GetMapping("/userInfo")
    public JsonResult userInfo(@RequestParam
                               @ApiParam(name ="userId",value = "用户id",required = true)
                                       String userId){
        return jsonResult.success(centerUserService.queryUserInfoById(userId));
    }



}
