package commons.model.account;

import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="AccountDetails")
public class AccountDetails {
	
	@Id
	private String id;
	
	private String accountId;
	
	private HashSet<String> followingList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public HashSet<String> getFollowingList() {
		return followingList;
	}
	public void setFollowingList(HashSet<String> followingList) {
		this.followingList = followingList;
	}
}
