package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.BaseController;
import controller.ComposeMessageWindowController;
import controller.LoginWindowController;
import controller.MainWindowController;
import controller.OptionsWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {

	private EmailManager emailManager;
	private List<Stage> activeStages;
	private ColorTheme colorTheme = ColorTheme.DEFAULT;
	private FontSize fontSize = FontSize.MEDIUM;
	private boolean mainViewInitialized;

	public ViewFactory(EmailManager emailManager) {
		this.emailManager = emailManager;
		this.activeStages = new ArrayList<>();
		this.mainViewInitialized = false;
	}

	public ColorTheme getColorTheme() {
		return colorTheme;
	}

	public void setColorTheme(ColorTheme colorTheme) {
		this.colorTheme = colorTheme;
	}

	public FontSize getFontSize() {
		return fontSize;
	}

	public void setFontSize(FontSize fontSize) {
		this.fontSize = fontSize;
	}

	public void showLoginWindow() {
		BaseController controller = new LoginWindowController(emailManager, this, "LoginWindow.fxml");
		initializeStage(controller);
	}

	public void showMainWindow() {
		if (!mainViewInitialized) {
			BaseController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");
			initializeStage(controller);
			mainViewInitialized = true;
		}
	}

	public void showOptionsWindow() {
		BaseController controller = new OptionsWindowController(emailManager, this, "OptionsWindow.fxml");
		initializeStage(controller);
	}
	
	public void showComposeMessageWindow() {
		BaseController controller = new ComposeMessageWindowController(emailManager, this, "ComposeMessageWindow.fxml");
		initializeStage(controller);
	}

	private void initializeStage(BaseController controller) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
		fxmlLoader.setController(controller);
		Parent parent;
		try {
			parent = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		activeStages.add(stage);
	}

	public void closeStage(Stage stage) {
		stage.close();
		activeStages.remove(stage);
	}

	public void updateStyles() {

		for (Stage stage : activeStages) {
			Scene scene = stage.getScene();
			scene.getStylesheets().clear();
			scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
			scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
		}
	}
}
