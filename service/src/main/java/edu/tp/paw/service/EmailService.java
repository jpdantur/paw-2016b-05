package edu.tp.paw.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import edu.tp.paw.interfaces.service.IEmailService;
import edu.tp.paw.interfaces.service.IPasswordRecoveryService;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseReview;
import edu.tp.paw.model.User;

@Service
public class EmailService implements IEmailService {
	private final static Logger logger = LoggerFactory.getLogger(EmailService.class);

	private final static String HOST = "smtp.gmail.com";
	private final static String PORT = "25";
	private final static String FROM = "siglas.commerce.paw@gmail.com";
	private final static String USER = "siglas.commerce.paw@gmail.com";
	private final static String PASS = "SiglasCommercePaw.1";
	private final static String URL = "http://pawserver.it.itba.edu.ar/paw-2016b-05";
	
	private Session session;
	private Properties props = new Properties();
	private VelocityEngine velocityEngine;

	
	@Autowired private IPasswordRecoveryService passwordRecoveryService;

	public EmailService() {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", "true");
		final Authenticator auth = getAuthenticator(USER, PASS);
		session = Session.getInstance(props, auth);
		velocityEngine = new VelocityEngine();
		
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		
		velocityEngine.init();
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
		
		final Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put("url", URL);
		
		final String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/greeting.vm", "utf-8", model);
		
		return sendRawEmail(user, "Bienvenido a Siglas Commerce", body);
	}

	@Override
	public boolean notifySale(final User user, final Purchase sale) {
		
		final Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put("item", sale.getItem());
		model.put("url", URL);
		
		final String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/sale.vm", "utf-8", model);
		
		return sendRawEmail(user, "Nueva Venta en Siglas Commerce", body);
	}

	@Override
	public boolean notifyPurchaseApproval(final User user, final Purchase purchase) {
		
		final Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put("item", purchase.getItem());
		model.put("url", URL);
		
		final String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/purchase.vm", "utf-8", model);
		
		return sendRawEmail(user, "Aprobación de Compra en Siglas Commerce", body);
		
	}

	@Override
	public boolean notifyPurchaseDeclined(final User user, final Purchase purchase) {
		
		
		final Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put("item", purchase.getItem());
		model.put("url", URL);
		
		final String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/purchase-declined.vm", "utf-8", model);
		
		return sendRawEmail(user, "Compra denegada en Siglas Commerce", body);
		
	}

	@Override
	public boolean notifySellerAboutReview(final User user, final Purchase purchase, final PurchaseReview review) {
		
		final Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put("item", purchase.getItem());
		model.put("review", review);
		model.put("url", URL);
		
		final String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/seller-review.vm", "utf-8", model);
		
		return sendRawEmail(user, "Le han calificado en Siglas Commerce", body);
	}

	@Override
	public boolean notifyBuyerAboutReview(final User user, final Purchase purchase, final PurchaseReview review) {
		final Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put("item", purchase.getItem());
		model.put("review", review);
		model.put("url", URL);
		
		final String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/buyer-review.vm", "utf-8", model);
		
		return sendRawEmail(user, "Le han calificado en Siglas Commerce", body);
	}

	@Override
	public boolean sendPasswordRecovery(final User user) {
		
		final String token = passwordRecoveryService.generatePasswordRecoveryToken(user);
		
		logger.debug("issued token: <{}> for user {}", token, user.getUsername());
		
		final Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put("token", token);
		model.put("url", URL);
		
		final String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/password-recovery.vm", "utf-8", model);
		
		return sendRawEmail(user, "Pedido de Recuperacion de Contrasena", body);
	}

}
