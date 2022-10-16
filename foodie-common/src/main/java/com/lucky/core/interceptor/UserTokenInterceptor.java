package com.lucky.core.interceptor;

import com.lucky.core.JsonResult;
import com.lucky.utils.JsonUtils;
import com.lucky.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @FileName: UserTokenInterceptor.java
 * @description: 用户会话拦截器
 * @author: 欧阳小广
 * @Date: 2021-01-26
 **/

public class UserTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;
    public static final String REDIS_USER_TOKEN = "redis_user_token";
    private static Logger logger = LoggerFactory.getLogger(UserTokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        logger.info("userId:" + userId);
        logger.info("userToken:" + userToken);
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
            String uniqueToken = redisUtil.get(REDIS_USER_TOKEN + ":" + userId);
            if (StringUtils.isBlank(uniqueToken)) {
                returnErrorResponse(response, JsonResult.error("请登录"));
                return false;
            } else {
                if (!userToken.equals(uniqueToken)) {
                    returnErrorResponse(response, JsonResult.error("该账号异地登陆，请重登！"));
                    return false;
                }
            }
        } else {
            returnErrorResponse(response, JsonResult.error("请登录"));
            return false;
        }
        return true;
    }


    public void returnErrorResponse(HttpServletResponse response, JsonResult jsonResult) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JsonUtils.objectToJson(jsonResult).getBytes());
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
