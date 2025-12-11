package windowContent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fileLogic.UserLogic;

import javax.swing.ImageIcon;
import objects.User;

public class AdminWindow extends JFrame {

	private User user;
	private JLayeredPane menuPane;
	private JLayeredPane createUserPane;
	private JLayeredPane deleteUserPane;

	private JTextField usernameTField;
	private JTextField deleteTField;
	private JTextField passwordTField;

	public String username;
	public String password;
	
	public AdminWindow(User user) {
		this.user = user;
		initialize();
		ImageIcon icono = new ImageIcon("images/logoNoticias.png");
		this.setIconImage(icono.getImage());
		this.setVisible(true);
	}

	private void initialize() {
		this.setBounds(100, 100, 600, 400);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		
		// --- BOTÓN ACERCA DE ---
		JButton btnAcercaDe = new JButton("Acerca de");
		btnAcercaDe.setBounds(460, 330, 100, 22); // En el menú principal de admin
		btnAcercaDe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				javax.swing.JOptionPane.showMessageDialog(null, 
						"Proyecto DAM 25/26\nDesarrollado por: [Tu Nombre/Grupo]\nVersión 1.0", 
						"Acerca de", javax.swing.JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menuPane.add(btnAcercaDe);
		
		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);

		menuPane = new JLayeredPane();
		menuPane.setLayout(null);
		menuPane.setOpaque(true);
		menuPane.setBackground(new Color(40, 40, 40));
		menuPane.setBounds(0, 0, 584, 361);
		this.getContentPane().add(menuPane);

		JLabel headerAdminLbl = new JLabel("Menú de admin");
		headerAdminLbl.setHorizontalAlignment(SwingConstants.CENTER);
		headerAdminLbl.setForeground(Color.WHITE);
		headerAdminLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		headerAdminLbl.setBounds(10, 11, 566, 73);
		menuPane.add(headerAdminLbl);

		JButton goToCreateUserBtn = new JButton("Crear usuario");
		goToCreateUserBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		goToCreateUserBtn.setForeground(new Color(0, 0, 0));
		goToCreateUserBtn.setBackground(new Color(255, 255, 255));
		goToCreateUserBtn.setBounds(43, 156, 137, 85);
		menuPane.add(goToCreateUserBtn);

		JButton goToDeleteUserBtn = new JButton("Borrar usuario");
		goToDeleteUserBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		goToDeleteUserBtn.setForeground(new Color(0, 0, 0));
		goToDeleteUserBtn.setBackground(new Color(255, 255, 255));
		goToDeleteUserBtn.setBounds(223, 156, 137, 85);
		menuPane.add(goToDeleteUserBtn);

		JButton goToTestNews = new JButton("Probar noticias");
		goToTestNews.setFont(new Font("Tahoma", Font.BOLD, 14));
		goToTestNews.setForeground(new Color(0, 0, 0));
		goToTestNews.setBackground(new Color(255, 255, 255));
		goToTestNews.setBounds(403, 156, 137, 85);
		menuPane.add(goToTestNews);

		JButton backMainBtn = new JButton("Ir al main");
		backMainBtn.setBounds(10, 330, 98, 22);
		menuPane.add(backMainBtn);

		createUserPane = new JLayeredPane();
		createUserPane.setLayout(null);
		createUserPane.setOpaque(true);
		createUserPane.setBackground(new Color(40, 40, 40));
		createUserPane.setBounds(0, 0, 584, 361);
		createUserPane.setVisible(false);
		this.getContentPane().add(createUserPane);

		JLabel createUserLbl = new JLabel("Crear un usuario nuevo");
		createUserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		createUserLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		createUserLbl.setForeground(new Color(255, 255, 255));
		createUserLbl.setBounds(10, 0, 566, 73);
		createUserPane.add(createUserLbl);

		JLabel createUsernameLbl = new JLabel("Introduzca el nombre del nuevo usuario:");
		createUsernameLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		createUsernameLbl.setForeground(new Color(255, 255, 255));
		createUsernameLbl.setHorizontalAlignment(SwingConstants.LEFT);
		createUsernameLbl.setBounds(164, 93, 280, 14);
		createUserPane.add(createUsernameLbl);

		usernameTField = new JTextField();
		usernameTField.setBounds(252, 127, 86, 20);
		createUserPane.add(usernameTField);
		usernameTField.setColumns(10);

		JButton backFromCreateBtn = new JButton("Volver");
		backFromCreateBtn.setBounds(10, 330, 88, 22);
		createUserPane.add(backFromCreateBtn);

		JLabel passwordLbl = new JLabel("Introduzca la contraseña del usuario:");
		passwordLbl.setHorizontalAlignment(SwingConstants.LEFT);
		passwordLbl.setForeground(Color.WHITE);
		passwordLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		passwordLbl.setBounds(164, 172, 280, 14);
		createUserPane.add(passwordLbl);

		passwordTField = new JTextField();
		passwordTField.setColumns(10);
		passwordTField.setBounds(252, 211, 86, 20);
		createUserPane.add(passwordTField);

		JButton createUserBtn = new JButton("Crear");
		createUserBtn.setBounds(252, 290, 88, 22);
		createUserPane.add(createUserBtn);

		deleteUserPane = new JLayeredPane();
		deleteUserPane.setLayout(null);
		deleteUserPane.setOpaque(true);
		deleteUserPane.setBackground(new Color(40, 40, 40));
		deleteUserPane.setBounds(0, 0, 584, 361);
		deleteUserPane.setVisible(false);
		this.getContentPane().add(deleteUserPane);

		JLabel deleteUserLbl = new JLabel("Eliminar un usario");
		deleteUserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		deleteUserLbl.setForeground(Color.WHITE);
		deleteUserLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		deleteUserLbl.setBounds(10, 11, 566, 73);
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
		backFromDeleteBtn.setBounds(10, 330, 88, 22);
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
				
				int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere crear el usuario?");
				if (JOptionPane.OK_OPTION == resp) {
					UserLogic.createNewUser(username, password);
					//Cuando con poner userX; porque me hace mierda el codigo, prohibir ";"
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
				new MainWindow(user);
			}
		});
	}
}
