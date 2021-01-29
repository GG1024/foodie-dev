package com.lucky.controller.center;

import com.lucky.core.AbstractController;
import com.lucky.core.JsonResult;
import com.lucky.pojo.OrderStatus;
import com.lucky.pojo.Orders;
import com.lucky.service.center.CenterOrderService;
import com.lucky.utils.PageResult;
import com.lucky.vo.MyOrdersVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 用户中心我的订单相关接口
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 13:40
 **/
@Api(value = "/", description = "用户中心我的订单相关接口")
@Slf4j
@RestController
@RequestMapping("/myorders")
public class CenterOrdersController extends AbstractController {


    @Autowired
    private CenterOrderService centerOrderService;

    private JsonResult jsonResult = new JsonResult();

    @ApiOperation(value = "查询用户订单", notes = "查询用户订单", httpMethod = "GET")
    @PostMapping("/query")
    public PageResult<MyOrdersVO> searchItems(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(value = "userId") String userId,
            @ApiParam(name = "orderStatus", value = "orderStatus")
            @RequestParam(value = "orderStatus") Integer orderStatus,
            @ApiParam(name = "pageNum", value = "页码")
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每一页显示数")
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        startPage();
        List<MyOrdersVO> ordersVOList = centerOrderService.queryMyOrders(userId, orderStatus);
        return PageResult.setPageResult(ordersVOList, pageNum);
    }


    @ApiOperation(value = "卖家发货接口", notes = "卖家发货接口---测试用", httpMethod = "GET")
    @GetMapping("/deliver")
    public JsonResult deliver(
            @ApiParam(name = "orderId", value = "orderId", required = true)
            @RequestParam(value = "orderId") String orderId) {

        centerOrderService.deliverItem(orderId);
        return jsonResult.success();

    }


    @ApiOperation(value = "确认收货接口", notes = "确认收货接口", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public JsonResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam(value = "orderId") String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(value = "userId") String userId) {
        //检查订单id与用户是否匹配
        JsonResult checkOrder = checkOrders(orderId, userId);
        if (checkOrder.getCode() != HttpStatus.OK.value()) {
            return checkOrder;
        }
        boolean b = centerOrderService.confirmReceive(orderId);
        if (!b) {
            return jsonResult.error("操作失败!");
        }
        return jsonResult.success("操作成功!");
    }

    @ApiOperation(value = "逻辑删除订单", notes = "逻辑删除订单接口", httpMethod = "POST")
    @PostMapping("/delete")
    public JsonResult delete(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam(value = "orderId") String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(value = "userId") String userId) {
        //检查订单id与用户是否匹配
        JsonResult checkOrders = checkOrders(orderId, userId);
        if (checkOrders.getCode() != HttpStatus.OK.value()) {
            return checkOrders;
        }
        boolean deleteOrder = centerOrderService.deleteOrder(orderId, userId);
        if (!deleteOrder) {
            return jsonResult.error("操作失败!");
        }
        return jsonResult.success("删除成功!");
    }

    @ApiOperation(value = "查询用户id与订单id是否匹配", notes = "查询用户id与订单id是否匹配", httpMethod = "GET")
    @PostMapping("/checkOrders")
    public JsonResult checkOrders(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam(value = "orderId") String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(value = "userId") String userId) {
        Orders orders = centerOrderService.checkOrders(orderId, userId);
        if (orders == null) {
            return jsonResult.error("订单不匹配!");
        }
        return jsonResult.success();

    }

    @ApiOperation(value = "查询订单各个状态数目", notes = "查询订单各个状态数目", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public JsonResult statusCounts(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(value = "userId") String userId) {
        return jsonResult.success(centerOrderService.getMyOrderStatusCounts(userId));
    }


    @ApiOperation(value = "查询用户订单", notes = "查询用户订单", httpMethod = "GET")
    @PostMapping("/trend")
    public PageResult<OrderStatus> getMyOrderTrend(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(value = "userId") String userId,
            @ApiParam(name = "pageNum", value = "页码")
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每一页显示数")
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        startPage();
        List<OrderStatus> myOrderTrend = centerOrderService.getMyOrderTrend(userId);
        return PageResult.setPageResult(myOrderTrend, pageNum);
    }
}
