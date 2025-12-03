package otherResources;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class SimpleEmail {	
	/**
	   Outgoing Mail (SMTP) Server
	   requires TLS or SSL: smtp.gmail.com (SSL)
	   Use Authentication: Yes
	   Port for SSL: 465
	 */
	public static void main(String[] args) {
		
		final String fromEmail = "sebastian.silva1.dosa@gmail.com"; //EMAIL DE SALIDA
		final String password = "scwi osdz jpta kzeq"; //CONTRASEÑA DEL EMAIL DE SALIDA (aplicaciones de 3ros) Contraseñas de aplicación -- Verificación en 2 pasos
		final String toEmail = "fp.ssilvanarro@salesianosdosa.com"; // EMAIL DESTINATARIO
		
		System.out.println("Configurando datos conexión SSL");
		
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
		
		/**
		 * Llamada al método sendEmail con todos los datos configurados
		 * session 
		 * toEmail 
		 * subject
		 * body 
		 */		
	    EmailUtil.sendEmail(session, toEmail,"ASUNTO", "MENSAJE/CUERPO");
	}
}

