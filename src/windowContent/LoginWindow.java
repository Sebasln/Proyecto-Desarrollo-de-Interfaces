package windowContent;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginWindow extends JFrame {
	private JTextField usernameTField;
	private JTextField passwordTField;

	public LoginWindow() {
		initialize();
		this.setVisible(true);
	}

	private void initialize() {
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(new Color(40, 40, 40));
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

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
	}
}
