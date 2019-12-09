package com.canimesh.revolut.assignment.transfers.respository;

import java.util.Optional;

import com.canimesh.revolut.assignment.transfers.respository.vo.MoneyTransferDetails;

public interface MoneyTransferRepository {

	Optional<MoneyTransfer> get(String transferId);

	String create(MoneyTransferDetails moneyTransferDetails);

}
