package com.canimesh.revolut.assignment.transfers.respository;

import com.canimesh.revolut.assignment.transfers.respository.vo.MoneyTransferDetails;


public class MoneyTransfer {
	private String id;
	private MoneyTransferDetails transferDetails;
	
	void setId(String id) {
		this.id = id;
	}

	public MoneyTransferDetails getTransferDetails() {
		return transferDetails;
	}

	public void setTransferDetails(MoneyTransferDetails transferDetails) {
		this.transferDetails = transferDetails;
	}

	public String getId() {
		return id;
	}
	
}
