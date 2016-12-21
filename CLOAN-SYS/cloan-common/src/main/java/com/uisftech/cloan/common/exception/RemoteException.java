package com.uisftech.cloan.common.exception;


public class RemoteException extends AppException {

	private static final long serialVersionUID = -5199099412099958127L;

	public RemoteException(String message) {
		super(message);
	}

	public RemoteException(String message, Throwable e) {
		super(message, e);
	}

}
