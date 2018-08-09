package cn.likegirl.rt.framework.exception;

import cn.likegirl.rt.constant.ResultCode;

public class InternalServerException extends BusinessException {

    private static final long serialVersionUID = 3721036867889297081L;

    public InternalServerException() {
        super();
    }

    public InternalServerException(Object data) {
        super();
        super.data = data;
    }

    public InternalServerException(ResultCode resultCode) {
        super(resultCode);
    }

    public InternalServerException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }

    public InternalServerException(String msg) {
        super(msg);
    }

    public InternalServerException(String formatMsg, Object... objects) {
        super(formatMsg, objects);
    }

}