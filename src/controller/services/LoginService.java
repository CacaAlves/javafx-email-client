package controller.services;


import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import controller.EmailLoginResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.EmailAccount;
import view.EmailManager;

public class LoginService extends Service<EmailLoginResult> {

	private EmailAccount emailAccount;
	private EmailManager emailManager;
	
	public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
		this.emailAccount = emailAccount;
		this.emailManager = emailManager;
	}



	private EmailLoginResult login() {
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
			}
		};
		
		try {
			Thread.sleep(3000);
			Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
			emailAccount.setSession(session);
			Store store = session.getStore("imaps");
			store.connect(emailAccount.getProperties().getProperty("incomingHost"), 
					emailAccount.getAddress(), 
					emailAccount.getPassword());
			emailAccount.setStore(store);
			emailManager.addEmailAccount(emailAccount);
		}
		catch (NoSuchProviderException e) {
			e.printStackTrace();
			return EmailLoginResult.FAILED_BY_NETWORK_ERROR;
		}
		catch (AuthenticationFailedException e) {
			e.printStackTrace();
			return EmailLoginResult.FAILED_BY_CREDENTIALS;
		}
		catch (MessagingException e) {
			e.printStackTrace();
			return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		return EmailLoginResult.SUCCESS; 
	}



	@Override
	protected Task<EmailLoginResult> createTask() {
		return new Task<EmailLoginResult>() {

			@Override
			protected EmailLoginResult call() throws Exception {
				return login();
			}
			
		};
	}
}
