package com.lucky.service;

import com.lucky.pojo.Orders;

/**
 * @FileName: CenterOrderService.java
 * @description:
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 11:51
 **/
public interface CenterOrderService {
    Orders checkOrders(String orderId, String userId);

    String queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);


    void deliverItem(String orderId);

    boolean confirmReceive(String orderId);

    boolean deleteOrder(String orderId, String userId);

    String getMyOrderStatusCounts(String userId);

    String getMyOrderTrend(String userId, Integer page, Integer pageSize);
}
