package objects;

public class NewsContent {
	String category, url1, selector1, url2, selector2, url3, selector3;

	public NewsContent(String category, String url1, String selector1, String url2, String selector2, String url3,
			String selector3) {
		super();
		this.category = category;
		this.url1 = url1;
		this.selector1 = selector1;
		this.url2 = url2;
		this.selector2 = selector2;
		this.url3 = url3;
		this.selector3 = selector3;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUrl1() {
		return url1;
	}

	public void setUrl1(String url1) {
		this.url1 = url1;
	}

	public String getSelector1() {
		return selector1;
	}

	public void setSelector1(String selector1) {
		this.selector1 = selector1;
	}

	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public String getSelector2() {
		return selector2;
	}

	public void setSelector2(String selector2) {
		this.selector2 = selector2;
	}

	public String getUrl3() {
		return url3;
	}

	public void setUrl3(String url3) {
		this.url3 = url3;
	}

	public String getSelector3() {
		return selector3;
	}

	public void setSelector3(String selector3) {
		this.selector3 = selector3;
	}

	@Override
	public String toString() {
		return "NewsContent [category=" + category + ", url1=" + url1 + ", selector1=" + selector1 + ", url2=" + url2
				+ ", selector2=" + selector2 + ", url3=" + url3 + ", selector3=" + selector3 + "]";
	}
}