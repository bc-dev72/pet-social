package repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import commons.model.post.PostData;

public interface PostDataRepo extends MongoRepository<PostData, String>{
	public PostData findByPostId(String postId);
	public List<PostData> findByPostIdIn(Set<String> postId);
	public Long countByFavorites(String favorites);
	
	public List<PostData> findByFavoritesOrderByPostTimeAsc(String favorites, Pageable pageable);
}
