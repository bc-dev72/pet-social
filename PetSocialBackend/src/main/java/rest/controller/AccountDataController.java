package rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.controller.response.BasicResponse;

@RestController
@RequestMapping("/api")
public class AccountDataController {

	@GetMapping("/isvalid")
	public ResponseEntity<Object> isTokenValid() {
		//this will return a 401 status if token is not valid
		return ResponseEntity.ok(new BasicResponse("Good"));
	}
	
}
