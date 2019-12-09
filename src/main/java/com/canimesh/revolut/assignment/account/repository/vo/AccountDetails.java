package com.canimesh.revolut.assignment.account.repository.vo;

import lombok.Value;

@Value
public class AccountDetails {
	
	private final String owner;
	private final long balance;
}
