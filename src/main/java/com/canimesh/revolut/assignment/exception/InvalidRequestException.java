package com.canimesh.revolut.assignment.exception;

public class InvalidRequestException extends ApplicationRuntimeException {

	public InvalidRequestException(String message) {
		super(400, message);
	}

	private static final long serialVersionUID = 1L;

}
