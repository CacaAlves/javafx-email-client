package model;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class EmailTreeItem<String> extends TreeItem<String>{
	
	private String name;
	private ObservableList<EmailMessage> emailMessages;
	private Integer unreadMessagesCounter;
	
	public EmailTreeItem(String name) {
		super(name);
		this.name = name;
		this.emailMessages = FXCollections.observableArrayList();
		this.unreadMessagesCounter = 0;
	}
	
	public ObservableList<EmailMessage> getEmailMessages() {
		return emailMessages;
	}
	
	public void addEmail(Message message) {
		EmailMessage emailMessage = fetchMessage(message);
		
	}
	
	public void addEmailToTop(Message message) {
		EmailMessage emailMessage = fetchMessage(message);
		emailMessages.add(0, emailMessage);
	}
	
	private EmailMessage fetchMessage(Message message) {
		EmailMessage emailMessage = null;
		try {
			boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
			
			emailMessage = new EmailMessage(
					message.getSubject(), 
					message.getFrom()[0].toString(),
					message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
					message.getSize(),
					message.getSentDate(), 
					messageIsRead,
					message);
			emailMessages.add(emailMessage);
			
			if (!messageIsRead) {
				incrementMessagesCounter();
			}
			
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
		return emailMessage;
	}
	
	public void incrementMessagesCounter() {
		unreadMessagesCounter++;
		updateName();
	}
	
	public void decrementMessagesCounter() {
		unreadMessagesCounter--;
		updateName();
	}
	
	private void updateName() {
		if (unreadMessagesCounter > 0) {
			this.setValue((String) (name + "(" + unreadMessagesCounter + ")"));
		}
		else {
			this.setValue(name);
		}
	}

}

