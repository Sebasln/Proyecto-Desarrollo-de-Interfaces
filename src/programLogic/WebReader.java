package programLogic;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fileLogic.WebLogic;
import objects.NewsContent;
import objects.NewsItem;

public class WebReader {
	public static ArrayList<NewsItem> getNews(ArrayList<String> prefs, boolean isAdmin) {
		ArrayList<NewsItem> res = new ArrayList<>();
		if (WebLogic.newsList == null) return res;

		int limit = 0;
		if (isAdmin) {
			limit = 3;
		} else {
			limit = 18 / Math.max(1, prefs.size());
		}

		for (NewsContent c : WebLogic.newsList) {
			if (!isAdmin && !prefs.contains(c.getCategory())) {
				continue;
			}

			String[] urls = {c.getUrl1(), c.getUrl2(), c.getUrl3()};
			String[] selectors = {c.getSelector1(), c.getSelector2(), c.getSelector3()};
			int count = 0;

			for (int i = 0; i < 3; i++) {
				if (count >= limit) {
					break;
				}
				
				if (urls[i] == null || urls[i].isEmpty()) {
					continue;
				}
				
				try {
					Document doc = Jsoup.connect(urls[i])
							.userAgent("Mozilla/5.0")
							.timeout(5000)
							.get();
							
					Elements elements = doc.select(selectors[i]);
					
					for (Element e : elements) {
						if (count >= limit) {
							break;
						}
						
						String t = e.text().trim();
						if (t.length() > 10) { 
							res.add(new NewsItem(c.getCategory(), t, urls[i]));
							count++;
						}
					}
				} catch (Exception e) {
				}
			}
		}
		return res;
	}
}
