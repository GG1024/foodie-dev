package com.lucky.service.center;

import com.lucky.pojo.OrderStatus;
import com.lucky.pojo.Orders;
import com.lucky.vo.MyOrdersVO;
import com.lucky.vo.OrderStatusCountsVO;

import java.util.List;

/**
 * @FileName: CenterOrderService.java
 * @description:
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 11:51
 **/
public interface CenterOrderService {
    Orders checkOrders(String orderId, String userId);

    List<MyOrdersVO> queryMyOrders(String userId, Integer orderStatus);


    void deliverItem(String orderId);

    boolean confirmReceive(String orderId);

    boolean deleteOrder(String orderId, String userId);

    OrderStatusCountsVO getMyOrderStatusCounts(String userId);

    List<OrderStatus> getMyOrderTrend(String userId);
}
