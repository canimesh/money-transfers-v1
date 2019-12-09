package com.canimesh.revolut.assignment.account.repository;

import java.util.Optional;

import com.canimesh.revolut.assignment.account.repository.vo.AccountDetails;

public interface AccountsRespository {

	Optional<Account> get(String accountId);

	String create(AccountDetails accountDeatils);
	
	String save(Account account);

}
