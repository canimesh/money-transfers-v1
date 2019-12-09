package com.canimesh.revolut.assignment.transfers.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.canimesh.revolut.assignment.exception.ApplicationRuntimeException;
import com.canimesh.revolut.assignment.exception.InvalidRequestException;
import com.canimesh.revolut.assignment.transfers.rest.dto.TransferDetailsResponse;
import com.canimesh.revolut.assignment.transfers.rest.dto.TransferRequest;
import com.canimesh.revolut.assignment.transfers.service.MoneyTransferService;
import com.canimesh.revolut.assignment.transfers.service.vo.MoneyTransferInfo;

public class MoneyTransfersRestControllerTest {

	private MoneyTransfersRestController moneyTransfersRestController;
	@Mock
	private MoneyTransferService moneyTransferService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		moneyTransfersRestController = new MoneyTransfersRestController(moneyTransferService);
	}
	
	@Test(expected =  InvalidRequestException.class)
	public void test_get_transfer_request_id_null() {
		moneyTransfersRestController.get(null);
	}
	
	@Test(expected =  InvalidRequestException.class)
	public void test_get_transfer_request_id_empty() {
		moneyTransfersRestController.get("		");
	}
	
	@Test(expected =  ApplicationRuntimeException.class)
	public void test_get_transfer_service_throws_exception() {
		Mockito.when(moneyTransferService.get(anyString())).thenThrow(new ApplicationRuntimeException("error"));
		moneyTransfersRestController.get("abc");
	}
	
	@Test
	public void test_get_success() {
		Mockito.when(moneyTransferService.get("abc")).thenReturn(new MoneyTransferInfo("123", "from", "to", 200));
		TransferDetailsResponse transferDetailsResponse = moneyTransfersRestController.get("abc");
		assertEquals("123", transferDetailsResponse.getId());
		assertEquals("from", transferDetailsResponse.getFromAccount());
		assertEquals("to", transferDetailsResponse.getToAccount());
		assertEquals(200, transferDetailsResponse.getAmount());
	}
	
	
	@Test
	public void test_create_transfer_success() {
		Mockito.when(moneyTransferService.transfer(anyString(), anyString(), anyLong())).thenReturn("transfer_id");
		String transferId = moneyTransfersRestController.transfer(new TransferRequest("from", "to", 100));;
		assertEquals("transfer_id", transferId);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void test_create_failure_empty_from() {
		moneyTransfersRestController.transfer(new TransferRequest("", "to", 100));;
	}
	
	@Test(expected = InvalidRequestException.class)
	public void test_create_failure_empty_to() {
		moneyTransfersRestController.transfer(new TransferRequest("from", "", 100));;
	}
	
	@Test(expected = InvalidRequestException.class)
	public void test_create_failure_negative_transfer_amount() {
		moneyTransfersRestController.transfer(new TransferRequest("from", "to", -100));;
	}
	
}
