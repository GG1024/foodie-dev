/**
 * @filename:OrdersServiceImpl 2019年10月16日
 * @project Copyright(c) 2018 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lucky.bo.ShopcartBo;
import com.lucky.bo.SubmitOrderBo;
import com.lucky.enums.OrderStatusEnum;
import com.lucky.enums.YesOrNo;
import com.lucky.mapper.OrderItemsMapper;
import com.lucky.mapper.OrderStatusMapper;
import com.lucky.pojo.*;
import com.lucky.mapper.OrdersMapper;
import com.lucky.service.ItemsService;
import com.lucky.service.OrdersService;
import com.lucky.service.UserAddressService;
import com.lucky.utils.DateUtil;
import com.lucky.vo.MerchantOrdersVo;
import com.lucky.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:TODO(订单表服务实现)
 * @version:
 * @author: 欧阳小广
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private Sid sid;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderVo createOrder(SubmitOrderBo submitOrderBo, List<ShopcartBo> shopcartBoList) {
        String userId = submitOrderBo.getUserId();
        String itemSpecIds = submitOrderBo.getItemSpecIds();
        Integer payMethod = submitOrderBo.getPayMethod();
        String addressId = submitOrderBo.getAddressId();
        String leftMsg = submitOrderBo.getLeftMsg();


        Orders orders = new Orders();
        String orderId = sid.nextShort();
        orders.setId(orderId);
        orders.setUserId(userId);

        //获取收货人信息
        UserAddress address = userAddressService.queryUserAddress(addressId, userId);
        orders.setReceiverName(address.getReceiver());
        orders.setReceiverMobile(address.getMobile());
        orders.setReceiverAddress(address.getProvince() + "" + address.getCity() + "" + address.getDistrict() + "" + address.getDetail());
        orders.setCreatedTime(new Date());
        orders.setUpdateTime(new Date());

        //设置邮费 当前为0
        int postAmount = 0;
        orders.setPostAmount(Integer.valueOf(postAmount));
        orders.setPayMethod(payMethod);
        orders.setLeftMsg(leftMsg);

        orders.setIsComment(YesOrNo.YES.code);
        orders.setIsDelete(YesOrNo.NO.code);

        //根据itemSpec商品规格保存订单商品明细表
        //商品原价积累
        BigDecimal totalAmount = new BigDecimal(BigInteger.ZERO);
        //优惠后的实际支付价格积累
        BigDecimal realPayAmount = new BigDecimal(BigInteger.ZERO);

        String[] split = itemSpecIds.split(",");
        List<ShopcartBo> waitRemoveShopCartBo = new ArrayList<>();
        for (String itemSpecId : split) {
            //规格ID查询具体信息
            ItemsSpec itemsSpec = itemsService.queryItemSpecById(itemSpecId);
            //整合redis后 商品购买数量需要重新从redis中购物车获取
            //从redis中获取购物车的商品信息
            ShopcartBo shopcartBo = getShopcartBoFromRedis(shopcartBoList, itemsSpec.getId());
            if (shopcartBo == null) {
                continue;
            }

            waitRemoveShopCartBo.add(shopcartBo);
            Integer buyCounts = shopcartBo.getBuyCounts();
            totalAmount = totalAmount.add(itemsSpec.getPriceNormal()).multiply(new BigDecimal(buyCounts));
            realPayAmount = realPayAmount.add(itemsSpec.getPriceDiscount()).multiply(new BigDecimal(buyCounts));

            //根据商品ID获取商品信息填充订单商品关联表
            OrderItems orderItems = new OrderItems();
            orderItems.setId(sid.nextShort());
            orderItems.setBuyCounts(buyCounts);
            orderItems.setItemId(itemsSpec.getItemId());
            orderItems.setItemSpecId(itemsSpec.getId());
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setOrderId(orderId);
            orderItems.setPrice(realPayAmount);

            Items items = itemsService.queryItemById(itemsSpec.getItemId());
            orderItems.setItemName(items.getItemName());
            ItemsImg itemsImg = itemsService.queryItemMainImg(itemsSpec.getItemId());
            if (itemsImg != null) {
                orderItems.setItemImg(itemsImg.getUrl());
            }
            orderItemsMapper.insert(orderItems);
            //用户提交订单后，扣除库存
            itemsService.decreaseItemSpecStock(itemSpecId, buyCounts);

        }


        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);

        ordersMapper.insert(orders);


        //订单状态表
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setCreatedTime(new Date());
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatusMapper.insert(orderStatus);

        //构建商户订单  传输给支付中心
        MerchantOrdersVo merchantOrdersVo = new MerchantOrdersVo();
        merchantOrdersVo.setAmount(realPayAmount.add(new BigDecimal(postAmount)));
        merchantOrdersVo.setMerchantOrderId(orderId);
        merchantOrdersVo.setMerchantUserId(userId);
        merchantOrdersVo.setPayMethod(payMethod);

        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId(orderId);
        orderVo.setMerchantOrdersVo(merchantOrdersVo);
        orderVo.setWaitRemoveShopCartBo(waitRemoveShopCartBo);

        return orderVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        QueryWrapper<OrderStatus> where = new QueryWrapper<>();
        where.lambda().eq(OrderStatus::getOrderId, orderId);
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderStatus(orderStatus);
        orderStatusMapper.update(paidStatus, where);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void closeOrders() {

        //查询所有未支付订单，如果创建时间大于1天，关闭订单
        QueryWrapper<OrderStatus> where = new QueryWrapper<>();
        where.lambda().eq(OrderStatus::getOrderStatus, OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> orderStatusList = orderStatusMapper.selectList(where);
        if (!CollectionUtils.isEmpty(orderStatusList)) {
            orderStatusList.stream().forEach(orderStatus -> {
                int dayBetween = DateUtil.daysBetween(orderStatus.getCreatedTime(), new Date());
                if (dayBetween >= 1) {
                    //超过一天 关闭订单
                    doCloseOrders(orderStatus.getOrderId());

                }

            });
        }


    }

    @Override
    public void closeOrdersByOrderId(String orderId) {
        doCloseOrders(orderId);
    }

    private void doCloseOrders(String orderId) {
        QueryWrapper<OrderStatus> where = new QueryWrapper<>();
        where.lambda().eq(OrderStatus::getOrderId, orderId);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.CLOSE.type);
        orderStatus.setCloseTime(new Date());

        orderStatusMapper.update(orderStatus, where);

    }

    private ShopcartBo getShopcartBoFromRedis(List<ShopcartBo> shopcartBos, String itemSpecId) {
        for (ShopcartBo shopcartBo : shopcartBos) {
            if (itemSpecId.equalsIgnoreCase(shopcartBo.getSpecId())){
                return shopcartBo;
            }
        }

        return null;
    }
}