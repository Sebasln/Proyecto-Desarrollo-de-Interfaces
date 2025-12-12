
import fileLogic.WebLogic;
import fileLogic.UserLogic;
import objects.NewsContent;
import java.io.File;

public class TestSettings {
    public static void main(String[] args) {
        System.out.println("Testing settings loading...");
        File f = new File("txtFiles/settings.txt");
        System.out.println("Settings file exists: " + f.exists());
        System.out.println("Path: " + f.getAbsolutePath());

        // Test News Loading
        WebLogic.setNewsProperties();
        System.out.println("News List Size: " + WebLogic.newsList.size());
        for(NewsContent nc : WebLogic.newsList) {
            System.out.println("Loaded Category: " + nc.getCategory());
        }

        // Test User Preferences Loading (Requires dummy users in list)
        // Skip for now, focus on why news might be empty.
    }
}
