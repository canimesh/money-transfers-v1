package com.canimesh.revolut.assignment.account.service;

import com.canimesh.revolut.assignment.account.service.vo.AccountInfo;

public interface AccountsService {

	AccountInfo get(String accountId);

	String createAccount(String owner, long money);
	
	String save(AccountInfo accountInfo);

}
