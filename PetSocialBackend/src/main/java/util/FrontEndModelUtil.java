package util;

import java.util.ArrayList;
import java.util.List;

import commons.model.post.Post;
import commons.model.post.PostComment;
import commons.model.post.PostData;
import rest.controller.response.posts.PostCommentResponse;
import rest.controller.response.posts.PostResponse;

public class FrontEndModelUtil {

	
	public static PostResponse createPostResponse(String accountId, Post post, PostData postData, boolean isFollowed) {
		PostResponse response = new PostResponse();
		response.setDesc(post.getDescription());
		response.setImage(post.getImage());
		response.setPostId(post.getPostId());
		response.setDateString(post.getPostTime().getMonthValue()+"/"+post.getPostTime().getDayOfMonth()+"/"+post.getPostTime().getYear());
		response.setUsername(post.getUserPosted());
		
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
		boolean favorited = postData.getFavorites().contains(accountId);
			
		response.setVoteCount(totalVotes);
		response.setCurrentVote(userVote);
		response.setTotalFavorites(favoriteCount);
		response.setFavorited(favorited);
		
		response.setUserPosted(post.getAccountId().equals(accountId));
		response.setFollowing(isFollowed);
		
		return response;
	}
	
}
