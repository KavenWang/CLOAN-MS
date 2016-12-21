package com.uisftech.cloan.common.util;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {
	private final Logger logger = LoggerFactory.getLogger(MailSender.class);

	private MimeMessage message;
	private Session session;
	private Transport transport;

	private  String host;
	private  String type;
	private  String user;
	private  String psd;
	
	

	/** 
	 * Creates a new instance of MailSender. 
	 */  
	public MailSender(String host, boolean auth, boolean starttlsEnable, String port, String type, String user,
			String psd, boolean debug) {
		super();
		this.host = host;
		this.type = type;
		this.user = user;
		this.psd = psd;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", starttlsEnable);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.user", user);
		props.put("mail.smtp.password", psd);

		HoperunSCFSAuthenticator authenticator = new HoperunSCFSAuthenticator(user, psd);
		session = Session.getDefaultInstance(props, authenticator);
		session.setDebug(debug);
		message = new MimeMessage(session);
	}


	public void sendEmail(String receiveList ,String subject, String content) {
		try {
			InternetAddress from = new InternetAddress(user);
			message.setFrom(from);
			InternetAddress[] toList = InternetAddress.parse(receiveList);
			message.setRecipients(Message.RecipientType.TO, toList);
			message.setSubject(subject);
			Multipart multipart = new MimeMultipart();
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(content, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);
			message.setContent(multipart);
			message.saveChanges();
			transport = session.getTransport(type);
			transport.connect(host, user, psd);
			transport.sendMessage(message, message.getAllRecipients());

			logger.info("send success!");
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					logger.error(e.toString());
				}
			}
		}
	}

	/**
	 * 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param emailTo
	 * @param subject
	 * @param sendHtml
	 * @param attachments
	 */
	public void sendEmailWithAttachment(String receiveList, String subject, String sendHtml, File...attachments) {
		try {
			// 发件人
			InternetAddress from = new InternetAddress(user);
			message.setFrom(from);
			InternetAddress[] toList = InternetAddress.parse(receiveList);
			message.setRecipients(Message.RecipientType.TO, toList);
			message.setSubject(subject);
			Multipart multipart = new MimeMultipart();
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);
			if (null != attachments && attachments.length > 0) {
				for (int i = 0; i < attachments.length; i++) {
					if (attachments[i] != null) {
						BodyPart attachmentBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(attachments[i]);
						attachmentBodyPart.setDataHandler(new DataHandler(source));

						attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachments[i].getName()));
						multipart.addBodyPart(attachmentBodyPart);
					}
				}
			}

			message.setContent(multipart);
			message.saveChanges();

			transport = session.getTransport(type);
			transport.connect(host, user, psd);
			transport.sendMessage(message, message.getAllRecipients());

			logger.info("send success!");

			for (int i = 0; i < attachments.length; i++) {
				File file = attachments[i];
				if (file.exists()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					logger.error(e.toString());
				}
			}
		}
	}

	class HoperunSCFSAuthenticator extends Authenticator {
		String name;
		String password;

		public HoperunSCFSAuthenticator(String name, String password) {
			this.name = name;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(name, password);
		}
	}
}