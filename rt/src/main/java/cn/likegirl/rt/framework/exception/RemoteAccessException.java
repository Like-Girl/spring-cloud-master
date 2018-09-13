package cn.likegirl.rt.framework.exception;

import cn.likegirl.rt.constant.ResultCode;

public class RemoteAccessException extends BusinessException {

    private static final long serialVersionUID = -6785908129997270115L;

    public RemoteAccessException() {
        super();
    }

    public RemoteAccessException(Object data) {
        super();
        super.data = data;
    }

    public RemoteAccessException(ResultCode resultCode) {
        super(resultCode);
    }

    public RemoteAccessException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }

    public RemoteAccessException(String msg) {
        super(msg);
    }

    public RemoteAccessException(String formatMsg, Object... objects) {
        super(formatMsg, objects);
    }

}
