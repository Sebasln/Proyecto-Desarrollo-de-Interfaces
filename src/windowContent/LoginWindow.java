package windowContent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import fileLogic.UserLogic;
import objects.User;

public class LoginWindow extends JFrame {
	private JTextField usernameTField;
	private JPasswordField passwordTField; // Cambiado a JPasswordField

	public LoginWindow() {
		initialize();
		ImageIcon icono = new ImageIcon("images/logoNoticias.png");
		this.setIconImage(icono.getImage());
		this.setVisible(true);
	}

	private void initialize() {
		this.getContentPane().setBackground(new Color(40, 40, 40));
		this.setBounds(100, 100, 450, 350); // Ventana más pequeña
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Cambiado a DO_NOTHING
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

		int centerX = 225; // Centro de la ventana (450 / 2)
		int width = 200;   // Ancho unificado para labels y fields
		int xPos = centerX - (width / 2); // Posición X centrada

		JLabel headerLabel = new JLabel("Inicio de sesión");
		headerLabel.setForeground(new Color(255, 255, 255));
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerLabel.setBounds(xPos, 30, width, 29);
		this.getContentPane().add(headerLabel);

		JLabel usernameLabel = new JLabel("Nombre de usuario");
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		usernameLabel.setForeground(new Color(255, 255, 255));
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setBounds(xPos, 80, width, 14);
		this.getContentPane().add(usernameLabel);

		usernameTField = new JTextField();
		usernameTField.setBounds(xPos, 105, width, 20);
		this.getContentPane().add(usernameTField);
		usernameTField.setColumns(10);

		JLabel passwordLabel = new JLabel("Contraseña");
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordLabel.setForeground(new Color(255, 255, 255));
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setBounds(xPos, 145, width, 14);
		this.getContentPane().add(passwordLabel);

		// Campo de contraseña usando JPasswordField
		passwordTField = new JPasswordField();
		passwordTField.setBounds(xPos, 170, width, 20);
		this.getContentPane().add(passwordTField);
		passwordTField.setColumns(10);
		
		// --- BOTÓN OJO PARA VER/OCULTAR CONTRASEÑA ---
		ImageIcon ojoAbiertoIcon = new ImageIcon("images/ojoAbierto.jpg");
		Image imgAbierto = ojoAbiertoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon iconAbierto = new ImageIcon(imgAbierto);
		
		ImageIcon ojoCerradoIcon = new ImageIcon("images/ojoCerrado.jpg");
		Image imgCerrado = ojoCerradoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon iconCerrado = new ImageIcon(imgCerrado);
		
		JToggleButton togglePasswordBtn = new JToggleButton();
		// Lo ponemos justo a la derecha del campo de contraseña
		togglePasswordBtn.setBounds(xPos + width + 5, 169, 25, 22); 
		togglePasswordBtn.setIcon(iconCerrado);
		togglePasswordBtn.setToolTipText("Mostrar/Ocultar contraseña");
		
		togglePasswordBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (togglePasswordBtn.isSelected()) {
					passwordTField.setEchoChar((char)0);
					togglePasswordBtn.setIcon(iconAbierto);
				} else {
					passwordTField.setEchoChar('•');
					togglePasswordBtn.setIcon(iconCerrado);
				}
			}
		});
		this.getContentPane().add(togglePasswordBtn);

		JButton loginButton = new JButton("Iniciar sesión");
		loginButton.setBounds(centerX - 56, 230, 112, 23); // Centrado
		this.getContentPane().add(loginButton);

		JLabel passwordWarningLabel = new JLabel("Este campo es obligatorio");
		passwordWarningLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordWarningLabel.setVisible(false);
		passwordWarningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordWarningLabel.setForeground(new Color(255, 32, 32));
		passwordWarningLabel.setBounds(xPos, 195, width, 14);
		this.getContentPane().add(passwordWarningLabel);

		JLabel usernameWarningLabel = new JLabel("Este campo es obligatorio");
		usernameWarningLabel.setForeground(new Color(255, 32, 32));
		usernameWarningLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		usernameWarningLabel.setVisible(false);
		usernameWarningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameWarningLabel.setBounds(xPos, 128, width, 14);
		this.getContentPane().add(usernameWarningLabel);
		
		JLabel autenticationFailedLabel = new JLabel("Usuario o contraseña incorrectos");
		autenticationFailedLabel.setForeground(new Color(255, 32, 32));
		autenticationFailedLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		autenticationFailedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		autenticationFailedLabel.setVisible(false);
		autenticationFailedLabel.setBounds(xPos, 260, width, 14);
		this.getContentPane().add(autenticationFailedLabel);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(passwordTField.getPassword());
				String username = usernameTField.getText();
				if (username.isEmpty() || password.isEmpty()) {
					checkFieldsNullity(username, password, usernameWarningLabel, passwordWarningLabel,
							autenticationFailedLabel);
					return;
				}
				System.out.println(username + ", " + password);

				for (User u : UserLogic.usersList) {
					String loadedUser = u.getUsername();		
					String loadedPassword = u.getPassword();

					if (loadedUser.equals(username) && loadedPassword.equals(password)) {
						if (u.getRole().toLowerCase().equals("admin")) {
							new AdminWindow(u); 
						} else {
							if (u.isNew() == true) {
								new UserConfigWindow(u);
							} else {
								new MainWindow(u); 
							}
						}
						LoginWindow.this.dispose();
						return;
					}
				}
				autenticationFailedLabel.setVisible(true);
			}
		});
	}

	public void checkFieldsNullity(String us, String pas, JLabel usWarn, JLabel passWarn, JLabel aFailed) {
		if ((us.equals("") || us == null) && (pas.equals("") || pas == null)) {
			passWarn.setVisible(true);
			usWarn.setVisible(true);
		} else if((us.equals("") || us == null) && (!(pas.equals("")) || pas != null)) {
			usWarn.setVisible(true);
		} else if((pas.equals("") || pas == null) && (!(us.equals("")) || us != null)) {
			passWarn.setVisible(true);
		} else{
			usWarn.setVisible(false);
			passWarn.setVisible(false);
			aFailed.setVisible(false);
		}
	}
}