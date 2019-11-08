package com.bro.blog.exception;

public class ParamException extends RuntimeException {
	private Object obj = null;

	public ParamException() {
	}

	public ParamException(String msg) {
		super(msg);
	}

	public ParamException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ParamException(String msg, Object obj) {
		super(msg);
		this.obj = obj;
	}

	public Object getObj() {
		return obj;
	}
}
