package com.jdc.app.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.function.Consumer;

import com.jdc.app.entity.Author;
import com.jdc.app.entity.Book;
import com.jdc.app.entity.Category;
import com.jdc.app.service.AuthorService;
import com.jdc.app.service.BookService;
import com.jdc.app.service.CategoryService;
import com.jdc.app.util.AppException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BookEdit {
	
	@FXML
	private Label title;
	@FXML
	private Label info;
	@FXML
	private ComboBox<Category> category;
	@FXML
	private ComboBox<Author> authorName;
	@FXML
	private TextField bookName;
	@FXML
	private TextField price;
	@FXML
	private DatePicker releaseDate;
	@FXML
	private TextArea remark;

	private Book book;
	private BookService bookService;
	private CategoryService catService;
	private AuthorService authService;
	private static Stage stage;
	private Consumer<Book> saveHandler;
	
	public static void show(Book book, Consumer<Book> saveHandler) {
		try {
			FXMLLoader loader = new FXMLLoader(BookEdit.class.getResource("BookEdit.fxml"));
			Parent view = loader.load();
			
			stage = new Stage();
			
			BookEdit controller = loader.getController();
			controller.saveHandler = saveHandler;
			controller.setData(book);
			
			stage.setScene(new Scene(view));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			boolean bookIsNull = null == book;
			
			if(bookIsNull) {
				book = new Book();
			}
			
			if(null == category.getValue())
				throw new AppException("Please select category name!");
			book.setCategory(category.getValue());
			
			if(null == authorName.getValue())
				throw new AppException("Please select author name!");			
			book.setAuthor(authorName.getValue());

			if(null == bookName.getText() && bookName.getText().isEmpty())
				throw new AppException("Please enter book name!");
			book.setName(bookName.getText());
			
			if(getPrice() < 0)
				throw new AppException("Price must be greater than 0!");
			book.setPrice(getPrice());

			if(null == releaseDate.getValue())
				throw new AppException("Please select date!");
			book.setReleaseDate(releaseDate.getValue());
			
			book.setRemark(remark.getText());
			
			if(bookIsNull)
				bookService.add(book);
			else
				bookService.update(book);
			
			saveHandler.accept(book);
			
			close();
			
		} catch (NumberFormatException e) {
			info.setText("Please enter digit only for price!");
		} catch (Exception e) {
			e.printStackTrace();
			info.setText(e.getMessage());
		}
	}
	
	private void setData(Book book) throws FileNotFoundException {
		this.book = book;
		if(null != book) {
			category.setValue(book.getCategory());
			authorName.setValue(book.getAuthor());
			bookName.setText(this.book.getName());
			price.setText(String.valueOf(this.book.getPrice()));
			releaseDate.setValue(this.book.getReleaseDate());
			remark.setText(this.book.getRemark());
			
			title.setText("EDIT BOOK");
			stage.setTitle("Edit Book");
			stage.getIcons().add(new Image(new FileInputStream("icon/edit.png")));
		}else {
			title.setText("ADD BOOK");
			stage.setTitle("Add Book");
			stage.getIcons().add(new Image(new FileInputStream("icon/add.png")));
		}
	}
	
	public void reset() {
		category.setValue(null);
		authorName.setValue(null);
		price.clear();
		releaseDate.setValue(LocalDate.now());
		remark.clear();
	}
	
	public void close() {
		bookName.getScene().getWindow().hide();
	}
	
	public int getPrice() {
		return price.getText().isEmpty() ? 0 : Integer.parseInt(price.getText());
	}

	@FXML
	private void initialize() {
		bookService = BookService.getInstance();
		catService = CategoryService.getInstance();
		authService = AuthorService.getInstance();
		reset();
		
		category.getItems().addAll(catService.findAll());
		authorName.getItems().addAll(authService.findAll());
	}

}
