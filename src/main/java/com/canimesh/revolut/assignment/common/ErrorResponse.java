package com.canimesh.revolut.assignment.common;

import lombok.Getter;

@Getter
public class ErrorResponse {
	
	private final int httpStatus;
	private final String description;
	public ErrorResponse(int httpStatus, String description) {
		super();
		this.httpStatus = httpStatus;
		this.description = description;
	}
	public ErrorResponse(String description) {
		super();
		this.description = description;
		this.httpStatus = 500;
	}
	
	
}
