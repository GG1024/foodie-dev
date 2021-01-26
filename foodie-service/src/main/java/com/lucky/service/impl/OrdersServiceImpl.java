/**
 * @filename:OrdersServiceImpl 2019年10月16日
 * @project Copyright(c) 2018 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.service.impl;

import com.lucky.bo.ShopcartBo;
import com.lucky.bo.SubmitOrderBo;
import com.lucky.pojo.Orders;
import com.lucky.mapper.OrdersMapper;
import com.lucky.service.OrdersService;
import com.lucky.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description:TODO(订单表服务实现)
 * @version:
 * @author: 欧阳小广
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {


    @Override
    public OrderVo createOrder(SubmitOrderBo submitOrderBo, List<ShopcartBo> shopcartBoList) {
        return null;
    }

    @Override
    public void updateOrderStatus(String merchantOrderId, Integer type) {

    }
}