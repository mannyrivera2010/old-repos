package org.shared.mailing;
/**
 * @author Emanuel Rivera
 */

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;

import org.logging.LogFileLimit;

import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class Mail.
 */
public class Mail {
	
	/** The logg. */
	private LogFileLimit logg = new LogFileLimit();
	
	/** The user_auth. */
	private String user_auth = "";
	
	/** The user_password. */
	private String user_password = "";
	
	/** The host. */
	private String host = "";
	
	/** The port. */
	private String port = "";
	
	/** The from. */
	private String from = "";
	
	/** The Server. */
	private String Server = "";

	/**
	 * Sets the user auth.
	 *
	 * @param User_auth the user_auth
	 * @param User_password the user_password
	 * @param Host the host
	 * @param Port the port
	 * @param SendAS the send as
	 * @param Exchange the exchange
	 */
	public void setUserAuth(String User_auth, String User_password,
			String Host, String Port, String SendAS, String Exchange) {
		user_auth = User_auth.trim();
		user_password = User_password.trim();

		host = Host.trim();
		port = Port.trim();
		from = SendAS.trim();
		Server = Exchange.trim().toLowerCase();
	}

	/**
	 * Send mail.
	 *
	 * @param to the to
	 * @param StrCC the str cc
	 * @param Subject the subject
	 * @param MessageText the message text
	 */
	public void SendMail(String to, String StrCC, String Subject,
			String MessageText) {

		Properties mailProp = new Properties();
		mailProp.put("mail.smtp.host", host);
		mailProp.put("mail.smtp.port", port);
		/*
		 * It’s Comming the TLS settings
		 */
		mailProp.put("mail.smtp.starttls.enable", "true");
		mailProp.put("mail.smtp.auth", "true");
		mailProp.put("mail.smtp.socketFactory.port", port);

		if (!Server.equals("yes")) {
			mailProp.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			// mailProp.put("mail.smtp.auth.plain.disable","true");

		}

		mailProp.put("mail.smtp.socketFactory.fallback", "false");

		// Optional debug
		mailProp.put("mail.debug", "true");

		try {
			/*
			 * Now comming the Real Authentication via the Autheticator Class,
			 * off course i need to overwrite
			 */
			Authenticator myAuth = new MyAuthenticator();
			Session session = Session.getDefaultInstance(mailProp, myAuth);

			// Session Debug ON
			session.setDebug(true);

			MimeMessage mymsg = new MimeMessage(session);
			mymsg.setText(MessageText);
			mymsg.setSubject(Subject);
			mymsg.setFrom(new InternetAddress(from));

			mymsg.setRecipient(Message.RecipientType.TO,
					new InternetAddress(to));

			if (StrCC.trim().length() != 0) {
				mymsg.setRecipient(Message.RecipientType.CC,
						new InternetAddress(StrCC));
			}

			Transport.send(mymsg);
			session.getDebugOut();

		} catch (Exception e) {
			logg.severe("email","Could not Email\n" + e.getMessage()+"---START---\n"+
					to+"\t"+StrCC+"\t"+Subject+"\n"+MessageText.trim()+"\n---END---");
	
			//e.printStackTrace();
			
			
		}
	}

	/**
	 * The Class MyAuthenticator.
	 */
	private class MyAuthenticator extends javax.mail.Authenticator {
		
		/* (non-Javadoc)
		 * @see javax.mail.Authenticator#getPasswordAuthentication()
		 */
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user_auth, user_password);
		}
	}

}
