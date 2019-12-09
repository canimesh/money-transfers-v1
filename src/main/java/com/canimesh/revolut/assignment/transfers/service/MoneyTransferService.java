package com.canimesh.revolut.assignment.transfers.service;

import com.canimesh.revolut.assignment.transfers.service.vo.MoneyTransferInfo;

public interface MoneyTransferService {

	String transfer(String fromString, String to, long money);

	MoneyTransferInfo get(String transferId);

}
