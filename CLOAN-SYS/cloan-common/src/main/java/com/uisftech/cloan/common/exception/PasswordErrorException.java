package com.uisftech.cloan.common.exception;


/**
 * 记录不存在异�?
 * @author yantao
 *
 */
public class PasswordErrorException extends AppException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PasswordErrorException(String message) {
		super(message);
	}

	public PasswordErrorException(String message, Throwable e) {
		super(message, e);
	}

}
