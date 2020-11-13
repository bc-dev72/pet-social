package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.controller.request.accounts.AccountUpdateRequest;
import rest.controller.response.BasicResponse;
import security.util.TokenAccountData;
import security.util.TokenManager;
import service.AccountDataService;
import service.error.ServiceError;
import util.GeneralUtil;

@RestController
@RequestMapping("/api")
public class AccountDataController {
	
	@Autowired
	private AccountDataService accountDataService;
	
	@GetMapping("/follow/{username}")
	public ResponseEntity<Object> followUser(@RequestHeader(value="Authorization") String authHeader, @PathVariable("username") String username) {
		TokenAccountData tokenData = TokenManager.getTokenAccountData(GeneralUtil.cleanAuthHeader(authHeader));
		try {
			return ResponseEntity.ok(accountDataService.followUser(tokenData, username));
		} catch (ServiceError e) {
			return ResponseEntity.badRequest().body(new BasicResponse(e.getErrorMessage()));
		}
	}
	
	@GetMapping("/unfollow/{username}")
	public ResponseEntity<Object> unfollowUser(@RequestHeader(value="Authorization") String authHeader, @PathVariable("username") String username) {
		TokenAccountData tokenData = TokenManager.getTokenAccountData(GeneralUtil.cleanAuthHeader(authHeader));
		try {
			return ResponseEntity.ok(accountDataService.unfollowUser(tokenData, username));
		} catch (ServiceError e) {
			return ResponseEntity.badRequest().body(new BasicResponse(e.getErrorMessage()));
		}
	}
	
	@PostMapping("/updateprofile")
	public ResponseEntity<Object> updateProfile(@RequestHeader(value="Authorization") String authHeader, @RequestBody AccountUpdateRequest updateRequest) {
		TokenAccountData tokenData = TokenManager.getTokenAccountData(GeneralUtil.cleanAuthHeader(authHeader));
		try {
			return ResponseEntity.ok(accountDataService.updateProfile(tokenData, updateRequest));
		} catch (ServiceError e) {
			return ResponseEntity.badRequest().body(new BasicResponse(e.getErrorMessage()));
		}
	}

	@GetMapping("/isvalid")
	public ResponseEntity<Object> isTokenValid() {
		//this will return a 401 status if token is not valid
		return ResponseEntity.ok(new BasicResponse("Good"));
	}
	
}
