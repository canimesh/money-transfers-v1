package com.canimesh.revolut.assignment.transfers.rest;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.canimesh.revolut.assignment.exception.InvalidRequestException;
import com.canimesh.revolut.assignment.transfers.rest.dto.TransferDetailsResponse;
import com.canimesh.revolut.assignment.transfers.rest.dto.TransferRequest;
import com.canimesh.revolut.assignment.transfers.service.MoneyTransferService;
import com.canimesh.revolut.assignment.transfers.service.vo.MoneyTransferInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MoneyTransfersRestController {

	private final MoneyTransferService moneyTransferService;

	public String transfer(TransferRequest transferRequest) {
		if(Objects.isNull(transferRequest) || transferRequest.getAmount() <= 0 || StringUtils.isBlank(transferRequest.getFromAccount()) || StringUtils.isBlank(transferRequest.getToAccount())) {
        	throw new InvalidRequestException("Invalid request");
        }
		return moneyTransferService.transfer(transferRequest.getFromAccount(), transferRequest.getToAccount(), transferRequest.getAmount());
	}

	public TransferDetailsResponse get(String transferId) {
		if(StringUtils.isBlank(transferId)) {
			throw new InvalidRequestException("Invalid request");
		}
		MoneyTransferInfo moneyTransfer = moneyTransferService.get(transferId);
		return convert(moneyTransfer);
	}

	private TransferDetailsResponse convert(MoneyTransferInfo moneyTransfer) {
		TransferDetailsResponse transferDetailsResponse = new TransferDetailsResponse();
		transferDetailsResponse.setFromAccount(moneyTransfer.getFromAccount());
		transferDetailsResponse.setToAccount(moneyTransfer.getToAccount());
		transferDetailsResponse.setAmount(moneyTransfer.getAmount());
		transferDetailsResponse.setId(moneyTransfer.getId());
		return transferDetailsResponse;
	}



}
