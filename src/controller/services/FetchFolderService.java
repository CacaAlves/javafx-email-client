package controller.services;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.EmailTreeItem;

public class FetchFolderService extends Service<Void> {

	private Store store;
	private EmailTreeItem<String> foldersRoot;

	public FetchFolderService(Store store, EmailTreeItem<String> foldersRoot) {
		this.store = store;
		this.foldersRoot = foldersRoot;
	}

	@Override
	protected Task<Void> createTask() {

		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				fetchFolders();
				return null;
			}
		};
	}

	private void fetchFolders() {
		Folder[] folders = null;
		try {
			folders = store.getDefaultFolder().list();
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
		
		handleFolders(folders, foldersRoot);

	}

	private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot) {
		for (Folder folder: folders) {
			EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(folder.getName());
			foldersRoot.getChildren().add(emailTreeItem);
			foldersRoot.setExpanded(true);
			
			try {
				if (folder.getType() == Folder.HOLDS_FOLDERS) {
					Folder[] subFolders = folder.list();
					handleFolders(subFolders, emailTreeItem);
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
}
