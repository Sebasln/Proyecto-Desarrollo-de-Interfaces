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

	// con este hilo lo dejo en segundo plano para que se lea la
	// hora que es y mire si toca enviar correos

	private boolean ranToday = false;
	private String lastRunDate = "";

	@Override
	public void run() {
		while (true) {
			try {
				String configuredTime = ConfigLogic.get("TIME");

				// si no tiene hora puesta pues se duerme un rato con fe que alguien le
				// establezca una hora
				if (configuredTime.isEmpty()) {
					Thread.sleep(60000);
					continue;
				}

				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date now = new Date();
				String currentTime = timeFormat.format(now);
				String currentDate = dateFormat.format(now);

				// si ya toca mandar correos y no ha mandado ninguno todavia, pues se ejecuta
				if (currentTime.equals(configuredTime) && !currentDate.equals(lastRunDate)) {
					sendEmailsToAllUsers();
					lastRunDate = currentDate;
				}

				Thread.sleep(30000);
			} catch (InterruptedException e) {
				MessageUtils.showError(null, "Ha ocurrido este problema: " + e.getMessage(), e);
			}
		}
	}

	private void sendEmailsToAllUsers() {
		if (UserLogic.usersList.isEmpty()) {
			MessageUtils.showError(null, "No hay usuarios cargados para enviar correos.");
			return;
		}

		for (User user : UserLogic.usersList) {
			if (user.getEmail() == null || user.getEmail().isEmpty()) {
				// si no tiene correo pues se salta
				continue;
			}

			ArrayList<NewsItem> news = WebReader.getNews(user.getPreferencesList(), false);

			if (news.isEmpty()) {
				// si no hay noticias pues se salta
				MessageUtils.showError(null, "Sin noticias para " + user.getUsername());
				continue;
			}

			String subject = "NOTICIAS DAM";
			String body = "";

			// le metemos mil millones de cosas al body

			body += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + "\n";
			body += "--------------------------------------------------\n\n";

			String lastCat = "";
			for (NewsItem item : news) {
				if (!item.getCategory().equals(lastCat)) {
					body += "\n" + item.getCategory() + ":\n";
					lastCat = item.getCategory();
				}
				body += item.getHeadline();
				if (item.getUrl() != null && !item.getUrl().isEmpty()) {
					body += " [" + item.getUrl() + "]";
				}
				body += "\n";
			}

			// enviamos
			EmailLogic.sendEmail(user.getEmail(), subject, body);
		}
	}
}
