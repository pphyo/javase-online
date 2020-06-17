package com.jdc.app.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BookEdit {

	@FXML
	private Label info;
	@FXML
	private ComboBox<String> category;
	@FXML
	private ComboBox<String> authorName;
	@FXML
	private TextField bookName;
	@FXML
	private TextField price;
	@FXML
	private DatePicker releaseDate;
	@FXML
	private TextArea remark;
	
	public static void show() {
		try {
			
			FXMLLoader loader = new FXMLLoader(BookEdit.class.getResource("BookEdit.fxml"));
			Parent view = loader.load();
			
			Stage stage = new Stage();
			stage.setScene(new Scene(view));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		
	}
	
	public void close() {
		info.getScene().getWindow().hide();
	}
	
}