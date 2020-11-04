package repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import commons.model.post.Post;

public interface PostRepo extends MongoRepository<Post, String>{
	
	public Post findByPostId(String postId);
	public List<Post> findByUserPosted(String userPosted);

}
