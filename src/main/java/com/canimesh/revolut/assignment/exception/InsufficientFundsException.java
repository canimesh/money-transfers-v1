package com.canimesh.revolut.assignment.exception;

public class InsufficientFundsException extends ApplicationRuntimeException {

	private static final long serialVersionUID = 1L;

	public InsufficientFundsException(String message) {
		super(400,message);
	}

}
