package com.canimesh.revolut.assignment.account.rest;

import com.canimesh.revolut.assignment.account.rest.dto.AccountInfoResponse;
import com.canimesh.revolut.assignment.account.rest.dto.CreateAccountRequest;
import com.canimesh.revolut.assignment.account.service.AccountsService;
import com.canimesh.revolut.assignment.account.service.vo.AccountInfo;
import com.canimesh.revolut.assignment.exception.ApplicationRuntimeException;
import com.canimesh.revolut.assignment.exception.InvalidRequestException;

import lombok.RequiredArgsConstructor;
import spark.utils.StringUtils;

@RequiredArgsConstructor
public class AccountsRestController {

	private final AccountsService accountsService;


	public AccountInfoResponse get(String accountId) {
		if(StringUtils.isBlank(accountId)) {
			throw new InvalidRequestException("Invalid request");
		}
		return convert(accountsService.get(accountId));
	}

	private AccountInfoResponse convert(AccountInfo accountInfo) {
		AccountInfoResponse accountInfoResponse = new AccountInfoResponse();
		accountInfoResponse.setAccountId(accountInfo.getAccountId());
		accountInfoResponse.setAmount(accountInfo.getAmount());
		accountInfoResponse.setOwner(accountInfo.getOwner());
		return accountInfoResponse;
	}

	public String create(CreateAccountRequest request) {
		if (request == null || request.getAmount()<0 || StringUtils.isBlank(request.getOwner())) {
            throw new InvalidRequestException("Invalid request");
        }
		return accountsService.createAccount(request.getOwner(),request.getAmount());

	}

}
