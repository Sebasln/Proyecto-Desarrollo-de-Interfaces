package windowContent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fileLogic.UserLogic;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import objects.User;

public class AdminWindow extends JFrame {

	private User user;
	private JLayeredPane menuPane;
	private JLayeredPane createUserPane;
	private JLayeredPane deleteUserPane;
	private JLayeredPane emailPane;

	private JTextField usernameTField;
	private JTextField deleteTField;
	private JTextField passwordTField;
	private JTextField emailTField;
	private javax.swing.JComboBox<String> roleComboBox;

	public String username;
	public String password;
	public String email;
	public String role;
	
	public AdminWindow(User user) {
		this.user = user;
		initialize();
		ImageIcon icono = new ImageIcon("images/logoNoticias.png");
		this.setIconImage(icono.getImage());
		this.setVisible(true);
	}

	private void initialize() {
		this.setBounds(100, 100, 800, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(null, 
					"¿Estás seguro de que quieres salir?", "Cerrar Aplicación", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
		

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, 
						"Proyecto DAM 25/26\nDesarrollado por: Sebastián Silva\nVersión 25.12.11", 
						"Acerca de", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnAyuda.add(mntmAcercaDe);

		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);

		menuPane = new JLayeredPane();
		menuPane.setLayout(null);
		menuPane.setOpaque(true);
		menuPane.setBackground(new Color(40, 40, 40));
		menuPane.setBounds(0, 0, 784, 540); 
		this.getContentPane().add(menuPane);

		JLabel headerAdminLbl = new JLabel("Menú de admin");
		headerAdminLbl.setHorizontalAlignment(SwingConstants.CENTER);
		headerAdminLbl.setForeground(Color.WHITE);
		headerAdminLbl.setFont(new Font("Tahoma", Font.BOLD, 26));
		headerAdminLbl.setBounds(10, 30, 764, 50);
		menuPane.add(headerAdminLbl);

		int btnWidth = 220;
		int btnHeight = 100;
		int startX = 50;
		int gap = 20;

		JButton goToCreateUserBtn = new JButton("Crear usuario");
		goToCreateUserBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		goToCreateUserBtn.setForeground(new Color(0, 0, 0));
		goToCreateUserBtn.setBackground(new Color(255, 255, 255));
		goToCreateUserBtn.setBounds(startX, 200, btnWidth, btnHeight);
		menuPane.add(goToCreateUserBtn);

		JButton goToDeleteUserBtn = new JButton("Borrar usuario");
		goToDeleteUserBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		goToDeleteUserBtn.setForeground(new Color(0, 0, 0));
		goToDeleteUserBtn.setBackground(new Color(255, 255, 255));
		goToDeleteUserBtn.setBounds(startX + btnWidth + gap, 200, btnWidth, btnHeight);
		menuPane.add(goToDeleteUserBtn);

		JButton goToTestNews = new JButton("Probar noticias");
		goToTestNews.setFont(new Font("Tahoma", Font.BOLD, 14));
		goToTestNews.setForeground(new Color(0, 0, 0));
		goToTestNews.setBackground(new Color(255, 255, 255));
		goToTestNews.setBounds(startX + (btnWidth + gap) * 2, 200, btnWidth, btnHeight);
		menuPane.add(goToTestNews);
		
		JButton goToEmailBtn = new JButton("Enviar correo");
		goToEmailBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		goToEmailBtn.setForeground(new Color(0, 0, 0));
		goToEmailBtn.setBackground(new Color(255, 255, 255));
		goToEmailBtn.setBounds(startX + btnWidth / 2 + gap, 320, btnWidth, btnHeight); 
		menuPane.add(goToEmailBtn);

		createUserPane = new JLayeredPane();
		createUserPane.setLayout(null);
		createUserPane.setOpaque(true);
		createUserPane.setBackground(new Color(40, 40, 40));
		createUserPane = new JLayeredPane();
		createUserPane.setLayout(null);
		createUserPane.setOpaque(true);
		createUserPane.setBackground(new Color(40, 40, 40));
		createUserPane.setBounds(0, 0, 784, 540);
		createUserPane.setVisible(false);
		this.getContentPane().add(createUserPane);

		JLabel createUserLbl = new JLabel("Crear un usuario nuevo");
		createUserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		createUserLbl.setFont(new Font("Tahoma", Font.BOLD, 26));
		createUserLbl.setForeground(new Color(255, 255, 255));
		createUserLbl.setBounds(10, 10, 764, 50);
		createUserPane.add(createUserLbl);

		int labelX = 200;
		int fieldX = 350;
		int startY = 100;
		int spacing = 50;

		JLabel createUsernameLbl = new JLabel("Nombre:");
		createUsernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		createUsernameLbl.setForeground(new Color(255, 255, 255));
		createUsernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		createUsernameLbl.setBounds(labelX, startY, 130, 20);
		createUserPane.add(createUsernameLbl);

		usernameTField = new JTextField();
		usernameTField.setBounds(fieldX, startY, 200, 25);
		createUserPane.add(usernameTField);
		usernameTField.setColumns(10);

		JLabel passwordLbl = new JLabel("Contraseña:");
		passwordLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLbl.setForeground(Color.WHITE);
		passwordLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordLbl.setBounds(labelX, startY + spacing, 130, 20);
		createUserPane.add(passwordLbl);

		passwordTField = new JTextField();
		passwordTField.setColumns(10);
		passwordTField.setBounds(fieldX, startY + spacing, 200, 25);
		createUserPane.add(passwordTField);

		JLabel emailLbl = new JLabel("Email:");
		emailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLbl.setForeground(Color.WHITE);
		emailLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		emailLbl.setBounds(labelX, startY + spacing * 2, 130, 20);
		createUserPane.add(emailLbl);

		emailTField = new JTextField();
		emailTField.setColumns(10);
		emailTField.setBounds(fieldX, startY + spacing * 2, 200, 25);
		createUserPane.add(emailTField);

		JLabel roleLbl = new JLabel("Rol:");
		roleLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		roleLbl.setForeground(Color.WHITE);
		roleLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		roleLbl.setBounds(labelX, startY + spacing * 3, 130, 20);
		createUserPane.add(roleLbl);

		roleComboBox = new javax.swing.JComboBox<>(new String[] { "USER", "ADMIN" });
		roleComboBox.setBounds(fieldX, startY + spacing * 3, 200, 25);
		createUserPane.add(roleComboBox);

		JButton createUserBtn = new JButton("Crear");
		createUserBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		createUserBtn.setBounds(350, 400, 100, 30);
		createUserPane.add(createUserBtn);

		JButton backFromCreateBtn = new JButton("Volver");
		backFromCreateBtn.setBounds(10, 480, 88, 22);
		createUserPane.add(backFromCreateBtn);

		deleteUserPane = new JLayeredPane();
		deleteUserPane.setLayout(null);
		deleteUserPane.setOpaque(true);
		deleteUserPane.setBackground(new Color(40, 40, 40));
		deleteUserPane.setBounds(0, 0, 784, 540);
		deleteUserPane.setVisible(false);
		this.getContentPane().add(deleteUserPane);

		JLabel deleteUserLbl = new JLabel("Eliminar un usuario");
		deleteUserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		deleteUserLbl.setForeground(Color.WHITE);
		deleteUserLbl.setFont(new Font("Tahoma", Font.BOLD, 26));
		deleteUserLbl.setBounds(10, 30, 764, 50);
		deleteUserPane.add(deleteUserLbl);

		JLabel deleteUsernameLbl = new JLabel("Introduzca el nombre del usuario al borrar:");
		deleteUsernameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		deleteUsernameLbl.setForeground(Color.WHITE);
		deleteUsernameLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		deleteUsernameLbl.setBounds(10, 153, 320, 14);
		deleteUserPane.add(deleteUsernameLbl);

		deleteTField = new JTextField();
		deleteTField.setColumns(10);
		deleteTField.setBounds(340, 151, 86, 20);
		deleteUserPane.add(deleteTField);

		JButton backFromDeleteBtn = new JButton("Volver");
		backFromDeleteBtn.setBounds(10, 480, 88, 22);
		deleteUserPane.add(backFromDeleteBtn);

		JButton deleteUserBtn = new JButton("Borrar");
		deleteUserBtn.setBounds(340, 242, 88, 22);
		deleteUserPane.add(deleteUserBtn);

		goToCreateUserBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPane.setVisible(false);
				createUserPane.setVisible(true);
				deleteUserPane.setVisible(false);
				emailPane.setVisible(false);
			}
		});

		emailPane = new JLayeredPane();
		emailPane.setLayout(null);
		emailPane.setOpaque(true);
		emailPane.setBackground(new Color(40, 40, 40));
		emailPane.setBounds(0, 0, 784, 540);
		emailPane.setVisible(false);
		this.getContentPane().add(emailPane);

		JLabel emailHeaderLbl = new JLabel("Enviar correo a usuario");
		emailHeaderLbl.setHorizontalAlignment(SwingConstants.CENTER);
		emailHeaderLbl.setForeground(Color.WHITE);
		emailHeaderLbl.setFont(new Font("Tahoma", Font.BOLD, 26));
		emailHeaderLbl.setBounds(10, 30, 764, 50);
		emailPane.add(emailHeaderLbl);

		JLabel selectUserLbl = new JLabel("Seleccionar usuario:");
		selectUserLbl.setForeground(Color.WHITE);
		selectUserLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		selectUserLbl.setBounds(50, 100, 200, 20);
		emailPane.add(selectUserLbl);

		JComboBox<String> usersComboBox = new JComboBox<>();
		usersComboBox.setBounds(250, 100, 300, 25);
		emailPane.add(usersComboBox);

		JLabel infoLbl = new JLabel("<html>Se enviará un resumen de noticias<br>basado en las preferencias del usuario.</html>");
		infoLbl.setForeground(Color.WHITE);
		infoLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		infoLbl.setBounds(250, 140, 300, 40);
		emailPane.add(infoLbl);

		JButton sendEmailBtn = new JButton("Enviar Noticias");
		sendEmailBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		sendEmailBtn.setBounds(300, 200, 150, 30);
		emailPane.add(sendEmailBtn);

		JLabel statusLbl = new JLabel("");
		statusLbl.setHorizontalAlignment(SwingConstants.CENTER);
		statusLbl.setForeground(Color.YELLOW);
		statusLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		statusLbl.setBounds(200, 240, 350, 30);
		emailPane.add(statusLbl);

		JButton backFromEmailBtn = new JButton("Volver");
		backFromEmailBtn.setBounds(10, 480, 88, 22);
		emailPane.add(backFromEmailBtn);
		
		goToEmailBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPane.setVisible(false);
				createUserPane.setVisible(false);
				deleteUserPane.setVisible(false);
				emailPane.setVisible(true);
				statusLbl.setText(""); 
				
				
				usersComboBox.removeAllItems();
				for (User u : UserLogic.usersList) {
					usersComboBox.addItem(u.getEmail());
				}
			}
		});
		
		backFromEmailBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				emailPane.setVisible(false);
				menuPane.setVisible(true);
			}
		});
		
		sendEmailBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedEmail = (String) usersComboBox.getSelectedItem();
				
				if (selectedEmail == null) {
					JOptionPane.showMessageDialog(null, "Seleccione un usuario", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				User foundUser = null;
				for (User u : UserLogic.usersList) {
					if (u.getEmail().equals(selectedEmail)) {
						foundUser = u;
						break;
					}
				}
				final User targetUser = foundUser; 
				
				if (targetUser == null) return;

				
				sendEmailBtn.setEnabled(false);
				statusLbl.setText("Enviando mensaje, espere por favor...");
				
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							
							ArrayList<objects.NewsItem> news = programLogic.WebReader.getNews(targetUser.getPreferencesList(), false);
							
							
							String subject = "NOTICIAS DAM";
							StringBuilder body = new StringBuilder();
							
							body.append(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()) + "\n");
							body.append("--------------------------------------------------\n\n");
							
							String lastCat = "";
							for (objects.NewsItem item : news) {
								if (!item.getCategory().equals(lastCat)) {
									body.append("\n" + item.getCategory() + ":\n");
									lastCat = item.getCategory();
								}
								body.append(item.getHeadline());
								if (item.getUrl() != null && !item.getUrl().isEmpty()) {
									body.append(" [" + item.getUrl() + "]");
								}
								body.append("\n");
							}
							
							programLogic.EmailLogic.sendEmail(selectedEmail, subject, body.toString());
							
							SwingUtilities.invokeLater(() -> {
								statusLbl.setText("");
								sendEmailBtn.setEnabled(true);
								JOptionPane.showMessageDialog(null, "Resumen de noticias enviado a " + selectedEmail, "Éxito", JOptionPane.INFORMATION_MESSAGE);
							});
							
						} catch (Exception ex) {
							SwingUtilities.invokeLater(() -> {
								statusLbl.setText("");
								sendEmailBtn.setEnabled(true);
								JOptionPane.showMessageDialog(null, "Error al enviar correo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							});
						}
					}
				}).start();
			}
		});

		goToDeleteUserBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPane.setVisible(false);
				createUserPane.setVisible(false);
				deleteUserPane.setVisible(true);
			}
		});

		backFromCreateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createUserPane.setVisible(false);
				menuPane.setVisible(true);
			}
		});

		backFromDeleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteUserPane.setVisible(false);
				menuPane.setVisible(true);
			}
		});

		createUserBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				username = usernameTField.getText();
				password = passwordTField.getText();
				email = emailTField.getText();
				role = (String) roleComboBox.getSelectedItem(); 
				
				if (username.contains(";") || password.contains(";") || email.contains(";")) {
					JOptionPane.showMessageDialog(null, "Ese caracter (;) destruirá el formato del .txt...\nNo lo pongas porfa.", "Error de formato", JOptionPane.ERROR_MESSAGE);
					return; 
				}
				
				if (UserLogic.usersList.size() >= 10) {
					JOptionPane.showMessageDialog(null, "Has alcanzado el límite máximo de usuarios (10).", "Error de creación", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere crear el usuario?");
				if (JOptionPane.OK_OPTION == resp) {
					UserLogic.createNewUser(username, password, email, role);
					System.out.println("Usuario creado");
				} else {
					System.out.println("Rechazo de creación de usuario, volviendo...");
					return;
				}
			}
		});

		deleteUserBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (UserLogic.usersList.size() < 4) {
					JOptionPane.showMessageDialog(null, "No se pueden borrar más usuarios.\nDebe haber al menos 4 usuarios (1 admin + 3 usuarios).", "Error de borrado", JOptionPane.ERROR_MESSAGE);
					return;
				}

				username = deleteTField.getText(); 
				
				int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere borrar el usuario?");
				if (JOptionPane.OK_OPTION == resp) {
					UserLogic.deleteUser(username);
				} else {
					System.out.println("Rechazo de borrado de usuario, volviendo...");
					return;
				}
			}
		});

		goToTestNews.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				User adminTestUser = new User(user.getUsername(), user.getPassword(), user.getEmail(), "ADMIN", false);
				ArrayList<String> allCats = new ArrayList<>();
				allCats.add("ECONOMIA");
				allCats.add("DEPORTES");
				allCats.add("NACIONAL");
				allCats.add("INTERNACIONAL");
				allCats.add("VIDEOJUEGOS");
				allCats.add("TECNOLOGIA");
				adminTestUser.setPreferencesList(allCats);
				
				new MainWindow(adminTestUser);
			}
		});
	}
}
