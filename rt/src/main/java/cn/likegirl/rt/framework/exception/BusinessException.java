package cn.likegirl.rt.framework.exception;

import cn.likegirl.rt.constant.ExceptionEnum;
import cn.likegirl.rt.constant.ResultCode;
import org.springframework.util.StringUtils;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 194906846739586856L;

    protected int code;

    protected String msg;

    protected ResultCode resultCode;

    protected Object data;

    public BusinessException() {
        ExceptionEnum exceptionEnum = ExceptionEnum.getByEClass(this.getClass());
        if (exceptionEnum != null) {
            resultCode = exceptionEnum.getResultCode();
            code = exceptionEnum.getResultCode().getCode();
            msg = exceptionEnum.getResultCode().getMessage();
        }

    }

    public BusinessException(String message) {
        this();
        this.msg = message;
    }

    public BusinessException(String format, Object... objects) {
        this();
        format = StringUtils.replace(format, "{}", "%s");
        this.msg = String.format(format, objects);
    }

    public BusinessException(String msg, Throwable cause, Object... objects) {
        this();
        String format = StringUtils.replace(msg, "{}", "%s");
        this.msg= String.format(format, objects);
    }

    public BusinessException(ResultCode resultCode, Object data) {
        this(resultCode);
        this.data = data;
    }

    public BusinessException(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public Object getData() {
        return data;
    }
}