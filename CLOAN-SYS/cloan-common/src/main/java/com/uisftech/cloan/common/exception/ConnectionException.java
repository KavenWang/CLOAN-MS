package com.uisftech.cloan.common.exception;

public class ConnectionException extends RemoteException {

	private static final long serialVersionUID = -8640171588708190622L;

	public ConnectionException(String message) {
		super(message);
	}

	public ConnectionException(String message, Throwable e) {
		super(message, e);
	}

}
