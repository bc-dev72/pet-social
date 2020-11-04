package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import rest.controller.request.posts.PostCommentRequest;
import rest.controller.request.posts.PostFavoriteRequest;
import rest.controller.request.posts.PostRequest;
import rest.controller.request.posts.PostVoteRequest;
import rest.controller.response.BasicResponse;
import security.util.TokenAccountData;
import security.util.TokenManager;
import service.PostService;
import service.error.NotFoundError;
import service.error.ServiceError;
import util.GeneralUtil;

@RestController
public class PostController {
	
	@Autowired
	private PostService postService;

	@GetMapping("/post/{postId}")
	public ResponseEntity<Object> getPost(@RequestHeader(value="Authorization", required=false) String authHeader, @PathVariable("postId") String postId) {
		TokenAccountData tokenData = null;
		if(authHeader != null) 
			tokenData = TokenManager.getTokenAccountData(GeneralUtil.cleanAuthHeader(authHeader));
		
		try {
			return ResponseEntity.ok(postService.getPost(tokenData, postId));
		} catch(ServiceError error) {
			return ResponseEntity.badRequest().body(new BasicResponse(error.getErrorMessage()));
		} catch(NotFoundError error) {
			return ResponseEntity.status(404).body(new BasicResponse(error.getErrorMessage()));
		}
	}

	@PostMapping("/api/post/{postId}/vote")
	public ResponseEntity<Object> vote(@RequestHeader(value="Authorization") String authHeader, @PathVariable("postId") String postId, @RequestBody PostVoteRequest request) {
		TokenAccountData tokenData = TokenManager.getTokenAccountData(GeneralUtil.cleanAuthHeader(authHeader));
		try {
			return ResponseEntity.ok(postService.votePost(tokenData, postId, request));
		} catch(ServiceError error) {
			return ResponseEntity.badRequest().body(new BasicResponse(error.getErrorMessage()));
		} catch(NotFoundError error) {
			return ResponseEntity.status(404).body(new BasicResponse(error.getErrorMessage()));
		}
	}
	
	@PostMapping("/api/post/{postId}/comment")
	public ResponseEntity<Object> comment(@RequestHeader(value="Authorization") String authHeader, @PathVariable("postId") String postId, @RequestBody PostCommentRequest request) {
		TokenAccountData tokenData = TokenManager.getTokenAccountData(GeneralUtil.cleanAuthHeader(authHeader));
		try {
			return ResponseEntity.ok(postService.makeComment(tokenData, postId, request));
		} catch(ServiceError error) {
			return ResponseEntity.badRequest().body(new BasicResponse(error.getErrorMessage()));
		} catch(NotFoundError error) {
			return ResponseEntity.status(404).body(new BasicResponse(error.getErrorMessage()));
		}
	}
	
	@PostMapping("/api/post/{postId}/favorite")
	public ResponseEntity<Object> favorite(@RequestHeader(value="Authorization") String authHeader, @PathVariable("postId") String postId, @RequestBody PostFavoriteRequest request) {
		TokenAccountData tokenData = TokenManager.getTokenAccountData(GeneralUtil.cleanAuthHeader(authHeader));
		try {
			return ResponseEntity.ok(postService.favoritePost(tokenData, postId, request));
		} catch(ServiceError error) {
			return ResponseEntity.badRequest().body(new BasicResponse(error.getErrorMessage()));
		} catch(NotFoundError error) {
			return ResponseEntity.status(404).body(new BasicResponse(error.getErrorMessage()));
		}
	}
	
	@PostMapping("/api/createpost")
	public ResponseEntity<Object> favorite(@RequestHeader(value="Authorization") String authHeader, @RequestBody PostRequest request) {
		TokenAccountData tokenData = TokenManager.getTokenAccountData(GeneralUtil.cleanAuthHeader(authHeader));
		try {
			return ResponseEntity.ok(postService.createPost(tokenData, request));
		} catch(ServiceError error) {
			return ResponseEntity.badRequest().body(new BasicResponse(error.getErrorMessage()));
		}
	}
	
	
}
