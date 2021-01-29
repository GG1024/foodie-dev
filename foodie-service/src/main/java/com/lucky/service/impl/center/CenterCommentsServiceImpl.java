package com.lucky.service.impl.center;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lucky.bo.OrderItemsCommentBO;
import com.lucky.enums.YesOrNo;
import com.lucky.mapper.ItemsCommentsMapper;
import com.lucky.mapper.OrderItemsMapper;
import com.lucky.mapper.OrderStatusMapper;
import com.lucky.mapper.OrdersMapper;
import com.lucky.pojo.OrderItems;
import com.lucky.pojo.OrderStatus;
import com.lucky.pojo.Orders;
import com.lucky.service.center.CenterCommentsService;
import com.lucky.vo.MyCommentVO;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName: CenterCommentsServiceImpl.java
 * @description:
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-29 09:26
 **/
@Slf4j
@Service
public class CenterCommentsServiceImpl implements CenterCommentsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrdersMapper ordersMapper;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<OrderItems> queryPendingComments(String orderId) {
        QueryWrapper<OrderItems> where = new QueryWrapper<>();
        where.lambda().eq(OrderItems::getOrderId, orderId);
        return orderItemsMapper.selectList(where);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveComments(String userId, String orderId, List<OrderItemsCommentBO> orderItemsCommentBOList) {

        orderItemsCommentBOList.stream().forEach(orderItemsCommentBO -> {
            String itemCommentId = sid.nextShort();
            orderItemsCommentBO.setCommentId(itemCommentId);
        });
        //保存评价
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderItemsCommentBOList", orderItemsCommentBOList);
        itemsCommentsMapper.batchSaveComments(map);

        //更新订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setCommentTime(new Date());

        QueryWrapper<OrderStatus> where = new QueryWrapper<>();
        where.lambda().eq(OrderStatus::getOrderId, orderId);
        orderStatusMapper.update(orderStatus, where);

        //更新订单为已评论
        Orders orders = new Orders();
        orders.setIsComment(YesOrNo.YES.code);
        orders.setUpdateTime(new Date());

        QueryWrapper<Orders> orderWhere = new QueryWrapper<>();
        orderWhere.lambda().eq(Orders::getId, orderId);
        ordersMapper.update(orders,orderWhere);

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MyCommentVO> queryMyComments(String userId) {
        List<MyCommentVO> myCommentVOS = itemsCommentsMapper.queryMyComments(userId);
        return myCommentVOS;
    }
}
