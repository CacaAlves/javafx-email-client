package controller;

import controller.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.EmailAccount;
import view.EmailManager;
import view.ViewFactory;

public class LoginWindowController extends BaseController {

	public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
		super(emailManager, viewFactory, fxmlName);
	}

	@FXML
	private Label errorLabel;

	@FXML
	private TextField email;

	@FXML
	private TextField password;

	@FXML
	void loginButtonAction() {
		if (fieldsAreValid()) {
			EmailAccount emailAccount = new EmailAccount(email.getText(), password.getText());
			LoginService ls = new LoginService(emailAccount, emailManager);
			ls.start();
			ls.setOnSucceeded(event -> {
				 
				EmailLoginResult emailLoginResult = ls.getValue();
						
						switch (emailLoginResult) {
						case SUCCESS:
							System.out.println(emailAccount.getAddress());
							
							Stage stage = (Stage) errorLabel.getScene().getWindow();
							viewFactory.closeStage(stage);
							return;
							
						default:
							break;
						}
		
			});
		}
	}

	private boolean fieldsAreValid() {
		if (email.getText().isEmpty()) {
			errorLabel.setText("Please, fill email");
			return false;
		} 
		else if (password.getText().isEmpty()) {
			errorLabel.setText("Please, fill password");
			return false;
		}
		return true;
	}

}