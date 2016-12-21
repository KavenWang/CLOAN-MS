package com.uisftech.cloan.common.exception;

public class LoginException extends RemoteException {

	private static final long serialVersionUID = 8468488001028184160L;

	public LoginException(String message) {
		super(message);
	}

	public LoginException(String message, Throwable e) {
		super(message, e);
	}

}
