package com.lucky.service.center;

import com.lucky.bo.OrderItemsCommentBO;
import com.lucky.pojo.OrderItems;
import com.lucky.vo.MyCommentVO;

import java.util.List;

/**
 * @FileName: CenterCommentsService.java
 * @description:
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 11:51
 **/
public interface CenterCommentsService {
    List<OrderItems> queryPendingComments(String orderId);

    void saveComments(String userId, String orderId, List<OrderItemsCommentBO> orderItemList);

    List<MyCommentVO> queryMyComments(String userId);

}
