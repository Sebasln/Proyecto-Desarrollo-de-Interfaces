package windowContent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import fileLogic.WebLogic;
import objects.NewsItem;
import objects.User;
import programLogic.WebReader;

import java.util.Date;
import java.text.SimpleDateFormat;

public class MainWindow extends JFrame {

	private User user;
	private String loadTime; // Para guardar la hora de carga
	private ArrayList<String> currentHeadlines = new ArrayList<>(); // Para guardar los titulares actuales

	public MainWindow(User user) {
		this.user = user;
		initialize();
		ImageIcon icono = new ImageIcon("images/logoNoticias.png");
		this.setIconImage(icono.getImage());
		this.setVisible(true);
	}

	private void initialize() {
		// --- MENU BAR (Ayuda) ---
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.JOptionPane.showMessageDialog(null, 
						"Proyecto DAM 25/26\nDesarrollado por: Sebastián Silva\nVersión 25.12.11", 
						"Acerca de", javax.swing.JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnAyuda.add(mntmAcercaDe);

		this.getContentPane().setBackground(new Color(40, 40, 40));
		this.setBounds(100, 50, 900, 900);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// Declaramos el botón aquí para poder usarlo en el hilo
		JButton btnGuardar = new JButton("Guardar consulta");
		btnGuardar.setBounds(380, 800, 140, 23);
		btnGuardar.setEnabled(false); // Deshabilitado al inicio
		this.getContentPane().add(btnGuardar);
		this.setLocationRelativeTo(null); // Centrar en pantalla
		this.setResizable(false); // Requisito: No redimensionable
		// Confirmación al cerrar
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (javax.swing.JOptionPane.showConfirmDialog(null, 
					"¿Estás seguro de que quieres salir?", "Cerrar Aplicación", 
					javax.swing.JOptionPane.YES_NO_OPTION,
					javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
		

		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		
		JLabel mainLabel = new JLabel("Estas son las noticias más recientes según sus intereses:");
		mainLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainLabel.setForeground(new Color(255, 255, 255));
		mainLabel.setBounds(10, 11, 864, 77);
		this.getContentPane().add(mainLabel);
		
		JLabel label1 = new JLabel("Cargando noticias...");
		label1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label1.setForeground(Color.WHITE);
		label1.setBounds(50, 100, 380, 80);
		this.getContentPane().add(label1);
		
		JLabel label2 = new JLabel("");
		label2.setFont(new Font("Tahoma", Font.BOLD, 13));
		label2.setForeground(Color.WHITE);
		label2.setBounds(50, 180, 380, 80);
		this.getContentPane().add(label2);
		
		JLabel label3 = new JLabel("");
		label3.setFont(new Font("Tahoma", Font.BOLD, 13));
		label3.setForeground(Color.WHITE);
		label3.setBounds(50, 260, 380, 80);
		this.getContentPane().add(label3);
		
		JLabel label4 = new JLabel("");
		label4.setFont(new Font("Tahoma", Font.BOLD, 13));
		label4.setForeground(Color.WHITE);
		label4.setBounds(50, 340, 380, 80);
		this.getContentPane().add(label4);
		
		JLabel label5 = new JLabel("");
		label5.setFont(new Font("Tahoma", Font.BOLD, 13));
		label5.setForeground(Color.WHITE);
		label5.setBounds(50, 420, 380, 80);
		this.getContentPane().add(label5);
		
		JLabel label6 = new JLabel("");
		label6.setFont(new Font("Tahoma", Font.BOLD, 13));
		label6.setForeground(Color.WHITE);
		label6.setBounds(50, 500, 380, 80);
		this.getContentPane().add(label6);
		
		JLabel label7 = new JLabel("");
		label7.setFont(new Font("Tahoma", Font.BOLD, 13));
		label7.setForeground(Color.WHITE);
		label7.setBounds(50, 580, 380, 80);
		this.getContentPane().add(label7);
		
		JLabel label8 = new JLabel("");
		label8.setFont(new Font("Tahoma", Font.BOLD, 13));
		label8.setForeground(Color.WHITE);
		label8.setBounds(50, 660, 380, 80);
		this.getContentPane().add(label8);
		
		JLabel label9 = new JLabel("");
		label9.setFont(new Font("Tahoma", Font.BOLD, 13));
		label9.setForeground(Color.WHITE);
		label9.setBounds(50, 740, 380, 80);
		this.getContentPane().add(label9);
		
		JLabel label10 = new JLabel("");
		label10.setFont(new Font("Tahoma", Font.BOLD, 13));
		label10.setForeground(Color.WHITE);
		label10.setBounds(470, 100, 380, 80);
		this.getContentPane().add(label10);
		
		JLabel label11 = new JLabel("");
		label11.setFont(new Font("Tahoma", Font.BOLD, 13));
		label11.setForeground(Color.WHITE);
		label11.setBounds(470, 180, 380, 80);
		this.getContentPane().add(label11);
		
		JLabel label12 = new JLabel("");
		label12.setFont(new Font("Tahoma", Font.BOLD, 13));
		label12.setForeground(Color.WHITE);
		label12.setBounds(470, 260, 380, 80);
		this.getContentPane().add(label12);
		
		JLabel label13 = new JLabel("");
		label13.setFont(new Font("Tahoma", Font.BOLD, 13));
		label13.setForeground(Color.WHITE);
		label13.setBounds(470, 340, 380, 80);
		this.getContentPane().add(label13);
		
		JLabel label14 = new JLabel("");
		label14.setFont(new Font("Tahoma", Font.BOLD, 13));
		label14.setForeground(Color.WHITE);
		label14.setBounds(470, 420, 380, 80);
		this.getContentPane().add(label14);
		
		JLabel label15 = new JLabel("");
		label15.setFont(new Font("Tahoma", Font.BOLD, 13));
		label15.setForeground(Color.WHITE);
		label15.setBounds(470, 500, 380, 80);
		this.getContentPane().add(label15);
		
		JLabel label16 = new JLabel("");
		label16.setFont(new Font("Tahoma", Font.BOLD, 13));
		label16.setForeground(Color.WHITE);
		label16.setBounds(470, 580, 380, 80);
		this.getContentPane().add(label16);
		
		JLabel label17 = new JLabel("");
		label17.setFont(new Font("Tahoma", Font.BOLD, 13));
		label17.setForeground(Color.WHITE);
		label17.setBounds(470, 660, 380, 80);
		this.getContentPane().add(label17);
		
		JLabel label18 = new JLabel("");
		label18.setFont(new Font("Tahoma", Font.BOLD, 13));
		label18.setForeground(Color.WHITE);
		label18.setBounds(470, 740, 380, 80);
		this.getContentPane().add(label18);

		JLabel[] labels = {label1, label10, label2, label11, label3, label12, label4, label13, label5, label14,
				           label6, label15, label7, label16, label8, label17, label9, label18};

		for(JLabel lbl : labels) {
			lbl.setSize(380, 80); 
			lbl.setVerticalAlignment(SwingConstants.TOP); 
		}

		Thread newsThread = new Thread(() -> {
			// Capturamos la hora EXACTA antes de empezar a descargar
			loadTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			
			boolean isAdmin = user.getRole().equalsIgnoreCase("ADMIN");
			ArrayList<NewsItem> news = WebReader.getNews(user.getPreferencesList(), isAdmin);
			System.out.println("Se han cargado " + news.size() + " noticias.");
			
			// Guardamos los titulares en memoria para el botón guardar
			for (NewsItem item : news) {
				currentHeadlines.add(item.getCategory() + ": " + item.getHeadline());
			}
			
			SwingUtilities.invokeLater(() -> {
				for (int i = 0; i < news.size(); i++) {
					if (i >= labels.length) break;
					
					NewsItem content = news.get(i);
					String displayText = "<html>" + content.getCategory() + ": " + content.getHeadline() + "</html>";
					labels[i].setText(displayText);
				}
				
				if (news.isEmpty()) {
					labels[0].setText("No se encontraron noticias para tus intereses.");
				}
				
				// Habilitamos el botón cuando ha terminado de cargar
				btnGuardar.setEnabled(true);
			});
		});
		newsThread.start();
		
		// Lógica del botón guardar (ya añadido arriba)
		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentHeadlines.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No hay noticias cargadas para guardar.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				WebLogic.saveLog(user.getUsername(), currentHeadlines, loadTime);
				JOptionPane.showMessageDialog(null, "Noticias guardadas en log.txt\nHora de referencia: " + loadTime, "Éxito", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		JButton btnNewButton = new JButton("Volver al login");
		btnNewButton.setBounds(10, 800, 132, 23);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoginWindow();
				dispose();
			}
		});
		this.getContentPane().add(btnNewButton);

		if (user.getRole().equalsIgnoreCase("ADMIN")) {
			JButton btnBackAdmin = new JButton("Volver a Admin");
			btnBackAdmin.setBounds(740, 800, 132, 23); 
			btnBackAdmin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new AdminWindow(user);
					dispose();
				}
			});
			this.getContentPane().add(btnBackAdmin);
		}
	}
}		
