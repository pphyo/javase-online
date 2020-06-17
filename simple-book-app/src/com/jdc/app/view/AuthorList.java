package com.jdc.app.view;

import java.util.List;

import com.jdc.app.entity.Author;
import com.jdc.app.service.AuthorService;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AuthorList {
	
	@FXML
	private TextField name;
	@FXML
	private TextField age;
	@FXML
	private TextField country;
	@FXML
	private TableView<Author> tblList;
	
	private AuthorService authService;
	
	public void add() {
		Author author = new Author();
		author.setName(name.getText());
		author.setAge(Integer.parseInt(age.getText()));
		author.setCountry(country.getText());
		authService.add(author);
		search();
		clear();
	}
	
	public void search() {
		tblList.getItems().clear();
		int age = this.age.getText().isEmpty() ? 0 : Integer.parseInt(this.age.getText());
		List<Author> authList = authService.finByParams(name.getText(), age, country.getText());
		tblList.getItems().addAll(authList);
	}
	
	public void clear() {
		name.clear();
		age.clear();
		country.clear();
	}

	@FXML
	private void initialize() {
		authService = AuthorService.getInstance();
		search();
	}
}
