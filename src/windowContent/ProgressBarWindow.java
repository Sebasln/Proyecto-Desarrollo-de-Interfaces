package windowContent;

import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;

import fileLogic.UserLogic;
import fileLogic.WebLogic;

public class ProgressBarWindow extends JWindow {

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
						Thread.sleep(5); // 100 * 0,05 = 5s + lo que se tarde en revisar los archivos, que no debería
											// ser nada.
					} catch (InterruptedException e) {
						System.err.println("Ha ocurrido este problema: " + e.getMessage());
					}

					if (i == 80) {
						try {
							UserLogic.readUsers();
							UserLogic.readUserPreferences();							
							WebLogic.setNewsProperties();
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(null,
									"Error crítico: Faltan archivos de sistema (users.txt o settings.txt).\nLa aplicación se cerrará.",
									"Error Fatal", JOptionPane.ERROR_MESSAGE);
							System.exit(1);
						}
					}
				}
				ProgressBarWindow.this.dispose(); // pa que se espere en lo que carga la barra de progreso le ponemos un
													// invoke later
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						new LoginWindow();
					}
				});

			}
		});
		loadThread.start();
	}
}