package com.jdc.app.view;

import java.io.FileInputStream;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFrame {
	
	@FXML
	private Label title;
	@FXML
	private Label footer;
	@FXML
	private VBox node;
	@FXML
	private StackPane viewHolder;
	
	public static void show() {
		try {
			FXMLLoader loader = new FXMLLoader(Login.class.getResource("MainFrame.fxml"));
			Stage stage = new Stage();
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setTitle("Book Store");
			stage.getIcons().add(new Image(new FileInputStream("icon/searchColor.png")));
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showHome(MouseEvent event) {
		changeActive(event);
		loadView("Sale Book", "Home.fxml");
	}
	
	public void showCategory(MouseEvent event) {
		changeActive(event);
		loadView("Book's Categories", "BookCategory.fxml");
	}
	
	public void showBook(MouseEvent event) {
		changeActive(event);
		loadView("Book List", "BookList.fxml");
	}
	
	public void showAuthor(MouseEvent event) {
		changeActive(event);
		loadView("Author List", "AuthorList.fxml");
	}
	
	public void showHistory(MouseEvent event) {
		changeActive(event);
		loadView("Sale History", "SaleHistory.fxml");
	}
	
	public void loadView(String viewName, String viewFile) {
		try {
			title.setText(viewName);
			Parent view = FXMLLoader.load(getClass().getResource(viewFile));
			viewHolder.getChildren().clear();
			viewHolder.getChildren().add(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeActive(MouseEvent event) {
		Node n = (Node)event.getSource();
		node.getChildren().stream().filter(a -> a.getStyleClass().contains("active")).findAny().ifPresent(a -> a.getStyleClass().remove("active"));
		n.getStyleClass().add("active");
	}
	
	@FXML
	private void initialize() {
		loadView("My Book Store", "Home.fxml");
		footer.setText("\u00A9 By JDC \u00A9");	
		
	}

}