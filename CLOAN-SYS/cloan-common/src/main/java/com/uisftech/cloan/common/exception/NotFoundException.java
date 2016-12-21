package com.uisftech.cloan.common.exception;

/**
 * 记录不存在异常
 * @author liuwei
 *
 */
public class NotFoundException extends ServiceException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable e) {
		super(message, e);
	}

}
