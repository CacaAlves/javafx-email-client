package controller;

import java.net.URL;
import java.util.ResourceBundle;

import controller.services.EmailSenderService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import model.EmailAccount;
import view.EmailManager;
import view.ViewFactory;

public class ComposeMessageWindowController extends BaseController implements Initializable {
	
	public ComposeMessageWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
		super(emailManager, viewFactory, fxmlName);
	}
	
	@FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;
	
	@FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private HTMLEditor htmlEditor;
    
    @FXML
    private Label errorLabel;

    @FXML
    void sendBtnAction() {
    	EmailSenderService service = new EmailSenderService(
    			emailAccountChoice.getValue(),
    			subjectTextField.getText(),
    			recipientTextField.getText(),
    			htmlEditor.getHtmlText());
    	service.start();
    	service.setOnSucceeded(e -> {
    		EmailSendingResult result = service.getValue();	
    		switch (result) {
			case SUCCESS:
				Stage stage = (Stage) recipientTextField.getScene().getWindow();
				viewFactory.closeStage(stage);
				break;
			case FAILED_BY_PROVIDER:
				errorLabel.setText("Provider error!");
				break;
			case FAILED_BY_UNEXPECTED_ERROR:
				errorLabel.setText("Unexpected error!");
				break;
			default:
				break;
			}
    	});
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		emailAccountChoice.setItems(emailManager.getEmailAccounts());
		emailAccountChoice.setValue(emailManager.getEmailAccounts().get(0));
	}
	
}







