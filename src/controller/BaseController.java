package controller;

import view.EmailManager;
import view.ViewFactory;

public abstract class BaseController {
	
	protected EmailManager emailManager;
	protected ViewFactory viewFactory;
	protected String fxmlName;
	
	public BaseController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
		this.emailManager = emailManager;
		this.viewFactory = viewFactory;
		this.fxmlName = fxmlName;
	}

	public String getFxmlName() {
		return fxmlName;
	}
	
	
}
