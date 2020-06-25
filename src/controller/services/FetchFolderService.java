package controller.services;

import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.EmailTreeItem;
import view.IconResolver;

public class FetchFolderService extends Service<Void> {

	private Store store;
	private EmailTreeItem<String> foldersRoot;
	private List<Folder> folderList;
	private IconResolver iconResolver = new IconResolver(); 

	public FetchFolderService(Store store, EmailTreeItem<String> foldersRoot, List<Folder> folderList) {
		this.store = store;
		this.foldersRoot = foldersRoot;
		this.folderList = folderList;
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
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		handleFolders(folders, foldersRoot);

	}

	private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot) {
		for (Folder folder : folders) {
			folderList.add(folder);
			EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(folder.getName());
			emailTreeItem.setGraphic(iconResolver.getIconFolder(folder.getName()));
			foldersRoot.getChildren().add(emailTreeItem);
			foldersRoot.setExpanded(true);
			fetchMessagesOnFolder(folder, emailTreeItem);
			addMessageListenerToFolder(folder, emailTreeItem);

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

	private void addMessageListenerToFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
		folder.addMessageCountListener(new MessageCountListener() {
			
			@Override
			public void messagesRemoved(MessageCountEvent e) {
				for (int i = 0; i < e.getMessages().length; i++) {
					try {
						Message message = folder.getMessage(folder.getMessageCount() - i);
						emailTreeItem.addEmailToTop(message);
					} catch (MessagingException e1) {
						e1.printStackTrace();
					}
				}
				
			}
			
			@Override
			public void messagesAdded(MessageCountEvent e) {
				System.out.println("Message removed: " + e);
				
				
			}
		});
	}

	private void fetchMessagesOnFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
		Service<Object> fetchMessagesService = new Service<Object>() {

			@Override
			protected Task<Object> createTask() {
				
				return new Task<Object>() {

					@Override
					protected Object call() throws Exception {
						if (folder.getType() != Folder.HOLDS_FOLDERS) {
							folder.open(Folder.READ_WRITE);
							int folderSize = folder.getMessageCount();
							for (int i = folderSize; i > 0; i--) {
								emailTreeItem.addEmail(folder.getMessage(i));
							}
						}
						return null;
					}
					
				};
			}
			
		};
		fetchMessagesService.start();
	}
}
