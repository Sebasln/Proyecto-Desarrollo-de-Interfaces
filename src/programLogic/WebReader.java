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
		if (WebLogic.newsList == null) {
			MessageUtils.showError(null, "No hay noticias cargadas");
			return res;
		}

		// quiero que al admin le carguen 3 noticias de cada categoria (18 en total)
		// y que al usuario le carguen 18 noticias de las categorias que el elige (en
		// principio, porque a veces no cargan todas)
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

			String[] urls = { c.getUrl1(), c.getUrl2(), c.getUrl3() };
			String[] selectors = { c.getSelector1(), c.getSelector2(), c.getSelector3() };
			int count = 0;

			for (int i = 0; i < 3; i++) {
				if (count >= limit) {
					break;
				}

				if (urls[i] == null || urls[i].isEmpty()) {
					continue;
				}

				try {
					Document doc = Jsoup.connect(urls[i]).userAgent(
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
							.timeout(5000).get();

					// Document doc = Jsoup.connect(urls[i]).get();
					// Esto funciona con dos paginas contadas, con el resto toca sacar el codigo
					// totalmente legitimo de ahi arriba

					Elements elements = doc.select(selectors[i]);

					for (Element e : elements) {
						if (count >= limit) {
							break;
						}

						String t = e.text().trim();

						// solucion radical ante titulares que no son titulares
						if (t.contains("El Gobierno de los escándalos") || t.contains("Guerra de Ucrania")
								|| t.contains("Tienes 6 intentos y 1 sola palabra")
								|| t.contains("Sabes más palabras de las que crees")
								|| t.contains("Encuentra las palabras ocultas")
								|| t.contains("Tu reto diario en nivel fácil")
								|| t.contains("No todos lo terminan... ¿Tú podrás?") || t.contains("Ir a MARCA TV")
								|| t.contains("NOTICIAS DESTACADAS")) {
							continue;
						}

						// si el texto es menor a 10 caracteres no lo pillo porque seguramente no sea un
						// titular
						if (t.length() > 10) {
							res.add(new NewsItem(c.getCategory(), t, urls[i]));
							count++;
						}
					}
				} catch (Exception e) {
					MessageUtils.showError(null, "Error al cargar noticias: " + e.getMessage(), e);
				}
			}
		}
		return res;
	}
}
