package repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import commons.model.post.PostData;

public interface PostDataRepo extends MongoRepository<PostData, String>{
	public PostData findByPostId(String postId);
}
