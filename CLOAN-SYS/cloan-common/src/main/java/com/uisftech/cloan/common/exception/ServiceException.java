package com.uisftech.cloan.common.exception;

/**
 * 服务层的异常
 * @author liuwei
 * @version
 * @since
 * @date 2010-8-7
 */
public class ServiceException extends AppException{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable e) {
		super(message, e);
	}

}
