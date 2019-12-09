package com.canimesh.revolut.assignment.transfers.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.canimesh.revolut.assignment.account.service.AccountsService;
import com.canimesh.revolut.assignment.account.service.vo.AccountInfo;
import com.canimesh.revolut.assignment.exception.InsufficientFundsException;
import com.canimesh.revolut.assignment.exception.InvalidRequestException;
import com.canimesh.revolut.assignment.exception.NotfoundException;
import com.canimesh.revolut.assignment.transfers.respository.MoneyTransfer;
import com.canimesh.revolut.assignment.transfers.respository.MoneyTransferRepository;
import com.canimesh.revolut.assignment.transfers.respository.vo.MoneyTransferDetails;

public class DefaultMoneyTransferServiceTest {

	private MoneyTransferService moneyTransferService;
	@Mock
	private MoneyTransferRepository moneyTransferRepository;
	@Mock
	private AccountsService accountsService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		moneyTransferService = new DefaultMoneyTransferService(moneyTransferRepository, accountsService);
	}

	@Test
	public void test_transfer_success() {
		String fromId = "123";
		String toId = "456";
		
		
		
		Mockito.when(accountsService.get(fromId)).thenReturn(new AccountInfo(fromId,"fromOwner",500));
		Mockito.when(accountsService.get(toId)).thenReturn(new AccountInfo(toId,"toOwner",200));
		
		Mockito.when(moneyTransferRepository.create(any(MoneyTransferDetails.class))).thenReturn("transferId");
		
		String transferId = moneyTransferService.transfer(fromId, toId, 10);
		ArgumentCaptor<AccountInfo> accountInfoCaptor = ArgumentCaptor.forClass(AccountInfo.class);
		Mockito.verify(accountsService, times(2)).save(accountInfoCaptor.capture());
		
		assertEquals(490,accountInfoCaptor.getAllValues().stream().filter((e)-> e.getAccountId().equalsIgnoreCase(fromId)).findFirst().get().getAmount());
		
		assertEquals(210,accountInfoCaptor.getAllValues().stream().filter((e)-> e.getAccountId().equalsIgnoreCase(toId)).findFirst().get().getAmount());
		
		assertEquals("transferId", transferId);
	}
	
	
	@Test(expected = InsufficientFundsException.class)
	public void test_transfer_failure_insufficient_funds() {
		String fromId = "123";
		String toId = "456";
		
		MoneyTransfer moneyTransfer = new MoneyTransfer();
		moneyTransfer.setTransferDetails(new MoneyTransferDetails("from", "to", 100));
		Mockito.when(moneyTransferRepository.get(anyString())).thenReturn(Optional.of(moneyTransfer));
		
		Mockito.when(accountsService.get(fromId)).thenReturn(new AccountInfo(fromId,"fromOwner",100));
		Mockito.when(accountsService.get(toId)).thenReturn(new AccountInfo(toId,"toOwner",200));
		
		Mockito.when(moneyTransferRepository.create(any(MoneyTransferDetails.class))).thenReturn("transferId");
		
		moneyTransferService.transfer(fromId, toId, 5000);
		

	}
	
	@Test(expected = InvalidRequestException.class)
	public void test_transfer_failure_transfer_to_self() {
		String fromId = "123";
		String toId = "123";
		
		moneyTransferService.transfer(fromId, toId, 5000);
		

	}
	
	@Test
	public void test_rollback_debit_update_fail() {
		String fromId = "123";
		String toId = "456";
		
		MoneyTransfer moneyTransfer = new MoneyTransfer();
		moneyTransfer.setTransferDetails(new MoneyTransferDetails("from", "to", 100));
		Mockito.when(moneyTransferRepository.get(anyString())).thenReturn(Optional.of(moneyTransfer));
		
		AccountInfo debited = new AccountInfo(fromId,"fromOwner",500);
		Mockito.when(accountsService.get(fromId)).thenReturn(debited);
		AccountInfo credited = new AccountInfo(toId,"toOwner",200);
		Mockito.when(accountsService.get(toId)).thenReturn(credited);
		
		Mockito.when(moneyTransferRepository.create(any(MoneyTransferDetails.class))).thenReturn("transferId");
		AccountInfo debAccInfo = new AccountInfo(fromId, "fromOwner", 400);
		Mockito.when(accountsService.save(debAccInfo)).thenThrow(new IllegalStateException());
		moneyTransferService.transfer(fromId, toId, 100);
		Mockito.verify(accountsService, times(3)).save(any(AccountInfo.class));

	}
	
	
	@Test
	public void test_rollback_credit_update_fail() {
		String fromId = "123";
		String toId = "456";
		
		MoneyTransfer moneyTransfer = new MoneyTransfer();
		moneyTransfer.setTransferDetails(new MoneyTransferDetails("from", "to", 100));
		Mockito.when(moneyTransferRepository.get(anyString())).thenReturn(Optional.of(moneyTransfer));
		
		AccountInfo debited = new AccountInfo(fromId,"fromOwner",500);
		Mockito.when(accountsService.get(fromId)).thenReturn(debited);
		AccountInfo credited = new AccountInfo(toId,"toOwner",200);
		Mockito.when(accountsService.get(toId)).thenReturn(credited);
		
		Mockito.when(moneyTransferRepository.create(any(MoneyTransferDetails.class))).thenReturn("transferId");
		AccountInfo credAccInfo = new AccountInfo(toId, "toOwner", 300);
		Mockito.when(accountsService.save(credAccInfo)).thenThrow(new IllegalStateException());
		moneyTransferService.transfer(fromId, toId, 100);
		Mockito.verify(accountsService, times(4)).save(any(AccountInfo.class));

	}
	
	
	@Test(expected = NotfoundException.class)
	public void test_get_transfer_id_not_found() {
		Mockito.when(moneyTransferRepository.get(anyString())).thenReturn(Optional.empty());
		moneyTransferService.get("abcd");
	}
}
