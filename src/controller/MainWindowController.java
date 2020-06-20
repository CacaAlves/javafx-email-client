package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import view.EmailManager;
import view.ViewFactory;

public class MainWindowController extends BaseController {

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
		super(emailManager, viewFactory, fxmlName);
	}

	@FXML
    private TreeView<?> emailsTreeView;

    @FXML
    private TableView<?> emailsTableView;

    @FXML
    private WebView emailWebView;

    @FXML
    void optionsAction(ActionEvent event) {
    	viewFactory.showOptionsWindow();
    }
    
    @FXML
    void addAccountAction(ActionEvent event) {
    	viewFactory.showLoginWindow();
    }

}