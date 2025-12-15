package fileLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import programLogic.MessageUtils;

public class ConfigLogic {

	private static final String CONFIG_FILE = "txtFiles/config.txt";
	private static Map<String, String> configData = new HashMap<>();

	static {
		configData.put("SMTP_HOST", "smtp.gmail.com");
		configData.put("SMTP_PORT", "465");
		loadConfig();
	}

	// ese static es como mi DOMContentLoaded de js, pero desgraciadamente en java

	private static void loadConfig() {
		File file = new File("txtFiles/settings.txt");
		if (!file.exists()) {
			MessageUtils.showError(null, "Configuration file not found: txtFiles/settings.txt");
			return;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty() || line.startsWith("SECTION;"))
					continue;

				String[] parts = line.split(";", -1);
				if (parts.length >= 2) {
					String key = parts[0].trim();
					String val = parts[1].trim();

					if (parts.length > 2) {
						configData.put("URL_" + key, line.substring(key.length() + 1));
						continue;
					}

					if (key.equals("TIME") && val.endsWith(".")) {
						val = val.substring(0, val.length() - 1);
					}

					switch (key) {
					case "FROM_EMAIL_ADDRESS":
						configData.put("SMTP_USER", val);
						break;
					case "FROM_EMAIL_PASSWORD":
						configData.put("SMTP_PASS", val);
						break;
					case "TIME":
						configData.put("TIME", val);
						break;
					}
				}
			}
		} catch (IOException e) {
			MessageUtils.showError(null, "Ha ocurrido este problema: " + e.getMessage(), e);
		}
	}

	public static String get(String key) {
		return configData.getOrDefault(key, "");
	}
}