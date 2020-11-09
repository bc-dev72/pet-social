package rest.controller.request.posts;

public class PostRequest {
	private String desc;
	private String image;
	private boolean randomImage;
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
	public boolean isRandomImage() {
		return randomImage;
	}
	public void setRandomImage(boolean randomImage) {
		this.randomImage = randomImage;
	}
}
