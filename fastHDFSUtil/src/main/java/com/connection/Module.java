package com.connection;

public enum Module {
	FASTDFS("1", "FASTDFS分布式文件系统");
	/** 错误码 */
	String code;

	/** 错误信息，用于日志输出，便于问题定位 */
	String message;

	Module(String code, String message) {
		this.message = message;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
