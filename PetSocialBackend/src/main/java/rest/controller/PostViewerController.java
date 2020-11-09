package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import security.util.TokenAccountData;
import security.util.TokenManager;
import service.PostViewerService;
import util.GeneralUtil;

@RestController
@RequestMapping("/public")
public class PostViewerController {
	
	@Autowired
	private PostViewerService postViewerService;
	
	@GetMapping("/home")
	public ResponseEntity<Object> getPosts(@RequestHeader(value="Authorization", required=false) String authHeader, @RequestParam("pageNumber") int pageNumber,  @RequestParam(value="lastPost", required=false) String lastPost) {
		TokenAccountData tokenData = null;
		if(authHeader != null) 
			tokenData = TokenManager.getTokenAccountData(GeneralUtil.cleanAuthHeader(authHeader));
		return ResponseEntity.ok(postViewerService.getAllPosts(tokenData, pageNumber, lastPost));
	}
	
	@GetMapping("/profile")
	public ResponseEntity<Object> getPostsForProfile(@RequestHeader(value="Authorization", required=false) String authHeader, @RequestParam("username") String username, @RequestParam("pageNumber") int pageNumber,  @RequestParam(value="lastPost", required=false) String lastPost) {
		TokenAccountData tokenData = null;
		if(authHeader != null) 
			tokenData = TokenManager.getTokenAccountData(GeneralUtil.cleanAuthHeader(authHeader));
		return ResponseEntity.ok(postViewerService.getPostsForUser(tokenData, username, pageNumber, lastPost));
	}
	

}
