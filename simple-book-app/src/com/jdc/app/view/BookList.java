package com.jdc.app.view;

import java.io.File;
import java.util.List;

import com.jdc.app.entity.Author;
import com.jdc.app.entity.Book;
import com.jdc.app.entity.Category;
import com.jdc.app.service.AuthorService;
import com.jdc.app.service.BookService;
import com.jdc.app.service.CategoryService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class BookList {
	
	@FXML
	private ComboBox<Category> category;
	@FXML
	private ComboBox<Author> authorName;
	@FXML
	private TextField bookName;
	@FXML
	private DatePicker releaseDate;
	@FXML
	private TableView<Book> tblList;
	private CategoryService catService;
	private AuthorService authService;
	private BookService bookService;
	
	public void add() {
		BookEdit.show(null, this::save);
	}
	
	private void update() {
		Book book = tblList.getSelectionModel().getSelectedItem();
		BookEdit.show(book, this::save);
	}
	
	private void delete() {
		Book book = tblList.getSelectionModel().getSelectedItem();
		bookService.delete(book);
		search();
	}
	
	private void imgUpload() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose Book Cover");
		fc.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
		fc.setSelectedExtensionFilter(new ExtensionFilter("Book Cover", "*.png", ".jpg"));
		
		File file = fc.showOpenDialog(category.getScene().getWindow());
		Book book = tblList.getSelectionModel().getSelectedItem();
		book.setImage(file.getAbsolutePath());
		bookService.imageUpload(book);
	}
	
	private void showDetail() {
		Book book = tblList.getSelectionModel().getSelectedItem();
		Detail.show(book);
	}
	
	private void save(Book book) {
		search();
	}
	
	public void search() {
		tblList.getItems().clear();
		List<Book> bookList = bookService.findByParams(category.getValue(), authorName.getValue(), bookName.getText(), releaseDate.getValue());
		tblList.getItems().addAll(bookList);
		
	}
	
	private void createMenu() {
		MenuItem edit = new MenuItem("Edit");
		MenuItem delete = new MenuItem("Delete");
		MenuItem imgUpload= new MenuItem("Image Upload");
		MenuItem detail = new MenuItem("Book Detail");
		
		tblList.setContextMenu(new ContextMenu(edit, delete, imgUpload, detail));
		edit.setOnAction(e -> update());
		delete.setOnAction(e -> delete());
		imgUpload.setOnAction(e -> imgUpload());
		detail.setOnAction(e -> showDetail());
	}
	
	@FXML
	private void initialize() {
		catService = CategoryService.getInstance();
		authService = AuthorService.getInstance();
		bookService = BookService.getInstance();
		search();
		
		category.getItems().addAll(catService.findAll());
		authorName.getItems().addAll(authService.findAll());
		
		createMenu();
	}

}