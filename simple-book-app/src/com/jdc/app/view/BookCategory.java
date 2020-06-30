package com.jdc.app.view;

import java.io.File;
import java.util.List;

import com.jdc.app.entity.Category;
import com.jdc.app.service.CategoryService;
import com.jdc.app.view.custom.CategoryBox;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class BookCategory {
	
	@FXML
	private TextField name;
	@FXML
	private TilePane categoryBoxHolder;
	
	private CategoryService catService;
	
	public void add() {
		Category c = new Category();
		if(!name.getText().isEmpty())
			c.setName(name.getText());
		catService.add(c);
		search();
	}
	
	public void upload() {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
		fc.setSelectedExtensionFilter(new ExtensionFilter("Category File", "*.txt", "*.csv", "*.tsv"));
		File file = fc.showOpenDialog(name.getScene().getWindow());
		catService.upload(file);
		search();
	}
	
	public void search() {
		List<Category> catList = catService.findByName(name.getText());
		categoryBoxHolder.getChildren().clear();
		catList.stream().map(CategoryBox::new).forEach(categoryBoxHolder.getChildren()::addAll);
	}
	
	public void clear() {
		name.clear();
	}

	@FXML
	private void initialize() {
		catService = CategoryService.getInstance();
		search();
		
		name.textProperty().addListener((a, b, c) -> {
			search();
		});
	}

}