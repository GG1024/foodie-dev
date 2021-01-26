package com.lucky.core;

import java.io.Serializable;
import java.net.ConnectException;
import java.sql.SQLException;

/**
 * @FileName: JsonResult.java
 * @description:
 * @author: 欧阳小广
 * @Date: 2021-01-18
 **/
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = 1071681926787951549L;
    private Integer code;
    private String operate;
    private String message;
    private T data;

    public JsonResult<T> success(String message, T data) {
        this.setCode(200);
        this.setOperate("success");
        this.setMessage(message);
        this.setData(data);
        return this;
    }

    public JsonResult<T> success(T data) {
        this.setCode(200);
        this.setOperate("success");
        this.setMessage("操作成功");
        this.setData(data);
        return this;
    }

    public JsonResult<T> success() {
        this.setCode(200);
        this.setOperate("success");
        this.setMessage("操作成功");
        this.setData(null);
        return this;
    }

    public JsonResult<T> success(String message) {
        this.setCode(200);
        this.setOperate("success");
        this.setMessage(message);
        this.setData(null);
        return this;
    }

    public JsonResult<T> error(String message) {
        this.setCode(500);
        this.setOperate("failed");
        this.setMessage(message);
        this.setData(null);
        return this;
    }

    public JsonResult<T> error(T data) {
        this.setCode(500);
        this.setOperate("failed");
        this.setMessage("操作失败");
        this.setData(data);
        return this;
    }

    public JsonResult<T> error(String message, T data) {
        this.setCode(500);
        this.setOperate("failed");
        this.setMessage(message);
        this.setData(data);
        return this;
    }

    public JsonResult(Throwable throwable) {
        this.operate = "failed";
        if (throwable instanceof NullPointerException) {
            this.code = 1001;
            this.message = "空指针：" + throwable;
        } else if (throwable instanceof ClassCastException) {
            this.code = 1002;
            this.message = "类型强制转换异常：" + throwable;
        } else if (throwable instanceof ConnectException) {
            this.code = 1003;
            this.message = "链接失败：" + throwable;
        } else if (throwable instanceof IllegalArgumentException) {
            this.code = 1004;
            this.message = "传递非法参数异常：" + throwable;
        } else if (throwable instanceof NumberFormatException) {
            this.code = 1005;
            this.message = "数字格式异常：" + throwable;
        } else if (throwable instanceof IndexOutOfBoundsException) {
            this.code = 1006;
            this.message = "下标越界异常：" + throwable;
        } else if (throwable instanceof SecurityException) {
            this.code = 1007;
            this.message = "安全异常：" + throwable;
        } else if (throwable instanceof SQLException) {
            this.code = 1008;
            this.message = "数据库异常：" + throwable;
        } else if (throwable instanceof ArithmeticException) {
            this.code = 1009;
            this.message = "算术运算异常：" + throwable;
        } else if (throwable instanceof RuntimeException) {
            this.code = 1010;
            this.message = "运行时异常：" + throwable;
        } else if (throwable instanceof Exception) {
            this.code = 9999;
            this.message = "未知异常" + throwable;
        }

    }

    public Integer getCode() {
        return this.code;
    }

    public String getOperate() {
        return this.operate;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof JsonResult)) {
            return false;
        } else {
            JsonResult<?> other = (JsonResult) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59:
                {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label59;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label59;
                    }

                    return false;
                }

                Object this$operate = this.getOperate();
                Object other$operate = other.getOperate();
                if (this$operate == null) {
                    if (other$operate != null) {
                        return false;
                    }
                } else if (!this$operate.equals(other$operate)) {
                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof JsonResult;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $operate = this.getOperate();
        result = result * 59 + ($operate == null ? 43 : $operate.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "JsonResult(code=" + this.getCode() + ", operate=" + this.getOperate() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }

    public JsonResult() {
    }

    public JsonResult(Integer code, String operate, String message, T data) {
        this.code = code;
        this.operate = operate;
        this.message = message;
        this.data = data;
    }
}
