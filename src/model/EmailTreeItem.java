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
		try {
			boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
			
			EmailMessage emailMessage = new EmailMessage(
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
			
			System.out.println("added to  " + name + " " + message.getSubject());
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void incrementMessagesCounter() {
		unreadMessagesCounter++;
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
