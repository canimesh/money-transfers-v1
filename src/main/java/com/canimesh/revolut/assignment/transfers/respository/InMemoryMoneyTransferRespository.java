package com.canimesh.revolut.assignment.transfers.respository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.canimesh.revolut.assignment.transfers.respository.vo.MoneyTransferDetails;

public class InMemoryMoneyTransferRespository implements MoneyTransferRepository {

	private final ConcurrentMap<String, MoneyTransferEntity> moneyTransferRepo;

	public InMemoryMoneyTransferRespository() {
		super();
		moneyTransferRepo = new ConcurrentHashMap<>();
	}

	@Override
	public Optional<MoneyTransfer> get(String transferId) {

		if (moneyTransferRepo.containsKey(transferId)) {
			return Optional.of(RepositoryMapperUtils.convert(moneyTransferRepo.get(transferId)));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public String create(MoneyTransferDetails moneyTransferDetails) {
		String newId = UUID.randomUUID().toString();
		MoneyTransferEntity moneyTransferEntity = new MoneyTransferEntity();
		moneyTransferEntity.setId(newId);
		moneyTransferEntity.setFromAccount(moneyTransferDetails.getFromAccount());
		moneyTransferEntity.setToAccount(moneyTransferDetails.getToAccount());
		moneyTransferEntity.setAmount(moneyTransferDetails.getAmount());
		moneyTransferRepo.put(newId, moneyTransferEntity);
		return newId;
	}

}
