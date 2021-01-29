/**
 * @filename:CarouselController 2019年10月16日
 * @project Copyright(c) 2020 欧阳小广 Co. Ltd.
 * All right reserved.
 */
package com.lucky.core;

import com.github.pagehelper.PageHelper;
import com.lucky.core.page.PageDomain;
import com.lucky.core.page.TableSupport;
import com.lucky.utils.SqlUtil;

/**
 * @Description:TODO(轮播图API接口层)
 * @version:
 * @author: 欧阳小广
 * @time 2019年10月16日
 */
public class AbstractController {


    /**
     * 设置请求分页数据
     */
    protected void startPage() {

        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (pageNum == null) {
            pageNum = 1;
            pageDomain.setPageNum(pageNum);
        }
        if (pageSize == null) {
            pageSize = 10;
            pageDomain.setPageSize(pageSize);
        }
        if (pageNum != null && pageSize != null) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }
}
