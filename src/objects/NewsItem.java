package objects;

public class NewsItem {
	private String category;
	private String headline;
	private String url;

	public NewsItem(String category, String headline, String url) {
		this.category = category;
		this.headline = headline;
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	public String getHeadline() {
		return headline;
	}

	public String getUrl() {
		return url;
	}
}
