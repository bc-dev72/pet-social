package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import commons.model.post.Post;
import commons.model.post.PostComment;
import commons.model.post.PostData;
import repo.PostDataRepo;
import repo.PostRepo;
import rest.controller.request.posts.PostCommentRequest;
import rest.controller.request.posts.PostFavoriteRequest;
import rest.controller.request.posts.PostRequest;
import rest.controller.request.posts.PostVoteRequest;
import rest.controller.response.posts.PostCommentResponse;
import rest.controller.response.posts.PostResponse;
import security.util.TokenAccountData;
import service.error.NotFoundError;
import service.error.ServiceError;
import util.thread.UpdaterProtection;

@Service
public class PostService {
	
	@Autowired
	private PostDataRepo postDataRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	
	public PostResponse createPost(TokenAccountData tokenData, PostRequest request) throws ServiceError{
		if(request.getDesc() == null || request.getImage() == null)
			throw new ServiceError("Fill in all fields");
		
		if(request.getDesc().trim().length() > 200)
			throw new ServiceError("Please make a shorter description");
		
		Post post = new Post();
		post.setAccountId(tokenData.getAccountId());
		post.setDescription(request.getDesc().trim());
		post.setPostTime(LocalDateTime.now());
		post.setImage(request.getImage());
		post.setPostId(UUID.randomUUID().toString());
		post.setUserPosted(tokenData.getUsername());
		
		PostData postData = new PostData();
		postData.setComments(new ArrayList<>());
		postData.setFavorites(new HashSet<>());
		postData.setPostId(post.getPostId());
		postData.setVotes(new HashMap<>());
		
		postDataRepo.save(postData);
		postRepo.save(post);
		
		return this.getPostResponse(tokenData.getAccountId(), post, postData);
	}
	
	public PostResponse getPost(TokenAccountData tokenData, String postId) throws ServiceError, NotFoundError {
		if(tokenData == null) {
			tokenData = new TokenAccountData();
			tokenData.setAccountId("");
		}
		
		Post post = postRepo.findByPostId(postId);
		PostData postData = this.getPostData(postId);
		
		if(post == null)
			throw new NotFoundError("Not found");
		return this.getPostResponse(tokenData.getAccountId(), post, postData);
	}
	
	public PostResponse votePost(TokenAccountData tokenData, String postId, PostVoteRequest request) throws ServiceError, NotFoundError {
		getAccess(postId);
		
		Post post = postRepo.findByPostId(postId);
		PostData postData = this.getPostData(postId);
		
		if(post == null) {
			UpdaterProtection.requestRelease("POST", postId, true);
			throw new NotFoundError("Not found");
		}
		
		int vote = request.getValue();
		if(vote < 0)
			vote = -1;
		if(vote > 0)
			vote = 1;
		
		postData.getVotes().put(tokenData.getAccountId(), request.getValue());
		
		postDataRepo.save(postData);
		UpdaterProtection.requestRelease("POST", postId, true);
		return this.getPostResponse(tokenData.getAccountId(), post, postData);
	}
	
	public PostResponse makeComment(TokenAccountData tokenData, String postId, PostCommentRequest request) throws ServiceError, NotFoundError{
		getAccess(postId);
		
		if(request.getComment() == null || request.getComment().trim().length() == 0)
			throw new ServiceError("Invalid comment");
		String cleanedComment = request.getComment().trim();
		
		if(cleanedComment.length() > 200) {
			throw new ServiceError("Comment too long");
		}
		
		Post post = postRepo.findByPostId(postId);
		PostData postData = this.getPostData(postId);
		if(post == null) {
			UpdaterProtection.requestRelease("POST", postId, true);
			throw new NotFoundError("Not found");
		}
		
		PostComment comment = new PostComment();
		comment.setComment(cleanedComment);
		comment.setTime(LocalDateTime.now());
		comment.setUser(tokenData.getUsername());
		
		postData.getComments().add(comment);
		
		postDataRepo.save(postData);
		UpdaterProtection.requestRelease("POST", postId, true);
		return this.getPostResponse(tokenData.getAccountId(), post, postData);
	}
	
	public PostResponse favoritePost(TokenAccountData tokenData, String postId, PostFavoriteRequest request) throws ServiceError, NotFoundError {
		getAccess(postId);
		
		Post post = postRepo.findByPostId(postId);
		PostData postData = this.getPostData(postId);
		if(post == null) {
			UpdaterProtection.requestRelease("POST", postId, true);
			throw new NotFoundError("Not found");
		}
		
		if(request.isFavorite()) 
			postData.getFavorites().add(tokenData.getAccountId());
		else 
			postData.getFavorites().remove(tokenData.getAccountId());

		postDataRepo.save(postData);
		UpdaterProtection.requestRelease("POST", postId, true);
		return this.getPostResponse(tokenData.getAccountId(), post, postData);
	}
	
	
	private PostResponse getPostResponse(String accountId, Post post, PostData postData) {
		PostResponse response = new PostResponse();
		response.setDesc(post.getDescription());
		response.setImage(post.getImage());
		response.setPostId(post.getPostId());
		response.setDateString(post.getPostTime().getMonthValue()+"/"+post.getPostTime().getDayOfMonth()+"/"+post.getPostTime().getYear());
		
		List<PostCommentResponse> comments = new ArrayList<>();
		for(PostComment comment : postData.getComments()) {
			PostCommentResponse commentResponse = new PostCommentResponse();
			commentResponse.setComment(comment.getComment());
			commentResponse.setDateString(comment.getTime().getMonthValue()+"/"+comment.getTime().getDayOfMonth()+"/"+comment.getTime().getYear());
			commentResponse.setUser(comment.getUser());
			comments.add(commentResponse);
		}
		response.setComments(comments);
		
		int totalVotes = 0;
		for(String s : postData.getVotes().keySet()) {
			totalVotes+=postData.getVotes().get(s);
		}
		
		int userVote = 0;
		if(postData.getVotes().containsKey(accountId))
			userVote = postData.getVotes().get(accountId);
		
		int favoriteCount = postData.getFavorites().size();
			
		response.setVoteCount(totalVotes);
		response.setCurrentVote(userVote);
		response.setTotalFavorites(favoriteCount);
		
		return response;
	}
	
	private void getAccess(String postId) {
		boolean waiting = true;
		while(waiting) {
			waiting = UpdaterProtection.requestRelease("POST", postId, false);
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
	}
	
	private PostData getPostData(String postId) {
		PostData postData = postDataRepo.findByPostId(postId);
		if(postData == null) {
			postData = new PostData();
			postData.setComments(new ArrayList<>());
			postData.setFavorites(new HashSet<>());
			postData.setPostId(postId);
			postData.setVotes(new HashMap<>());
		}
		return postData;
	}

}
