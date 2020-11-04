package security.util;

import java.time.LocalDateTime;

public class Token {
	
	private String token;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private TokenAccountData accountData;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public TokenAccountData getAccountData() {
		return accountData;
	}
	public void setAccountData(TokenAccountData accountData) {
		this.accountData = accountData;
	}
}
