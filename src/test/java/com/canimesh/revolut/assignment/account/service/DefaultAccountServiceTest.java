package com.canimesh.revolut.assignment.account.service;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.canimesh.revolut.assignment.account.repository.Account;
import com.canimesh.revolut.assignment.account.repository.AccountsRespository;
import com.canimesh.revolut.assignment.account.repository.vo.AccountDetails;
import com.canimesh.revolut.assignment.account.service.vo.AccountInfo;
import com.canimesh.revolut.assignment.exception.NotfoundException;

public class DefaultAccountServiceTest {

	private AccountsService accountService;
	@Mock
	private AccountsRespository accountsRespository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		accountService = new DefaultAccountService(accountsRespository);
	}
	
	@Test
	public void test_get_success() {
		Account account = new Account();
		account.setAccountDetails(new AccountDetails("owner", 200));
		Mockito.when(accountsRespository.get("123")).thenReturn(Optional.of(account));
		AccountInfo accountInfo = accountService.get("123");
		assertEquals(200, accountInfo.getAmount());
		assertEquals("owner", accountInfo.getOwner());
	}
	
	@Test(expected = NotfoundException.class)
	public void test_get_fail_account_not_found() {
		Mockito.when(accountsRespository.get("123")).thenReturn(Optional.empty());
		accountService.get("123");
	}
	
	@Test
	public void test_create_account_success() {
		Mockito.when(accountsRespository.create(any(AccountDetails.class))).thenReturn("some_id");
		String accountId = accountService.createAccount("owner", 500);
		assertEquals("some_id", accountId);
		
	}
	
	@Test
	public void test_save_account_success() {
		Account account = new Account();
		Mockito.when(accountsRespository.get("123")).thenReturn(Optional.of(account));
		Mockito.when(accountsRespository.save(any(Account.class))).thenReturn("id");
		String accountId = accountService.save(new AccountInfo("123", "owner", 500));
		assertEquals("id", accountId);
		
	}
	
	
	

}
