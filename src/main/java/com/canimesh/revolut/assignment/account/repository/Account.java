package com.canimesh.revolut.assignment.account.repository;

import com.canimesh.revolut.assignment.account.repository.vo.AccountDetails;

public class Account {

	private String id;
	private AccountDetails accountDetails;
	
	public String getId() {
		return this.id;
	}
	
	void setId(String id) {
		this.id = id;
	}

	public AccountDetails getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(AccountDetails accountDetails) {
		this.accountDetails = accountDetails;
	}
	
	
}
