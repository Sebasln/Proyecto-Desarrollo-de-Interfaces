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
		newsList.clear();
		File settingsFile = new File("txtFiles/settings.txt");

		if (!settingsFile.exists()) {
			System.err.println("No existe settings.txt, no se pueden cargar noticias.");
			return;
		}

		try (FileReader fr = new FileReader(settingsFile); BufferedReader br = new BufferedReader(fr)) {
			String line;
			boolean isUrlSection = false;

			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty()) continue;

				if (line.startsWith("SECTION;URL")) {
					isUrlSection = true;
					continue;
				} else if (line.startsWith("SECTION;")) {
					isUrlSection = false;
					continue;
				}

				if (isUrlSection) {
					String[] parts = line.split(";", -1);
					
					if (parts.length >= 7) {
						String category = parts[0].trim();
						String u1 = parts[1].trim();
						String s1 = parts[2].trim();
						String u2 = parts[3].trim();
						String s2 = parts[4].trim();
						String u3 = parts[5].trim();
						String s3 = parts[6].trim();
						
						if (!category.isEmpty()) {
							newsList.add(new NewsContent(category, u1, s1, u2, s2, u3, s3));
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveLog(String username, ArrayList<String> headlines, String timeStamp) {
    File logFile = new File("txtFiles/log.txt");
    try (java.io.FileWriter fw = new java.io.FileWriter(logFile, true); 
        java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
        
        bw.write("--- LOG: " + username + " [" + timeStamp + "] ---");
        bw.newLine();
        for (String h : headlines) {
            bw.write(h);
            bw.newLine();
        }
        bw.newLine();
        
    } catch (java.io.IOException e) {
        e.printStackTrace();
    }
}

	public static String checkNullity(String field, int position) {
		if (field.equals("") || field == null) {
			System.out.println("settings.txt tiene campos faltantes, por lo que no se puede ejecutar el programa.");
			return null;
		}
		return field;
	}
}