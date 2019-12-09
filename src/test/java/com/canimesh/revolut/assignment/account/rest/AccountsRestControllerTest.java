package com.canimesh.revolut.assignment.account.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.canimesh.revolut.assignment.account.rest.dto.AccountInfoResponse;
import com.canimesh.revolut.assignment.account.rest.dto.CreateAccountRequest;
import com.canimesh.revolut.assignment.account.service.AccountsService;
import com.canimesh.revolut.assignment.account.service.vo.AccountInfo;
import com.canimesh.revolut.assignment.exception.ApplicationRuntimeException;
import com.canimesh.revolut.assignment.exception.InvalidRequestException;

public class AccountsRestControllerTest {

	private AccountsRestController accountsRestController;
	@Mock
	private AccountsService accoutsService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		accountsRestController = new AccountsRestController(accoutsService);
	}
	
	@Test(expected = ApplicationRuntimeException.class)
	public void test_get_error_from_service() {
		
		Mockito.when(accoutsService.get("account_id")).thenThrow(new ApplicationRuntimeException("dummy"));
		accountsRestController.get("account_id");
	}
	
	@Test(expected = InvalidRequestException.class)
	public void test_get_error_empty_account_id() {
		
		accountsRestController.get("		");
	}
	
	@Test
	public void test_get_success() {
		Mockito.when(accoutsService.get("account_id")).thenReturn(new AccountInfo("accid", "owner", 500));
		AccountInfoResponse accountInfoResponse = accountsRestController.get("account_id");
		assertEquals("accid", accountInfoResponse.getAccountId());
		assertEquals("owner", accountInfoResponse.getOwner());
		assertEquals(500, accountInfoResponse.getAmount());
	}
	
	@Test
	public void test_create_success() {
		Mockito.when(accoutsService.createAccount("owner", 500)).thenReturn("some_id");
		String id = accountsRestController.create(new CreateAccountRequest(500, "owner"));
		assertEquals("some_id",id);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void test_create_error_empty_owner() {
		accountsRestController.create(new CreateAccountRequest(0, ""));
	}
	
	@Test(expected = InvalidRequestException.class)
	public void test_create_error_null_input() {
		accountsRestController.create(null);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void test_create_error_balance_less_than_zero() {
		accountsRestController.create(new CreateAccountRequest(-100, "owner"));
	}

}
