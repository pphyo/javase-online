package com.jdc.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainFrame implements Initializable {
	
	@FXML
	private Label title;
	@FXML
	private Label footer;
	@FXML
	private StackPane viewHolder;
	
	public void showHome() {
		
	}
	
	public void showCategory() {
		
	}
	
	public void showBook() {
		
	}
	
	public void showAuthor() {
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		footer.setText("\u00A9 By JDC \u00A9");		
	}

}
