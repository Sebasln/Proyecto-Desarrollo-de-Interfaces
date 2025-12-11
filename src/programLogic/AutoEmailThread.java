package programLogic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import fileLogic.ConfigLogic;
import fileLogic.UserLogic;
import objects.NewsItem;
import objects.User;

public class AutoEmailThread extends Thread {

	private boolean ranToday = false;
	private String lastRunDate = "";

	@Override
	public void run() {
		System.out.println("AutoEmailThread iniciado.");
		while (true) {
			try {
				String configuredTime = ConfigLogic.get("TIME");
				if (configuredTime.isEmpty()) {
					Thread.sleep(60000);
					continue;
				}

				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date now = new Date();
				String currentTime = timeFormat.format(now);
				String currentDate = dateFormat.format(now);


				if (currentTime.equals(configuredTime) && !currentDate.equals(lastRunDate)) {
					System.out.println("Hora de envío de correos detectada: " + currentTime);
					sendEmailsToAllUsers();
					lastRunDate = currentDate;
				}

				Thread.sleep(30000);
			} catch (InterruptedException e) {
				System.err.println("Ha ocurrido este problema: " + e.getMessage());
			}
		}
	}

	private void sendEmailsToAllUsers() {
		if (UserLogic.usersList.isEmpty()) {
			System.out.println("No hay usuarios cargados para enviar correos.");
			return;
		}

		for (User user : UserLogic.usersList) {
			if (user.getEmail() == null || user.getEmail().isEmpty()) continue;
			
			System.out.println("Preparando noticias para: " + user.getUsername());
			
			ArrayList<NewsItem> news = WebReader.getNews(user.getPreferencesList(), false);
			
			if (news.isEmpty()) {
				System.out.println("Sin noticias para " + user.getUsername());
				continue;
			}
			
			String subject = "NOTICIAS DAM";
			StringBuilder body = new StringBuilder();
			
			body.append("• " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + "\n");
			body.append("--------------------------------------------------\n\n");
			
			String lastCat = "";
			for (NewsItem item : news) {
				if (!item.getCategory().equals(lastCat)) {
					body.append("\n• " + item.getCategory() + ":\n");
					lastCat = item.getCategory();
				}
				body.append("✓ " + item.getHeadline());
				if (item.getUrl() != null && !item.getUrl().isEmpty()) {
					body.append(" [" + item.getUrl() + "]");
				}
				body.append("\n");
			}
			
			EmailLogic.sendEmail(user.getEmail(), subject, body.toString());
		}
	}
}
