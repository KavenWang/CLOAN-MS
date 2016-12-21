package com.uisftech.cloan.common.exception;

/**
 * 记录重复异常.
 * @author liuwei
 *
 */
public class DuplicateException extends AppException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateException(String message) {
		super(message);
	}

	public DuplicateException(String message, Throwable e) {
		super(message, e);
	}

}
