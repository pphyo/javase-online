package com.jdc.app.view;

import java.time.LocalDate;
import java.util.List;

import com.jdc.app.entity.Author;
import com.jdc.app.entity.Book;
import com.jdc.app.entity.Category;
import com.jdc.app.service.AuthorService;
import com.jdc.app.service.BookService;
import com.jdc.app.service.CategoryService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

public class BookList {
	
	@FXML
	private ComboBox<String> category;
	@FXML
	private ComboBox<String> authorName;
	@FXML
	private DatePicker releaseDate;
	@FXML
	private TableView<Book> tblList;
	
	private CategoryService catService;
	private AuthorService authService;
	private BookService bookService;
	
	public void add() {
		BookEdit.show();
	}
	
	public void search() {
		
	}
	
	public void clear() {
		category.setValue(null);
		authorName.setValue(null);
		releaseDate.setValue(LocalDate.now());
	}
	
	private void loadCategory() {
		List<Category> catList = catService.findAll();
		catList.stream().map(a -> a.getName()).forEach(a -> category.getItems().add(a));
	}
	
	private void loadAuthor() {
		List<Author> authList = authService.findAll();
		authList.stream().map(a -> a.getName()).forEach(authorName.getItems()::addAll);
	}
	
	@FXML
	private void initialize() {
		catService = CategoryService.getInstance();
		authService = AuthorService.getInstance();
		bookService = BookService.getInstance();
		loadCategory();
		loadAuthor();
	}

}
