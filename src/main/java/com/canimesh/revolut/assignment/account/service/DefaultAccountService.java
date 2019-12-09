package com.canimesh.revolut.assignment.account.service;

import com.canimesh.revolut.assignment.account.repository.Account;
import com.canimesh.revolut.assignment.account.repository.AccountsRespository;
import com.canimesh.revolut.assignment.account.repository.vo.AccountDetails;
import com.canimesh.revolut.assignment.account.service.vo.AccountInfo;
import com.canimesh.revolut.assignment.exception.NotfoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultAccountService implements AccountsService {

	private final AccountsRespository accountsRespository;
	
	@Override
	public AccountInfo get(String accountId) {
		Account accountFromRepo = getAccount(accountId);
		return convert(accountFromRepo);
	}

	private Account getAccount(String accountId) {
		return accountsRespository.get(accountId).orElseThrow(() -> {return new NotfoundException("Account not found");});
	}

	private AccountInfo convert(Account accountFromRepo) {
		return new AccountInfo(accountFromRepo.getId(), accountFromRepo.getAccountDetails().getOwner(), accountFromRepo.getAccountDetails().getBalance());
	}

	@Override
	public String createAccount(String owner, long money) {
		return accountsRespository.create(new AccountDetails(owner,money));
	}

	@Override
	public String save(AccountInfo accountInfo) {
		Account account = getAccount(accountInfo.getAccountId());
		account.setAccountDetails(new AccountDetails(accountInfo.getOwner(), accountInfo.getAmount()));
		return accountsRespository.save(account);
	}
	
	

}
