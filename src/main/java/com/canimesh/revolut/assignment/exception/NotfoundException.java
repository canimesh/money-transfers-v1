package com.canimesh.revolut.assignment.exception;

public class NotfoundException extends ApplicationRuntimeException {

	private static final long serialVersionUID = 1L;

	public NotfoundException(String message) {
		super(404,message);
	}

}
