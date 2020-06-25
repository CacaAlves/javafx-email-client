package controller.services;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;
import model.EmailMessage;

public class MessageRendererService extends Service<EmailMessage> {
	
	private EmailMessage emailMessage;
	private WebEngine webEngine;
	private StringBuffer stringBuffer;

	public MessageRendererService(WebEngine webEngine) {
		this.webEngine = webEngine; 
		this.stringBuffer  = new StringBuffer();
		this.setOnSucceeded(e -> {
			displayMessage();
		});
	}
	
	public void setEmailMessage(EmailMessage emailMessage) {
		this.emailMessage = emailMessage;
	}
	
	private void displayMessage() {
		webEngine.loadContent(stringBuffer.toString());
	}
	
	@Override
	protected Task createTask() {
		return new Task() {

			@Override
			protected Object call() throws Exception {
				loadMessage();
				return null;
			}
			
		};
	}
	
	public void loadMessage() throws MessagingException, IOException {
		stringBuffer.setLength(0); //clear the stringBuffer
		Message message = emailMessage.getMessage();
		String contentType = message.getContentType();
		if (isSimpleType(contentType)) {
			stringBuffer.append(message.getContent().toString());
		} 
		else if (isMultipleType(contentType)) {
			Multipart multipart = (Multipart) message.getContent();
			for (int i = multipart.getCount() - 1; i >= 0; i++) {
				BodyPart bp = multipart.getBodyPart(i);
				String  bpContentType = bp.getContentType();
				if (isSimpleType(bpContentType)) {
					stringBuffer.append(bp.getContent().toString());
				}
			}
		}
	}

	private boolean isSimpleType(String contentType) {
		return (contentType.contains("TEXT/HTML")) ||
			   (contentType.contains("mixed")) ||
			   (contentType.contains("text"));
	}
	
	private boolean isMultipleType(String contentType) {
		return (contentType.contains("multipart"));
	}
}
