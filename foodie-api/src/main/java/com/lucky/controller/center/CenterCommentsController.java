package com.lucky.controller.center;

import com.lucky.bo.OrderItemsCommentBO;
import com.lucky.core.AbstractController;
import com.lucky.core.JsonResult;
import com.lucky.pojo.OrderItems;
import com.lucky.pojo.Orders;
import com.lucky.service.center.CenterCommentsService;
import com.lucky.service.center.CenterOrderService;
import com.lucky.utils.PageResult;
import com.lucky.vo.MyCommentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName: CenterCommentsController.java
 * @description: 用户中心评价管理相关接口
 * @author: 欧阳小广
 * @Date: 2021-01-26
 **/
@Api(value = "用户中心评价管理", tags = "用户中心评价管理相关接口")
@Slf4j
@RestController
@RequestMapping("/mycomments")
public class CenterCommentsController extends AbstractController {


    @Autowired
    private CenterCommentsService centerCommentsService;

    @Autowired
    private CenterOrderService centerOrderService;

    private JsonResult jsonResult = new JsonResult();

    @ApiOperation(value = "根据订单id查询待评价商品", notes = "根据订单id查询待评价商品", httpMethod = "POST")
    @PostMapping("/pending")
    public JsonResult<OrderItems> searchItems(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(value = "userId") String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam(value = "orderId") String orderId
    ) {
        Orders orders = centerOrderService.checkOrders(orderId, userId);
        if (orders == null) {
            return jsonResult.error("订单id和用户id不匹配！");
        }

        List<OrderItems> orderItems = centerCommentsService.queryPendingComments(orderId);
        return jsonResult.success(orderItems);
    }


    @ApiOperation(value = "保存商品评论", notes = "保存商品评论", httpMethod = "POST")
    @PostMapping("/saveList")
    public JsonResult saveList(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(value = "userId") String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam(value = "orderId") String orderId,
            @ApiParam(name = "orderItemList", value = "商品评论列表", required = true)
            @RequestBody List<OrderItemsCommentBO> orderItemList
    ) {
        if (orderItemList == null || orderItemList.isEmpty()) {
            return jsonResult.error("评论不能为空!");
        }

        centerCommentsService.saveComments(userId, orderId, orderItemList);
        return jsonResult.success();
    }


    @ApiOperation(value = "查询我的评论", notes = "查询我的评论", httpMethod = "POST")
    @PostMapping("/query")
    public PageResult searchItems(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam(value = "userId") String userId,
            @ApiParam(name = "pageNum", value = "页码")
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每一页显示数")
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        startPage();
        List<MyCommentVO> myCommentVOS = centerCommentsService.queryMyComments(userId);
        return PageResult.setPageResult(myCommentVOS, pageNum);
    }
}
