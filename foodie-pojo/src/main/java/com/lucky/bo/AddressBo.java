package com.lucky.bo;

import lombok.Data;

/**
 * @author lank
 * @date 2020/1/19 11:28
 * @desc 用户新增或修改地址bo
 */
@Data
public class AddressBo {

    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;
}
