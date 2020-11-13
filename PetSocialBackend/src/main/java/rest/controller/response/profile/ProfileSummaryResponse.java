package rest.controller.response.profile;

public class ProfileSummaryResponse {
	
	private String bio;
	private String profilePicture;
	
	private int totalFollowers;
	private int totalFollowing;
	private int totalFavorites;
	
	private boolean authedUser;
	private boolean following;
	
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public int getTotalFollowers() {
		return totalFollowers;
	}
	public void setTotalFollowers(int totalFollowers) {
		this.totalFollowers = totalFollowers;
	}
	public int getTotalFollowing() {
		return totalFollowing;
	}
	public void setTotalFollowing(int totalFollowing) {
		this.totalFollowing = totalFollowing;
	}
	public int getTotalFavorites() {
		return totalFavorites;
	}
	public void setTotalFavorites(int totalFavorites) {
		this.totalFavorites = totalFavorites;
	}
	public boolean isFollowing() {
		return following;
	}
	public void setFollowing(boolean following) {
		this.following = following;
	}
	public boolean isAuthedUser() {
		return authedUser;
	}
	public void setAuthedUser(boolean authedUser) {
		this.authedUser = authedUser;
	}
}
