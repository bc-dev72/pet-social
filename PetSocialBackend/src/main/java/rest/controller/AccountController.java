package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.controller.request.SignInRequest;
import rest.controller.request.SignUpRequest;
import rest.controller.response.BasicResponse;
import service.AccountService;
import service.error.ServiceError;

@RestController
@RequestMapping("/auth")
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	@PostMapping("/signup")
	public ResponseEntity<Object> signUp(@RequestBody SignUpRequest request) {
		try {
			return ResponseEntity.ok(accountService.signUp(request));
		} catch(ServiceError error) {
			return ResponseEntity.badRequest().body(new BasicResponse(error.getErrorMessage()));
		}
	}
	
	@PostMapping("/signin")
	public ResponseEntity<Object> signIn(@RequestBody SignInRequest request) {
		try {
			return ResponseEntity.ok(accountService.signIn(request));
		} catch(ServiceError error) {
			return ResponseEntity.badRequest().body(new BasicResponse(error.getErrorMessage()));
		}
	} 
}
