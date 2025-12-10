package fileLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import objects.NewsContent;

public class WebLogic {
	public static ArrayList<NewsContent> newsList = new ArrayList<>();

	public static void setNewsProperties() {
		File urls = new File("txtFiles/urls.txt");

		if (!(urls.exists())) {
			System.out.println("No se pueden buscar noticias porque no existe urls.txt");
			return;
		}

		try (FileReader fr = new FileReader(urls); BufferedReader br = new BufferedReader(fr);) {
			String line;

			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()){
					System.out.println("urls.txt tiene campos faltantes, por lo que no se puede ejecutar el programa.");
					return;
				}

				String[] newsFields = line.split(";", -1);
				String category = checkNullity(newsFields[0], 0);
				String url1 = checkNullity(newsFields[1], 1);
				String selector1 = checkNullity(newsFields[2], 2);
				String url2 = checkNullity(newsFields[3], 3);
				String selector2 = checkNullity(newsFields[4], 4);
				String url3 = checkNullity(newsFields[5], 5);
				String selector3 = checkNullity(newsFields[6], 6);

				NewsContent newsCategory = new NewsContent(category, url1, selector1, url2, selector2, url3, selector3);
				newsList.add(newsCategory);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String checkNullity(String field, int position) {
		if (field.equals("") || field == null) {
			System.out.println("urls.txt tiene campos faltantes, por lo que no se puede ejecutar el programa.");
			return null;
		}
		return field;
	}
}