package objects;

public class NewsItem {
	// esta clase es para guardar los titulares despues de haber sido extraidos de
	// las urls y los selectores que contienen su clase 'hermana'

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

	public void setCategory(String category) {
		this.category = category;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "NewsItem [category=" + category + ", headline=" + headline + ", url=" + url + "]";
	}
}