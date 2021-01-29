package com.lucky.service.impl.center;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lucky.enums.OrderStatusEnum;
import com.lucky.enums.YesOrNo;
import com.lucky.mapper.OrderStatusMapper;
import com.lucky.mapper.OrdersMapper;
import com.lucky.mapper.OrdersMapperCustom;
import com.lucky.pojo.OrderStatus;
import com.lucky.pojo.Orders;
import com.lucky.service.center.CenterOrderService;
import com.lucky.vo.MyOrdersVO;
import com.lucky.vo.OrderStatusCountsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName: CenterOrderServiceImpl.java
 * @description:
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-29 11:56
 **/
@Slf4j
@Service
public class CenterOrderServiceImpl implements CenterOrderService {

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersMapperCustom ordersMapperCustom;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Orders checkOrders(String orderId, String userId) {
        QueryWrapper<Orders> where = new QueryWrapper<>();
        where.lambda().eq(Orders::getId, orderId);
        where.lambda().eq(Orders::getUserId, userId);
        where.lambda().eq(Orders::getIsDelete, YesOrNo.NO.code);
        return ordersMapper.selectOne(where);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MyOrdersVO> queryMyOrders(String userId, Integer orderStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderStatus", orderStatus);
        List<MyOrdersVO> ordersVOList = ordersMapperCustom.queryMyOrders(map);
        return ordersVOList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deliverItem(String orderId) {

        //更新订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setDeliverTime(new Date());
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        QueryWrapper<OrderStatus> wehre = new QueryWrapper<>();
        wehre.lambda().eq(OrderStatus::getOrderStatus, OrderStatusEnum.WAIT_DELIVER.type);
        wehre.lambda().eq(OrderStatus::getOrderId, orderId);
        orderStatusMapper.update(orderStatus, wehre);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean confirmReceive(String orderId) {
        //更新订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setSuccessTime(new Date());
        orderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        QueryWrapper<OrderStatus> wehre = new QueryWrapper<>();
        wehre.lambda().eq(OrderStatus::getOrderStatus, OrderStatusEnum.WAIT_RECEIVE.type);
        wehre.lambda().eq(OrderStatus::getOrderId, orderId);
        int updateCount = orderStatusMapper.update(orderStatus, wehre);
        return updateCount == 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteOrder(String orderId, String userId) {
        Orders orders = new Orders();
        orders.setUpdateTime(new Date());
        orders.setIsDelete(YesOrNo.YES.code);

        QueryWrapper<Orders> where = new QueryWrapper<>();
        where.lambda().eq(Orders::getUserId, userId);
        where.lambda().eq(Orders::getId, orderId);
        int updateCount = ordersMapper.update(orders, where);
        return updateCount == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public OrderStatusCountsVO getMyOrderStatusCounts(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCount = ordersMapperCustom.getMyOrderStatusCounts(map);
        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCount = ordersMapperCustom.getMyOrderStatusCounts(map);
        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCount = ordersMapperCustom.getMyOrderStatusCounts(map);
        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.code);
        int waitCommentCount = ordersMapperCustom.getMyOrderStatusCounts(map);


        OrderStatusCountsVO orderStatusCountsVO = new OrderStatusCountsVO();
        orderStatusCountsVO.setWaitCommentCounts(waitCommentCount);
        orderStatusCountsVO.setWaitDeliverCounts(waitDeliverCount);
        orderStatusCountsVO.setWaitPayCounts(waitPayCount);
        orderStatusCountsVO.setWaitReceiveCounts(waitReceiveCount);
        return orderStatusCountsVO;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<OrderStatus> getMyOrderTrend(String userId) {
        List<OrderStatus> myOrderTrend = ordersMapperCustom.getMyOrderTrend(userId);
        return myOrderTrend;
    }
}
