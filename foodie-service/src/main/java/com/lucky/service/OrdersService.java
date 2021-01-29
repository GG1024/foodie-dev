/**
 * @filename:OrdersService 2019年10月16日
 * @project Copyright(c) 2020 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.service;

import com.lucky.bo.ShopcartBo;
import com.lucky.bo.SubmitOrderBo;
import com.lucky.pojo.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lucky.vo.OrderVo;

import java.util.List;

/**
 * @Description:TODO(订单表服务层)
 * @version:
 * @author: 欧阳小广
 *
 */
public interface OrdersService extends IService<Orders> {

    OrderVo createOrder(SubmitOrderBo submitOrderBo, List<ShopcartBo> shopcartBoList);

    void updateOrderStatus(String orderId, Integer orderStatus);

    void closeOrders();

   void closeOrdersByOrderId(String orderId);

}