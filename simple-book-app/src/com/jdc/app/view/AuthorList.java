package com.jdc.app.view;

import java.util.List;

import com.jdc.app.entity.Author;
import com.jdc.app.service.AuthorService;
import com.jdc.app.util.DecimalFormatedConverter;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class AuthorList {
	
	@FXML
	private TextField authorName;
	@FXML
	private TextField age;
	@FXML
	private TextField country;
	@FXML
	private TableView<Author> tblList;
	@FXML
	private TableColumn<Author, String> nameCol;
	@FXML
	private TableColumn<Author, Integer> ageCol;
	@FXML
	private TableColumn<Author, String> countryCol;
	
	private AuthorService authService;
	
	public void add() {
		Author author = new Author();
		if(!authorName.getText().isEmpty())
			author.setName(authorName.getText());
		if(getAge() > 0)
			author.setAge(getAge());
		if(!country.getText().isEmpty())
			author.setCountry(country.getText());

		authService.add(author);
		search();
	}
	
	public void search() {
		tblList.getItems().clear();
		List<Author> authList = authService.findByParam(authorName.getText(), getAge(), country.getText());
		tblList.getItems().addAll(authList);
	}
	
	public void clear() {
		authorName.clear();
		age.clear();
		country.clear();
	}
	
	private void listener() {
		authorName.textProperty().addListener((a, b, c) -> {
			search();
		});
		age.textProperty().addListener((a, b, c) -> {
			search();
		});
		country.textProperty().addListener((a, b, c) -> {
			search();
		});
	}

	public int getAge() {
		return this.age.getText().isEmpty() ? 0 : Integer.parseInt(this.age.getText());
	}
	
	@FXML
	private void initialize() {
		authService = AuthorService.getInstance();
		search();
		
		listener();
		
		
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setOnEditCommit(e -> {
			Author data = e.getRowValue();
			data.setName(e.getNewValue());
			authService.update(data);
			search();
		});
		
		ageCol.setCellFactory(TextFieldTableCell.forTableColumn(new DecimalFormatedConverter()));
		ageCol.setOnEditCommit(e -> {
			Author data = e.getRowValue();
			data.setAge(e.getNewValue());
			authService.update(data);
			search();
		});
		
		countryCol.setCellFactory(TextFieldTableCell.forTableColumn());
		countryCol.setOnEditCommit(e -> {
			Author data = e.getRowValue();
			data.setCountry(e.getNewValue());
			authService.update(data);
			search();
		});
	}

}