package it.unibo.soseng.mdm.util;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

/**
 * Class used to send email using GMAIL services.
 * 
 * @author Davide Marchi
 *
 */
public class EmailSender {	
	private static final String GMAIL_HOST = "smtp.gmail.com";
	
	private Email email = new SimpleEmail();
	
	protected String username;
	protected String password;
	protected String name;
	protected String charset;
	protected Integer smtpPort;
	protected Boolean useSSL;
	
	public EmailSender() {
		
	}
	
	public EmailSender(String username, String password) {
		this.username = username;
		this.password = password;
		
		// Default values
		this.name = username;
		this.charset = "utf-8";
		this.smtpPort = 465;
		this.useSSL = true;
	}
	
	public EmailSender(String username, String password, String name) {
		this(username, password);
		this.name = name;
	}
	
	public EmailSender(String username, String password, String name, String charset, Integer smtpPort, Boolean useSSL) {
		this(username, password, name);
		this.charset = charset;
		this.smtpPort = smtpPort;
		this.useSSL = useSSL;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public Boolean getUseSSL() {
		return useSSL;
	}

	public void setUseSSL(Boolean useSSL) {
		this.useSSL = useSSL;
	}
	
	@Override
	public String toString() {
		return "EmailSender ["
				+ "email=" + email + ", "
				+ "username=" + username + ", "
				+ "password=" + password + ", "
				+ "name=" + name + ", "
				+ "charset=" + charset + ", "
				+ "smtpPort=" + smtpPort + ", "
				+ "useSSL=" + useSSL
				+ "]";
	}
	
	/**
	 * Configure the connection with Google Mail server
	 */
	public void configureConnection() {
		email.setCharset(charset);
		email.setHostName(GMAIL_HOST);
		email.setSmtpPort(smtpPort);
		email.setSSL(useSSL);
		email.setAuthentication(username, password);
	}
	
	/**
	 * Configure the connection with a custom mail server
	 * @param customHost The custom mail server
	 */
	public void configureConnection(String customHost) {
		configureConnection();
		email.setHostName(customHost);
	}
	
	/**
	 * Send the email to receiver.
	 * @param senderName The name of the sender
	 * @param subject The subject of the email
	 * @param message The message of the email
	 * @param receiverMail The mail address of the receiver
	 * @return
	 */
	public Boolean send(String subject, String message, String receiverMail) {
		try {
			email.setFrom(username, name);
			email.setSubject(subject);
			email.setMsg(message);
			email.addTo(receiverMail);
			email.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
