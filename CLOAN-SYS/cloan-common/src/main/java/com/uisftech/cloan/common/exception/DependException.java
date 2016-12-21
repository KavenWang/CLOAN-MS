package com.uisftech.cloan.common.exception;

/**
 * 记录不存在异常
 * @author liuwei
 *
 */
public class DependException extends AppException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public DependException(String message) {
		super(message);
	}

	public DependException(String message, Throwable e) {
		super(message, e);
	}

}
