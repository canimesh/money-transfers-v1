package com.canimesh.revolut.assignment.transfers.respository;

import com.canimesh.revolut.assignment.transfers.respository.vo.MoneyTransferDetails;

class RepositoryMapperUtils {

	static MoneyTransfer convert(MoneyTransferEntity moneyTransferEntity) {
		
		MoneyTransfer moneyTransfer = new MoneyTransfer();
		moneyTransfer.setId(moneyTransferEntity.getId());
		moneyTransfer.setTransferDetails(new MoneyTransferDetails(moneyTransferEntity.getFromAccount(), moneyTransferEntity.getToAccount(), moneyTransferEntity.getAmount()));
		return moneyTransfer;
	}

}
