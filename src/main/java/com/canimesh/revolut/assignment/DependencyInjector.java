package com.canimesh.revolut.assignment;

import com.canimesh.revolut.assignment.account.repository.AccountsRespository;
import com.canimesh.revolut.assignment.account.repository.InMemoryAccountsRepository;
import com.canimesh.revolut.assignment.account.rest.AccountsRestController;
import com.canimesh.revolut.assignment.account.rest.AccountsRestHandler;
import com.canimesh.revolut.assignment.account.service.AccountsService;
import com.canimesh.revolut.assignment.account.service.DefaultAccountService;
import com.canimesh.revolut.assignment.transfers.respository.InMemoryMoneyTransferRespository;
import com.canimesh.revolut.assignment.transfers.respository.MoneyTransferRepository;
import com.canimesh.revolut.assignment.transfers.rest.MoneyTransfersRestController;
import com.canimesh.revolut.assignment.transfers.rest.TransfersRestHandler;
import com.canimesh.revolut.assignment.transfers.service.DefaultMoneyTransferService;
import com.canimesh.revolut.assignment.transfers.service.MoneyTransferService;

import lombok.Getter;

@Getter
public class DependencyInjector {

	private AccountsRestHandler accountsRestHandler;

	private TransfersRestHandler transfersRestHandler;

	private final static DependencyInjector INSTANCE = new DependencyInjector();

	static DependencyInjector instance() {
		return INSTANCE;
	}

	private DependencyInjector() {
		setUp();
	}

	private void setUp() {
		
		AccountsRespository accountsRespository = new InMemoryAccountsRepository();
		AccountsService accountsService = new DefaultAccountService(accountsRespository);
		AccountsRestController accountsRestController = new AccountsRestController(accountsService );
		this.accountsRestHandler = new AccountsRestHandler(accountsRestController );

		MoneyTransferRepository moneyTransferRepository = new InMemoryMoneyTransferRespository();
		MoneyTransferService moneyTransferService = new DefaultMoneyTransferService(moneyTransferRepository, accountsService);
		MoneyTransfersRestController transfersRestController = new MoneyTransfersRestController(moneyTransferService);
		this.transfersRestHandler = new TransfersRestHandler(transfersRestController);

	}
	

}
