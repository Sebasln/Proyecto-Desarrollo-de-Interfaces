package main;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {

	public static void main(String[] args) {
		System.out.println("Soy el main del proyecto");
		System.out.println("Prueba");
		System.out.println("Prueba 2");
	}

	public static void recursosEmail() {
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
		//recursosEmail(session, toEmail,textAsunto.getText(), textCuerpo.getText());
	}
	public static void enviarEmail(Session session, String toEmail, String asunto, String cuerpo) {
		try{
		      MimeMessage msg = new MimeMessage(session);
		      //Configurar Cabeceras
		      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		      msg.addHeader("format", "flowed");
		      msg.addHeader("Content-Transfer-Encoding", "8bit");
		      msg.setFrom(new InternetAddress("no_reply@example.com", "NO BIZUM NO RESPONDER"));//Datos de ejemplo	      	      
		      msg.setReplyTo(InternetAddress.parse("no_reply_DOSA@DAM.com", false));	      
		      msg.setSubject(asunto, "UTF-8");
		      msg.setText(cuerpo, "UTF-8");
		      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));	   
		      System.out.println("MENSAJE CREADO");  	  
		      Transport.send(msg);
		      System.out.println("¡EMAIL ENVIADO!");//SI NO DA ERROR
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
	}	
}