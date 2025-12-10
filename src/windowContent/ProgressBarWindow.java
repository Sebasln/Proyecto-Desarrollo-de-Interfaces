package windowContent;

import java.io.FileNotFoundException;

import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;

import fileLogic.UserLogic;
import fileLogic.WebLogic;

public class ProgressBarWindow extends JWindow{

	public ProgressBarWindow() {
		this.setLayout(null);

		ImageIcon imagenOriginal = new ImageIcon("images/logoNoticias.png");
		Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
		ImageIcon imagenFinal = new ImageIcon(imagenEscalada);
		JLabel fondo = new JLabel(imagenFinal);
		fondo.setBounds(0, 0, 400, 300);
		this.add(fondo);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(false);
		progressBar.setStringPainted(true);
		progressBar.setBounds(50, 250, 300, 30); 
		this.add(progressBar); 

		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setIconImage(imagenOriginal.getImage());
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
							WebLogic.setNewsProperties();
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