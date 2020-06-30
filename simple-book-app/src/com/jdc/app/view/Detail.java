package com.jdc.app.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;

import com.jdc.app.entity.Book;
import com.jdc.app.service.BookService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Detail {
	
	@FXML
	private ImageView image;
	@FXML
	private Label category;
	@FXML
	private Label bookName;
	@FXML
	private Label authorName;
	@FXML
	private Label price;
	@FXML
	private Label releaseDate;
	@FXML
	private Label remark;
	
	private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	private BookService bookService;
	
	public static void show(Book book) {
		try {
			
			FXMLLoader loader = new FXMLLoader(Detail.class.getResource("Detail.fxml"));
			Parent view = loader.load();
			Detail controller = loader.getController();
			Stage stage = new Stage();
			stage.setScene(new Scene(view));
			
			controller.setData(book);
			
			stage.getIcons().add(new Image(new FileInputStream("detail.png")));
			stage.setTitle("Book Detail");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setData(Book book) throws FileNotFoundException {
		if(null != book) {
			String image = bookService.findImage(book);
			if(null != image && !image.isEmpty())
				this.image.setImage(new Image(new FileInputStream(image)));
			category.setText(book.getCategory().toString());
			authorName.setText(book.getAuthor().toString());
			bookName.setText(book.getName());
			price.setText(String.valueOf(book.getPrice()));
			releaseDate.setText(String.valueOf(format.format(book.getReleaseDate())));
			remark.setText(book.getRemark());
		}
	}
	
	public void buy() {
		
	}
	
	public void close() {
		image.getScene().getWindow().hide();
	}

	@FXML
	private void initialize() {
		bookService = BookService.getInstance();
	}
	
}