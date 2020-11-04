package rest.controller.response.posts;

import java.util.List;

public class PostResponse {
	
	private String postId;
	private String desc;
	private String image;
	private String dateString;

	private int voteCount;
	private int currentVote; //the signed in user vote
	private int totalFavorites;
	
	private List<PostCommentResponse> comments;

	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getVoteCount() {
		return voteCount;
	}
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
	public int getCurrentVote() {
		return currentVote;
	}
	public void setCurrentVote(int currentVote) {
		this.currentVote = currentVote;
	}
	public List<PostCommentResponse> getComments() {
		return comments;
	}
	public void setComments(List<PostCommentResponse> comments) {
		this.comments = comments;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public int getTotalFavorites() {
		return totalFavorites;
	}
	public void setTotalFavorites(int totalFavorites) {
		this.totalFavorites = totalFavorites;
	}
}
