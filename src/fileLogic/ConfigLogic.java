package fileLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigLogic {

    private static final String CONFIG_FILE = "txtFiles/config.txt";
    private static Map<String, String> configData = new HashMap<>();

    static {
        configData.put("SMTP_HOST", "smtp.gmail.com");
        configData.put("SMTP_PORT", "465");
        
        loadConfig();
    }

    private static void loadConfig() {
        File file = new File("txtFiles/settings.txt");
        if (!file.exists()) {
            System.err.println("Configuration file not found: txtFiles/settings.txt");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                line = line.trim();
                if (line.isEmpty() || line.startsWith("SECTION;")) continue;

                String[] parts = line.split(";", -1);
                if (parts.length >= 2) {
                    String key = parts[0].trim();
                    String val = parts[1].trim();
                    
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
            System.err.println("Ha ocurrido este problema: " + e.getMessage());
        }
    }
  

    public static String get(String key) {
        return configData.getOrDefault(key, "");
    }
    
    public static String[] getArray(String key) {
    	String val = configData.getOrDefault(key, "");
    	if (val.isEmpty()) return new String[0];
    	return val.split(";");
    }
}
