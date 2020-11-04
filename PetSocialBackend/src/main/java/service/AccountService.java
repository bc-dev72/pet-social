package service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import commons.model.account.AccountData;
import commons.model.account.AccountDetails;
import repo.AccountDataRepo;
import repo.AccountDetailsRepo;
import rest.controller.request.SignInRequest;
import rest.controller.request.SignUpRequest;
import rest.controller.response.SignInResponse;
import security.util.TokenAccountData;
import security.util.TokenManager;
import service.error.ServiceError;
import util.GeneralUtil;

@Service
public class AccountService {

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private AccountDataRepo accountDataRepo;
	
	@Autowired
	private AccountDetailsRepo accountDetailsRepo;
	
	public SignInResponse signUp(SignUpRequest request) throws ServiceError {
		//check inputs
		if(request == null ||request.getEmail() == null || request.getUsername() == null || request.getPassword() == null)
			throw new ServiceError("Please fill in all fields");
		
		String cleanedEmail = request.getEmail().toLowerCase().trim();
		String cleanedUsername = request.getUsername().toLowerCase().trim();
		
		if(!GeneralUtil.isValidEmailAddress(cleanedEmail))
			throw new ServiceError("Please enter a valid email");
		
		if(request.getPassword().length() < 8)
			throw new ServiceError("Password too short");
		
		if(accountDataRepo.findByEmail(cleanedEmail) != null)
			throw new ServiceError("Email is already in use");
		if(accountDataRepo.findByUsername(cleanedUsername) != null)
			throw new ServiceError("Username is already taken"); 
		
		AccountData data = new AccountData();
		data.setAccountId(UUID.randomUUID().toString());
		data.setCreateTime(LocalDateTime.now());
		data.setEmail(cleanedEmail);
		data.setPassword(encoder.encode(request.getPassword()));
		data.setUsername(cleanedUsername);
		
		AccountDetails accountDetails = new AccountDetails();
		accountDetails.setAccountId(data.getAccountId());
		accountDetails.setFollowingList(new HashSet<>());
		
		accountDataRepo.save(data);
		accountDetailsRepo.save(accountDetails);
		
		return this.signIn(data);
	}
	
	public SignInResponse signIn(SignInRequest request) throws ServiceError {
		if(request.getEmail() == null || request.getPassword() == null)
			throw new ServiceError("Please fill in all fields");
		
		String cleanedEmail = request.getEmail().toLowerCase().trim();
		
		AccountData data = accountDataRepo.findByEmail(cleanedEmail);
		if(data == null || !encoder.matches(request.getPassword(), data.getPassword()))
			throw new ServiceError("Email or password is incorrect");

		return signIn(data);
	}
	
	private SignInResponse signIn(AccountData data) {
		
		TokenAccountData tokenData = new TokenAccountData();
		tokenData.setAccountId(data.getAccountId());
		String token = TokenManager.login(tokenData);

		SignInResponse response = new SignInResponse();
		response.setToken(token);
		return response;
	}
	
}
