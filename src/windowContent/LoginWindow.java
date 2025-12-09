package windowContent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fileLogic.UserLogic;
import objects.User;

public class LoginWindow extends JFrame {
	private JTextField usernameTField;
	private JTextField passwordTField;

	public LoginWindow() {
		initialize();
		ImageIcon icono = new ImageIcon("images/logoNoticias.png");
		this.setIconImage(icono.getImage());
		this.setVisible(true);
	}

	private void initialize() {
		this.getContentPane().setBackground(new Color(40, 40, 40));
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setSize(800, 600);

		JLabel usernameLabel = new JLabel("Nombre de usuario");
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		usernameLabel.setForeground(new Color(255, 255, 255));
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setBounds(135, 64, 136, 14);
		this.getContentPane().add(usernameLabel);

		usernameTField = new JTextField();
		usernameTField.setBounds(156, 89, 86, 20);
		this.getContentPane().add(usernameTField);
		usernameTField.setColumns(10);

		JLabel passwordLabel = new JLabel("Contraseña");
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordLabel.setForeground(new Color(255, 255, 255));
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setBounds(156, 143, 86, 14);
		this.getContentPane().add(passwordLabel);

		passwordTField = new JTextField();
		passwordTField.setBounds(156, 168, 86, 20);
		this.getContentPane().add(passwordTField);
		passwordTField.setColumns(10);

		JLabel headerLabel = new JLabel("Inicio de sesión");
		headerLabel.setForeground(new Color(255, 255, 255));
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		headerLabel.setBounds(135, 11, 158, 29);
		this.getContentPane().add(headerLabel);

		JButton loginButton = new JButton("Iniciar sesión");
		loginButton.setBounds(148, 227, 112, 23);
		this.getContentPane().add(loginButton);

		JLabel passwordWarningLabel = new JLabel("Este campo es obligatorio");
		passwordWarningLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordWarningLabel.setVisible(false);
		passwordWarningLabel.setForeground(new Color(255, 32, 32));
		passwordWarningLabel.setBounds(124, 199, 158, 14);
		this.getContentPane().add(passwordWarningLabel);

		JLabel usernameWarningLabel = new JLabel("Este campo es obligatorio");
		usernameWarningLabel.setForeground(new Color(255, 32, 32));
		usernameWarningLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		usernameWarningLabel.setVisible(false);
		usernameWarningLabel.setBounds(124, 120, 158, 14);
		this.getContentPane().add(usernameWarningLabel);

		JLabel autenticationFailedLabel = new JLabel("Usuario o contraseña incorrectos");
		autenticationFailedLabel.setForeground(new Color(255, 32, 32));
		autenticationFailedLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		autenticationFailedLabel.setVisible(false);
		autenticationFailedLabel.setBounds(120, 250, 200, 14);
		this.getContentPane().add(autenticationFailedLabel);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = passwordTField.getText();
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

					System.out.println(loadedUser + ", " + loadedPassword);

					if (loadedUser.equals(username) && loadedPassword.equals(password)) {
						if (u.getRole().equalsIgnoreCase("ADMIN")) {
							new AdminWindow(u); 
						} else {
							if (u.getPreferencesList() == null || u.getPreferencesList().isEmpty()) {
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