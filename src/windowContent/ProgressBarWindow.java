package windowContent;

import java.io.FileNotFoundException;

import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;


import fileLogic.UserLogic;

public class ProgressBarWindow extends JWindow{

	public ProgressBarWindow() {
		JProgressBar progressBar = new JProgressBar();

		progressBar.setIndeterminate(false);
		progressBar.setStringPainted(true);
		this.add(progressBar);
		this.setSize(300, 50);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		Thread loadThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <= 100; i++) {
					progressBar.setValue(i);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					if (i == 80) {
						try {
							UserLogic.readUsers();
							UserLogic.readUserPreferences();
							continue;
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}				
				ProgressBarWindow.this.dispose();
				SwingUtilities.invokeLater(new Runnable() {
					
				public void run() {
					System.out.println("Creando ventana de login...");
					new windowContent.LoginWindow();
				}
			});			

			}
		});
		loadThread.start();
	}
}