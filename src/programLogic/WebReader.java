package programLogic;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import fileLogic.WebLogic;
import objects.NewsContent;
import objects.NewsItem;

public class WebReader {

	public static ArrayList<NewsItem> getNews(ArrayList<String> userPreferences, boolean isAdmin) {
		ArrayList<NewsItem> results = new ArrayList<>();
		
		ArrayList<NewsContent> configList = WebLogic.newsList;
		if (configList == null || configList.isEmpty()) {
			System.err.println("No hay configuración de noticias cargada.");
			return results;
		}

		if (isAdmin) {
			for (NewsContent config : configList) {
				scrapeCategory(config, 3, results);
			}
			
		} else {
			
			ArrayList<NewsContent> preferredConfigs = new ArrayList<>();
			for (NewsContent config : configList) {
				if (userPreferences.contains(config.getCategory())) {
					preferredConfigs.add(config);
				}
			}
			
			if (preferredConfigs.isEmpty()) return results;

			int totalSlots = 18;
			int numCategories = preferredConfigs.size();
			int slotsPerCategory = totalSlots / numCategories;
			int remainder = totalSlots % numCategories;

			for (int i = 0; i < numCategories; i++) {
				NewsContent config = preferredConfigs.get(i);
				int newsToFetch = slotsPerCategory + (i < remainder ? 1 : 0);
				
				if (newsToFetch > 0) {
					scrapeCategory(config, newsToFetch, results);
				}
			}
		}
		
		return results;
	}

	private static void scrapeCategory(NewsContent config, int limit, ArrayList<NewsItem> results) {
		ArrayList<ArrayList<String>> sourcesHeadlines = new ArrayList<>();
		ArrayList<String> sourcesUrls = new ArrayList<>();

		String[] urls = {config.getUrl1(), config.getUrl2(), config.getUrl3()};
		String[] selectors = {config.getSelector1(), config.getSelector2(), config.getSelector3()};

		for (int i = 0; i < 3; i++) {
			String url = urls[i];
			String selector = selectors[i];

			if (url == null || url.isEmpty() || selector == null || selector.isEmpty()) continue;

			ArrayList<String> retrieved = new ArrayList<>();
			try {
				Document doc = Jsoup.connect(url)
						.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
						.timeout(5000) 
						.get();
				
				Elements elements = doc.select(selector);
				// Cogemos hasta 'limit' titulares por si acaso necesitamos rellenar huecos
				for (int j = 0; j < Math.min(elements.size(), limit); j++) {
					String txt = elements.get(j).text();
					if (txt.length() > 5) {
						retrieved.add(txt);
					}
				}
			} catch (Exception e) { // Catch genérico
				System.err.println("Error en " + url + ": " + e.toString());
			}
			
			if (!retrieved.isEmpty()) {
				sourcesHeadlines.add(retrieved);
				sourcesUrls.add(url);
			}
		}

		// Round Robin
		int addedCount = 0;
		int maxIndex = 0; 
		boolean addedSomething = true;

		while (addedCount < limit && addedSomething) {
			addedSomething = false;
			for (int i = 0; i < sourcesHeadlines.size(); i++) {
				if (addedCount >= limit) break;
				
				ArrayList<String> source = sourcesHeadlines.get(i);
				if (maxIndex < source.size()) {
					String headline = source.get(maxIndex);
					String url = sourcesUrls.get(i);
					
					results.add(new NewsItem(config.getCategory(), headline, url));
					addedCount++;
					addedSomething = true;
				}
			}
			maxIndex++;
		}
	}
}
