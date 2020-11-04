package security.util;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TokenManager {
	
	private static ConcurrentHashMap<String, Token> tokenMap = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, String> tokenAccountMap = new ConcurrentHashMap<>();

	public static String login(TokenAccountData accountData) {
		Token token = generateToken(accountData);
		String accountId = accountData.getAccountId();
		
		String oldToken = tokenAccountMap.get(accountId);
		if(oldToken != null) {
			tokenMap.remove(oldToken);
		}
		
		if(tokenAccountMap.contains(accountId)) 
			tokenMap.remove(tokenAccountMap.get(accountId)); 
		
		tokenAccountMap.put(accountId, token.getToken());
		tokenMap.put(token.getToken(), token);
		
		return token.getToken();
	}
	
	public static TokenAccountData getTokenAccountData(String token) {
		Token tokenData = tokenMap.get(token);
		if(tokenData == null)
			return null;
		
		TokenAccountData data = tokenData.getAccountData();
		return data;
	}
	
	
	public static boolean isTokenValid(String token) {
		return tokenMap.keySet().contains(token); 
	}
	
	private static Token generateToken(TokenAccountData accountData) {
		String tokenString = UUID.randomUUID().toString();
		boolean alreadyExists = true;
		while(alreadyExists) 
			if(!tokenMap.containsKey(tokenString))
				alreadyExists = false;
			else
				tokenString = UUID.randomUUID().toString();
		
		
		Token token = new Token();
		token.setToken(tokenString);
		token.setStartTime(LocalDateTime.now());
		token.setEndTime(token.getStartTime().plusHours(16));
		token.setAccountData(accountData);
		
		return token;
	}
	
}
