module javaFXEmailClient {
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.web;
	requires javafx.base;
	requires java.mail;
	
	opens application;
	opens view;
	opens controller;
	opens model;
}