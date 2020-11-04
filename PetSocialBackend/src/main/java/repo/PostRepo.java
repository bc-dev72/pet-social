package repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import commons.model.post.Post;

public interface PostRepo extends MongoRepository<Post, String>{
	
	public Post findByPostId(String postId);

}
