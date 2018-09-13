package cn.likegirl.rt.framework.exception;

import cn.likegirl.rt.constant.ResultCode;


/**
 * @author LikeGirl
 */
public class DataNotFoundException extends BusinessException {


    private static final long serialVersionUID = 429667789018724339L;

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(Object data) {
        super();
        super.data = data;
    }

    public DataNotFoundException(ResultCode resultCode) {
        super(resultCode);
    }

    public DataNotFoundException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }

    public DataNotFoundException(String msg) {
        super(msg);
    }

    public DataNotFoundException(String formatMsg, Object... objects) {
        super(formatMsg, objects);
    }

}
