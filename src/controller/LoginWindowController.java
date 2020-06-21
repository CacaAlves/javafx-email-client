package controller;

import java.net.URL;
import java.util.ResourceBundle;

import controller.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.EmailAccount;
import view.EmailManager;
import view.ViewFactory;

public class LoginWindowController extends BaseController implements Initializable {

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
							viewFactory.showMainWindow();
							return;
						case FAILED_BY_CREDENTIALS:
							errorLabel.setText("Wrong credentials");
							return;
						case FAILED_BY_NETWORK_ERROR:
							errorLabel.setText("Network error");
							return;
						case FAILED_BY_UNEXPECTED_ERROR:
							errorLabel.setText("Unexpected error");
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		email.setText("cacazinho99@gmail.com");
		
	}

}