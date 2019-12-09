package com.canimesh.revolut.assignment.account.repository;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class AccountsEntity {
	private String id;
	private String owner;
	private long amount;
}
