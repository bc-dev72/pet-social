package repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import commons.model.account.AccountDetails;

public interface AccountDetailsRepo extends MongoRepository<AccountDetails, String>{
	public AccountDetails findByAccountId(String accountId);
	public Long countByFollowingList(String followingList);
}
