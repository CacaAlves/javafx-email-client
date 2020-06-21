package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import view.EmailManager;
import view.ViewFactory;

public class MainWindowController extends BaseController implements Initializable {

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
		super(emailManager, viewFactory, fxmlName);
	}

	@FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private TableView<String> emailsTableView;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setUpEmailsTreeView();
	}

	private void setUpEmailsTreeView() {
		emailsTreeView.setRoot(emailManager.getFoldersRoot());
		emailsTreeView.setShowRoot(false);
	}

}