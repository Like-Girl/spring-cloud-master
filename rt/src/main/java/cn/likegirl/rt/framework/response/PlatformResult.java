package cn.likegirl.rt.framework.response;


import cn.likegirl.rt.constant.ResultCode;

/**
 * @desc 平台通用返回结果
 * 
 * @author zhumaer
 * @since 10/9/2017 3:00 PM
 */
public class PlatformResult implements Result {

	private static final long serialVersionUID = 874200365941306385L;

	private Integer code;

	private String message;

	private Object data;

	public static PlatformResult success() {
		PlatformResult result = new PlatformResult();
		result.setResultCode(ResultCode.SUCCESS);
		return result;
	}

	public static PlatformResult success(Object data) {
		PlatformResult result = new PlatformResult();
		result.setResultCode(ResultCode.SUCCESS);
		result.setData(data);
		return result;
	}

	public static PlatformResult failure(ResultCode resultCode) {
		PlatformResult result = new PlatformResult();
		result.setResultCode(resultCode);
		return result;
	}

	public static PlatformResult failure(ResultCode resultCode, Object data) {
		PlatformResult result = new PlatformResult();
		result.setResultCode(resultCode);
		result.setData(data);
		return result;
	}

	public static PlatformResult failure(String message) {
		PlatformResult result = new PlatformResult();
		result.setCode(ResultCode.PARAM_IS_INVALID.getCode());
		result.setMessage(message);
		return result;
	}

	private void setResultCode(ResultCode code) {
		this.code = code.getCode();
		this.message = code.getMessage();
	}


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
