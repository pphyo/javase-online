package com.jdc.app.view;

import com.jdc.app.entity.SaleDeatil;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Home {
	
	@FXML
	private ComboBox<String> category;
	@FXML
	private ComboBox<String> authorName;
	@FXML
	private TextField bookName;
	@FXML
	private DatePicker releasedDate;
	@FXML
	private TableView<SaleDeatil> tblList;
	
	public void addToCart(MouseEvent event) {
		if(event.getClickCount() == 2) {
			
		}
	}
	
	public void search() {
		
	}
	
	public void clear() {
		
	}

}