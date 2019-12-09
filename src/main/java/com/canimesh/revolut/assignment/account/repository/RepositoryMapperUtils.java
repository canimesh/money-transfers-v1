package com.canimesh.revolut.assignment.account.repository;

import com.canimesh.revolut.assignment.account.repository.vo.AccountDetails;

class RepositoryMapperUtils {

	public static Account convert(AccountsEntity accountsEntity) {
		Account account = new Account();
		account.setId(accountsEntity.getId());
		account.setAccountDetails(new AccountDetails(accountsEntity.getOwner(), accountsEntity.getAmount()));
		return account;
	}
}
