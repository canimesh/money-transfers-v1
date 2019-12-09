package com.canimesh.revolut.assignment.transfers.respository.vo;

import lombok.Value;

@Value
public class MoneyTransferDetails {

	private String fromAccount;
	private String toAccount;
	private long amount;
}
