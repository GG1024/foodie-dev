package com.lucky.controller;

import com.lucky.bo.AddressBo;
import com.lucky.core.JsonResult;
import com.lucky.pojo.UserAddress;
import com.lucky.service.UserAddressService;
import com.lucky.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 地址相关接口api
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 09:33
 **/
@Api(value = "地址相关", tags = "地址相关接口api")
@Slf4j
@RestController
@RequestMapping(value = "/address")
public class AddressController {

    @Autowired
    private UserAddressService userAddressService;


    @ApiOperation(value = "显示用户地址列表", notes = "显示用户地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public JsonResult<List<UserAddress>> list(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.error("用户id不能为空!");
        }
        List<UserAddress> addressList = userAddressService.queryAll(userId);
        return JsonResult.success(addressList);
    }


    @ApiOperation(value = "增加地址信息", notes = "增加地址信息", httpMethod = "POST")
    @PostMapping("/add")
    public JsonResult add(@RequestBody AddressBo addressBo) {
        JsonResult checkResult = checkAddress(addressBo);
        if (checkResult.getCode() == 500) {
            return checkResult;
        }
        userAddressService.addAddress(addressBo);
        return JsonResult.success();
    }

    @ApiOperation(value = "修改地址信息", notes = "修改地址信息", httpMethod = "POST")
    @PostMapping("/update")
    public JsonResult update(@RequestBody AddressBo addressBo) {
        JsonResult checkResult = checkAddress(addressBo);
        if (checkResult.getCode() == 500) {
            return checkResult;
        }
        userAddressService.updateAddress(addressBo);
        return JsonResult.success();
    }

    @ApiOperation(value = "设置当前地址为默认", notes = "设置当前地址为默认", httpMethod = "POST")
    @PostMapping("/setDefalut")
    public JsonResult setDefalut(@RequestParam String userId, String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return JsonResult.error("参数不能为空!");
        }
        userAddressService.updateUserAddressToBeDefault(userId, addressId);
        return JsonResult.success();
    }

    @ApiOperation(value = "删除当前地址", notes = "删除当前地址", httpMethod = "POST")
    @PostMapping("/delete")
    public JsonResult del(@RequestParam String userId, String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return JsonResult.error("参数不能为空!");
        }
        userAddressService.deleteAddress(userId, addressId);
        return JsonResult.success();
    }

    private JsonResult checkAddress(AddressBo addressBo) {
        String receiver = addressBo.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JsonResult.error("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JsonResult.error("收货人姓名不能太长");
        }

        String mobile = addressBo.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JsonResult.error("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return JsonResult.error("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return JsonResult.error("收货人手机号格式不正确");
        }
        String province = addressBo.getProvince();
        String city = addressBo.getCity();
        String district = addressBo.getDistrict();
        String detail = addressBo.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return JsonResult.error("收货地址信息不能为空");
        }
        return JsonResult.success();
    }
}
