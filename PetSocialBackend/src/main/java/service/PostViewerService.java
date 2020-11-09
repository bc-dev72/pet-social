package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import commons.model.post.Post;
import commons.model.post.PostData;
import repo.PostDataRepo;
import repo.PostRepo;
import rest.controller.response.posts.PostResponse;
import security.util.TokenAccountData;
import util.FrontEndModelUtil;

@Service
public class PostViewerService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private PostDataRepo postDataRepo;
	
	public List<PostResponse> getAllPosts(TokenAccountData tokenData, int pageNumber, String lastItem) {
		String accountId = "";
		if(tokenData != null)
			accountId = tokenData.getAccountId();
		
		Page<Post> postsResults = postRepo.findAll(PageRequest.of(pageNumber, 5, Sort.by(Sort.Direction.DESC, "postTime")));
		List<Post> posts = postsResults.getContent();
		
		HashMap<String, PostData> postDataMap = getPostDataForPosts(posts);
		
		List<PostResponse> response = new ArrayList<>();
		for(Post post : posts) 
			response.add(FrontEndModelUtil.createPostResponse(accountId, post, postDataMap.get(post.getPostId())));
		return response;
	}
	
	public List<PostResponse> getPostsForUser(TokenAccountData tokenData, String username, int pageNumber, String lastPost) {
		String accountId = "";
		if(tokenData != null)
			accountId = tokenData.getAccountId();
		
		List<Post> posts = postRepo.findByUserPostedOrderByPostTimeDesc(username, PageRequest.of(pageNumber, 10));
		HashMap<String, PostData> postDataMap = getPostDataForPosts(posts);
		
		List<PostResponse> response = new ArrayList<>();
		for(Post post : posts) 
			response.add(FrontEndModelUtil.createPostResponse(accountId, post, postDataMap.get(post.getPostId())));
		return response;
	}
	
	
	private HashMap<String, PostData> getPostDataForPosts(List<Post> posts) {
		HashSet<String> ids = new HashSet<>();
		for(Post post : posts)
			ids.add(post.getPostId());
		List<PostData> postDataList = postDataRepo.findByPostIdIn(ids);
		
		HashMap<String, PostData> map = new HashMap<>();
		for(PostData postData : postDataList) 
			map.put(postData.getPostId(), postData);
		
		for(String id : ids) {
			if(!map.containsKey(id)) {
				PostData postData = new PostData();
				postData.setComments(new ArrayList<>());
				postData.setFavorites(new HashSet<>());
				postData.setPostId(id);
				postData.setVotes(new HashMap<>());
				map.put(id, postData);
			}
		}
		return map;
	}
	
	
}
