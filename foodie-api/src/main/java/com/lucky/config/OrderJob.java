package com.lucky.config;

import com.lucky.service.OrdersService;
import com.lucky.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderJob {

    @Autowired
    private OrdersService ordersService;
   // @Scheduled(cron = "0/3 * * * * ?")
    public void autoCloseOrders() {
        log.info("定时任务执行---->关闭超时订单----->当前时间:{}", DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
        ordersService.closeOrders();
    }


}
