package repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import commons.model.account.AccountData;

public interface AccountDataRepo extends MongoRepository<AccountData, String>{
	public AccountData findByEmail(String email);
	public AccountData findByUsername(String username);
	public boolean existsByUsername(String username);
}
