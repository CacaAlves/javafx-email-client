package view;

import controller.services.FetchFolderService;
import javafx.scene.control.TreeItem;
import model.EmailAccount;
import model.EmailTreeItem;

public class EmailManager {

	private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

	public EmailTreeItem<String> getFoldersRoot() {
		return foldersRoot;
	}

	public void addEmailAccount(EmailAccount emailAccount) {
		EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
		FetchFolderService fetchFolderService = new FetchFolderService(emailAccount.getStore(),treeItem);
		fetchFolderService.start();
		foldersRoot.getChildren().add(treeItem);
	}

}
