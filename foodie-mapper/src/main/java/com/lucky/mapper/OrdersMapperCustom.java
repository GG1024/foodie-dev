package com.lucky.mapper;

import com.lucky.pojo.OrderStatus;
import com.lucky.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @FileName: OrdersMapperCustom.java
 * @description:
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-29 12:00
 **/
@Mapper
public interface OrdersMapperCustom {

    List<MyOrdersVO> queryMyOrders(@Param("map") Map<String, Object> map);

    List<OrderStatus> getMyOrderTrend(String userId);

    int getMyOrderStatusCounts(@Param("map") Map<String, Object> map);


}
