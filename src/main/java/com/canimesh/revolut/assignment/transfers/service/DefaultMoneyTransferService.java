package com.canimesh.revolut.assignment.transfers.service;

import com.canimesh.revolut.assignment.account.service.AccountsService;
import com.canimesh.revolut.assignment.account.service.vo.AccountInfo;
import com.canimesh.revolut.assignment.exception.ApplicationRuntimeException;
import com.canimesh.revolut.assignment.exception.InsufficientFundsException;
import com.canimesh.revolut.assignment.exception.InvalidRequestException;
import com.canimesh.revolut.assignment.exception.NotfoundException;
import com.canimesh.revolut.assignment.transfers.respository.MoneyTransfer;
import com.canimesh.revolut.assignment.transfers.respository.MoneyTransferRepository;
import com.canimesh.revolut.assignment.transfers.respository.vo.MoneyTransferDetails;
import com.canimesh.revolut.assignment.transfers.service.vo.MoneyTransferInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultMoneyTransferService implements MoneyTransferService {

	private final MoneyTransferRepository moneyTransferRepository;

	private final AccountsService accountsService;

	@Override
	public MoneyTransferInfo get(String transferId) {
		MoneyTransfer transferDetails = moneyTransferRepository.get(transferId).orElseThrow(() -> {
			return new NotfoundException("Transfer details not found.");
		});
		return convert(transferDetails);
	}

	private MoneyTransferInfo convert(MoneyTransfer transferDetails) {
		MoneyTransferInfo moneyTransferInfo = new MoneyTransferInfo(transferDetails.getId(),
				transferDetails.getTransferDetails().getFromAccount(),
				transferDetails.getTransferDetails().getToAccount(), transferDetails.getTransferDetails().getAmount());
		return moneyTransferInfo;
	}

	@Override
	public String transfer(String from, String to, long amount) {
		if(from.equalsIgnoreCase(to)) {
			throw new InvalidRequestException("Cannot transfer to self");
		}
		AccountInfo fromAccount = accountsService.get(from);
		AccountInfo toAccount = accountsService.get(to);
		synchronized (fromAccount) {
			this.transfer(fromAccount, toAccount, amount);
			return moneyTransferRepository.create(new MoneyTransferDetails(from, to, amount));
		}

	}

	private void transfer(AccountInfo fromAccount, AccountInfo toAccount, long amount) {
		if (fromAccount.getAmount() < amount) {
			throw new InsufficientFundsException("Cannot transfer more than the account balance");
		}

		executeTransfer(fromAccount, toAccount, amount);

	}

	private void executeTransfer(AccountInfo fromAccount, AccountInfo toAccount, long amount) {
		AccountInfo debitedAccount = new AccountInfo(fromAccount.getAccountId(), fromAccount.getOwner(),
				fromAccount.getAmount() - amount);
		AccountInfo creditedAccount = new AccountInfo(toAccount.getAccountId(), toAccount.getOwner(),
				toAccount.getAmount() + amount);
		try {
			accountsService.save(debitedAccount);
			accountsService.save(creditedAccount);
		} catch (Exception e) {
			rollback(fromAccount);
			rollback(toAccount);
		}

	}

	private void rollback(AccountInfo account) {
		accountsService.save(account);
	}

}
