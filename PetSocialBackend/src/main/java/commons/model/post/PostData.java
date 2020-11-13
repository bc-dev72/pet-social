package commons.model.post;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="PostData")
public class PostData {
	
	@Id
	private String id;
	
	private String postId;
	private LocalDateTime postTime;
	
	private List<PostComment> comments;
	private HashMap<String, Integer> votes;
	private HashSet<String> favorites;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public List<PostComment> getComments() {
		return comments;
	}
	public void setComments(List<PostComment> comments) {
		this.comments = comments;
	}
	public HashMap<String, Integer> getVotes() {
		return votes;
	}
	public void setVotes(HashMap<String, Integer> votes) {
		this.votes = votes;
	}
	public HashSet<String> getFavorites() {
		return favorites;
	}
	public void setFavorites(HashSet<String> favorites) {
		this.favorites = favorites;
	}
	public LocalDateTime getPostTime() {
		return postTime;
	}
	public void setPostTime(LocalDateTime postTime) {
		this.postTime = postTime;
	}
}
