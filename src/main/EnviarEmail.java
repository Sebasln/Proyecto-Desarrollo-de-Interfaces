package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.swing.JButton;

public class EnviarEmail {

	private JFrame frame;
	private JTextField textAsunto;
	private JTextField textCuerpo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnviarEmail window = new EnviarEmail();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EnviarEmail() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final String fromEmail = "sebastian.silva1.dosa@gmail.com"; //EMAIL DE SALIDA
		final String password = "scwi osdz jpta kzeq"; //CONTRASEÑA DEL EMAIL DE SALIDA (aplicaciones de 3ros) Contraseñas de aplicación -- Verificación en 2 pasos
		final String toEmail = "fp.ssilvanarro@salesianosdosa.com"; // EMAIL DESTINATARIO
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP de GMAIL en este caso
		props.put("mail.smtp.socketFactory.port", "465"); //PUERTO SSL 
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
		props.put("mail.smtp.auth", "true"); //ACTIVAR SMTP AUTENTIFICACI�N
		props.put("mail.smtp.port", "465"); //SMTP Port		
		Authenticator auth = new Authenticator() {		
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};		
		Session session = Session.getDefaultInstance(props, auth);//CREA UNA SESIÓN CON TODAS LAS PROPIEDADES Y EL "LOGIN"
		System.out.println("Sesión Creada");
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textAsunto = new JTextField();
		textAsunto.setBounds(138, 72, 86, 20);
		frame.getContentPane().add(textAsunto);
		textAsunto.setColumns(10);
		
		JLabel labelAsunto = new JLabel("Asunto");
		labelAsunto.setBounds(138, 47, 46, 14);
		frame.getContentPane().add(labelAsunto);
		
		JLabel labelCuerpo = new JLabel("Cuerpo");
		labelCuerpo.setBounds(138, 103, 46, 14);
		frame.getContentPane().add(labelCuerpo);
		
		textCuerpo = new JTextField();
		textCuerpo.setBounds(138, 128, 86, 20);
		frame.getContentPane().add(textCuerpo);
		textCuerpo.setColumns(10);
		
		JButton btnNewButton = new JButton("Enviar correo");
		btnNewButton.setBounds(122, 159, 117, 23);
		frame.getContentPane().add(btnNewButton);
		
		
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				EmailUtil.sendEmail(session, toEmail,textAsunto.getText(), textCuerpo.getText());
			}
		});
			
		
				
	}
}
