package edu.tp.paw.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.service.IEmailService;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.User;

@Service
public class EmailService implements IEmailService {
	private final static Logger logger = LoggerFactory.getLogger(EmailService.class);

	private final static String HOST = "smtp.gmail.com";
	private final static String PORT = "25";
	private final static String FROM = "siglas.commerce.paw@gmail.com";
	private final static String USER = "siglas.commerce.paw@gmail.com";
	private final static String PASS = "SiglasCommercePaw.1";
	
	private Session session;
	private Properties props = new Properties();


	public EmailService() {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", "true");
		final Authenticator auth = getAuthenticator(USER, PASS);
		session = Session.getInstance(props, auth);
	}

	public EmailService(Session session) {
		this.session = session;
	}

	private static Authenticator getAuthenticator(final String user, final String pass) {
		final Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		};
		return auth;
	}

	private Message createNewMessage(final String from, final String to, final String subject, final String body) throws MessagingException {
		final MimeMessage msg = new MimeMessage(session);
		msg.addHeader("Content-type", "text/html; charset=utf8-8");
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");

		try {
			msg.setFrom(new InternetAddress(FROM, "Siglas Commerce"));
		} catch (UnsupportedEncodingException e) {
			msg.setFrom(InternetAddress.getLocalAddress(session));
		}
		msg.setReplyTo(InternetAddress.parse(FROM, false));

		msg.setSubject(subject);
		msg.setText(body, "utf-8", "html");

		msg.setSentDate(new Date());
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

		return msg;
	}

	@Override
	@Async
	public boolean sendRawEmail(final User user, final String subject, final String body) {
		try {
			final Message message = createNewMessage(FROM, user.getEmail(), subject, body);
			logger.debug("Sending message {}", message);
			Transport.send(message);
		} catch (final MessagingException e) {
			logger.error("Message delivery for {} failed", user.getUsername(), e);
			return false;
		}
		return true;
	}

	@Override
	public boolean greet(final User user) {
		
		
		return sendRawEmail(user, "Bienvenido a Siglas Commerce", String.format("Hola %s %s, le damos la bienvenida a Siglas Commerce. Puede acceder <a href=\"http://pawserver.it.itba.edu.ar/paw-2016b-05/\">aqui</a>", user.getFirstName(), user.getLastName()));
	}

	@Override
	public boolean notifySale(final User user, final Purchase sale) {
		
		return sendRawEmail(user, "Nueva Venta", String.format("Hola %s %s, le informamos que ha vendido un articulo. Puede acceder <a href=\"http://pawserver.it.itba.edu.ar/paw-2016b-05/\">aqui</a>", user.getFirstName(), user.getLastName()));
	}

	@Override
	public boolean notifyPurchaseApproval(final User user, final Purchase purchase) {
		
		return sendRawEmail(user, "Se ha aprobado su compra", String.format("Hola %s %s, le informamos que se ha confirmado su compra. Puede acceder <a href=\"http://pawserver.it.itba.edu.ar/paw-2016b-05/\">aqui</a>", user.getFirstName(), user.getLastName()));
		
	}

	@Override
	public boolean notifyPurchaseDeclined(final User user, final Purchase purchase) {
		
		return sendRawEmail(user, "Se ha rechazado su compra", String.format("Hola %s %s, le informamos que se ha rechazado su compra. Puede acceder <a href=\"http://pawserver.it.itba.edu.ar/paw-2016b-05/\">aqui</a>", user.getFirstName(), user.getLastName()));
		
	}

}
