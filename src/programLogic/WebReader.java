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
						.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
						.referrer("http://www.google.com") 
						.timeout(10000) // SUBIDO A 10 SEGUNDOS
						.ignoreHttpErrors(true)
						.get();
				
				Elements elements = doc.select(selector);
				
				if (elements.isEmpty()) {
					System.out.println("AVISO: Cero noticias encontradas en " + url + " (Selector: " + selector + "). Posible cambio de diseño web.");
				}

				// Lógica específica para El Mundo: saltar los primeros 5 H2 (juegos/pasatiempos)
				int skipCount = 0;
				if (url.contains("elmundo.es")) {
					skipCount = 5;
				}

				// Cogemos hasta 'limit' titulares VÁLIDOS
				for (int j = skipCount; j < elements.size(); j++) {
					if (retrieved.size() >= limit) break; // Ya tenemos suficientes
					
					String txt = elements.get(j).text().trim();
					
					// Filtro de ruido (Blacklist)
					if (txt.length() > 5 && !isNoise(txt)) {
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

	private static boolean isNoise(String txt) {
		String[] blacklist = {
			"Principales", "Más noticias", "Destacamos", "Video y audio", 
			"No te lo pierdas", "Próximos Lanzamientos", "Temas", "Suscríbete",
			"Iniciar sesión", "Regístrate", "Contacto", "Publicidad", "Aviso Legal",
			"Política de privacidad", "Cookies", "Lo más leído", "Lo más visto",
			"Newsletter", "Hemeroteca"
		};
		
		for (String bad : blacklist) {
			if (txt.equalsIgnoreCase(bad)) return true;
		}
		return false;
	}
}
