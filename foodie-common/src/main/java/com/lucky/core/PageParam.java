package com.lucky.core;

import java.io.Serializable;

/**
 * @FileName: PageParam.java
 * @description:
 * @author: 欧阳小广
 * @Date: 2021-01-18
 **/

public class PageParam<T> implements Serializable {
    private static final long serialVersionUID = -7248374800878487522L;
    private int pageNum = 1;
    private int pageSize = 10;
    private T param;

    public PageParam() {
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public T getParam() {
        return this.param;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setParam(T param) {
        this.param = param;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageParam)) {
            return false;
        } else {
            PageParam<?> other = (PageParam) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getPageNum() != other.getPageNum()) {
                return false;
            } else if (this.getPageSize() != other.getPageSize()) {
                return false;
            } else {
                Object this$param = this.getParam();
                Object other$param = other.getParam();
                if (this$param == null) {
                    if (other$param == null) {
                        return true;
                    }
                } else if (this$param.equals(other$param)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PageParam;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + this.getPageNum();
        result = result * 59 + this.getPageSize();
        Object $param = this.getParam();
        result = result * 59 + ($param == null ? 43 : $param.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "PageParam(pageNum=" + this.getPageNum() + ", pageSize=" + this.getPageSize() + ", param=" + this.getParam() + ")";
    }
}
