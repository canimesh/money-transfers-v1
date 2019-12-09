package com.canimesh.revolut.assignment.transfers.respository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class MoneyTransferEntity {
	private String id;
	private String fromAccount;
	private String toAccount;
	private long amount;
}
