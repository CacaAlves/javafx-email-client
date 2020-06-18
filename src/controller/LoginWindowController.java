package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginWindowController {

    @FXML
    private Button errorLabel;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    void loginButtonAction(ActionEvent event) {
    	System.out.println("Login");
    }

}