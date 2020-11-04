package rest.controller.request.posts;

public class PostFavoriteRequest {
	private boolean favorite;
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
}
