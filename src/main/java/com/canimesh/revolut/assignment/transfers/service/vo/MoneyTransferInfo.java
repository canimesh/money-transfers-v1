package com.canimesh.revolut.assignment.transfers.service.vo;

import lombok.Value;

@Value
public class MoneyTransferInfo {
	private String id;
    private String fromAccount;
    private String toAccount;
    private long amount;
}
