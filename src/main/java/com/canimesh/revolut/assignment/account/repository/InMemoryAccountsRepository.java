package com.canimesh.revolut.assignment.account.repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.canimesh.revolut.assignment.account.repository.vo.AccountDetails;


public class InMemoryAccountsRepository implements AccountsRespository {

	private final ConcurrentMap<String, AccountsEntity> accountRepo;
	
	public InMemoryAccountsRepository() {
		accountRepo = new ConcurrentHashMap<>();
	}
	
	@Override
	public Optional<Account> get(String accountId) {
		if(accountRepo.containsKey(accountId)) {
			return Optional.of(RepositoryMapperUtils.convert(accountRepo.get(accountId)));
		}else {
			return Optional.empty();
		}
	}

	@Override
	public String create(AccountDetails account) {
		String newId = UUID.randomUUID().toString();
		AccountsEntity newEntity = new AccountsEntity();
		newEntity.setId(newId);
		newEntity.setAmount(account.getBalance());
		newEntity.setOwner(account.getOwner());
		accountRepo.put(newId, newEntity);
		return newId;
	}


	@Override
	public String save(Account account) {
		AccountsEntity entity = createEntity(account);
		accountRepo.put(account.getId(), entity);
		return entity.getId();
	}

	private AccountsEntity createEntity(Account account) {
		AccountsEntity accountsEntity = new AccountsEntity();
		accountsEntity.setId(account.getId());
		accountsEntity.setOwner(account.getAccountDetails().getOwner());
		accountsEntity.setAmount(account.getAccountDetails().getBalance());
		return accountsEntity;
	}

	

}
